package com.tolover.tolover.service;

import com.tolover.tolover.config.ChatGPTConfig;
import com.tolover.tolover.domain.ChatRoomEntity;
import com.tolover.tolover.domain.MessageHistoryEntity;
import com.tolover.tolover.domain.UserEntity;
import com.tolover.tolover.dto.chat.ChatMessage;
import com.tolover.tolover.dto.chat.CreateChatRoomReqDTO;
import com.tolover.tolover.dto.chat.CreateChatRoomResDTO;
import com.tolover.tolover.dto.chat.SendMessageDTO;
import com.tolover.tolover.repository.ChatRoomRepository;
import com.tolover.tolover.repository.MessageHistoryRepository;
import com.tolover.tolover.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.*;

@Service
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final MessageHistoryRepository messageHistoryRepository;
    private final UserRepository userRepository;
    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.key}")
    private String secretKey;
    public ChatService(ChatRoomRepository chatRoomRepository, MessageHistoryRepository messageHistoryRepository, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.messageHistoryRepository = messageHistoryRepository;
        this.userRepository = userRepository;
    }

    public CreateChatRoomResDTO createChatRoom(CreateChatRoomReqDTO createChatRoomReqDTO, long userId) {

        ChatRoomEntity.ChatRoomEntityBuilder chatRoomBuilder = ChatRoomEntity.builder();
        chatRoomBuilder.communicator(createChatRoomReqDTO.getCommunicator());
        String prompt = "";
        if(createChatRoomReqDTO.getCommunicator() != "counselor"){
            prompt += "너의 역할은 " + createChatRoomReqDTO.getCommunicator() + "야. ";
        }else{
            prompt += "너의 역할은 나와 이별한 내가 사랑했던 " + createChatRoomReqDTO.getCommunicator() + "야. ";
        }
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));

        chatRoomBuilder.user(user);

        if(createChatRoomReqDTO.getCharacteristic() != null) {
            chatRoomBuilder.characteristic(createChatRoomReqDTO.getCharacteristic());
            prompt += " 너의 성격 및 특징은 주로 " + createChatRoomReqDTO.getMbti() + "라고 가정하고 말해줘.";
        }

        if(createChatRoomReqDTO.getMbti() != null) {
            chatRoomBuilder.mbti(createChatRoomReqDTO.getMbti());
            prompt += "너의 mbti는 " + createChatRoomReqDTO.getMbti() + "라고 가정하고 말해줘. ";
        }

        if(createChatRoomReqDTO.getConversation_subject() != null) {
            chatRoomBuilder.conversation_subject(createChatRoomReqDTO.getConversation_subject());
            prompt += "대화 주제는 주로" + createChatRoomReqDTO.getConversation_subject() + "를 할거야.";
        }

        if(createChatRoomReqDTO.getSpecific_situation() != null) {
            chatRoomBuilder.specific_situation(createChatRoomReqDTO.getSpecific_situation());
            prompt += "너와 나의 상황은 " + createChatRoomReqDTO.getConversation_subject() + "이고 이를 잘 기억해두고 대화해.";
        }

        ChatRoomEntity chatRoom = chatRoomBuilder.build();
        ChatRoomEntity savedRoom = chatRoomRepository.save(chatRoom);

        //message 전송 및 first message
        prompt += " 자 이런 상황에서 첫 마디를 간단하게 시작해줘.나에 대한 간단한 안부 인사로 시작해도 좋아. ";
        ChatMessage calculed_prompt = makeMessageFormat(prompt, "user");
        ArrayList<ChatMessage>  messageList = new ArrayList<>();
        messageList.add(calculed_prompt);

//        System.out.println("messageList = " + makeMessageComplete(messageList));
        String firstResponse = sendMessage(messageList);


        //message history 저장
        MessageHistoryEntity sentmessageHistory = MessageHistoryEntity.builder().message(prompt)
                .chatRoom(savedRoom).role("user").build();
        messageHistoryRepository.save(sentmessageHistory);

        MessageHistoryEntity messageHistory = MessageHistoryEntity.builder().message(firstResponse)
                .chatRoom(savedRoom).role("system").build();
        messageHistoryRepository.save(messageHistory);

        return new CreateChatRoomResDTO(savedRoom.getId(),firstResponse);
    }


    public String getMessageList(long chatRoomId){
        String prompt = "";

        return prompt;
    }

    public ChatMessage makeMessageFormat(String Message, String Role){
        return new ChatMessage(Role, Message);
    }

    public List<String>  makeMessageComplete(ArrayList<ChatMessage> messageList){

        List<String> resultList = new ArrayList<>();

        for (ChatMessage message : messageList) {
            String role = message.getRole();
            String content = message.getContent();

            // JSON 형태의 문자열 생성
            String messageJson = String.format("{\"role\": \"%s\", \"content\": \"%s\"}", role, content);

            // 생성된 JSON 형태의 문자열을 리스트에 추가
            resultList.add(messageJson);
            System.out.println("messageJson = " + messageJson);
        }

        return resultList;
    }

    public String sendMessage(ArrayList<ChatMessage> prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + secretKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model","gpt-3.5-turbo");

        requestBody.put("messages", prompt);


        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);




        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");

                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> firstChoice = choices.get(0);
                    Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");

                    if (message != null) {
                        String content = (String) message.get("content");
                        return content;
                    }
                }
            }

        }
        return "";
    }

    public CreateChatRoomResDTO sendMessageAfter(SendMessageDTO sendMessageDTO, Long userId) {
        //기존 것 가져와서 prompt 만들기
        ChatRoomEntity chatRoom = chatRoomRepository.findById(sendMessageDTO.getChatRoomId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));;// 채팅방 엔티티를 가져오는 코드

        //기존의 것들을 MessageHistoryEntity로 가져옴
        List<MessageHistoryEntity> messages = messageHistoryRepository.findByChatRoomOrderByCreatedAtAsc(chatRoom);

        ArrayList<ChatMessage>  messageList = new ArrayList<>();
        for (MessageHistoryEntity messageHistory: messages){
            messageList.add(new ChatMessage(messageHistory.getRole(), messageHistory.getMessage()));
        }

        messageList.add(new ChatMessage("user", sendMessageDTO.getMessage()));

        String response = sendMessage(messageList);

        //message history 저장

        MessageHistoryEntity sentmessageHistory = MessageHistoryEntity.builder().message(sendMessageDTO.getMessage())
                .chatRoom(chatRoom).role("user").build();
        messageHistoryRepository.save(sentmessageHistory);

        MessageHistoryEntity messageHistory = MessageHistoryEntity.builder().message(response)
                .chatRoom(chatRoom).role("system").build();
        messageHistoryRepository.save(messageHistory);

        return new CreateChatRoomResDTO(chatRoom.getId(),response);
    }
}
