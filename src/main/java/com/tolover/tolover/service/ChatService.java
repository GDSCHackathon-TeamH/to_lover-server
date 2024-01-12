package com.tolover.tolover.service;

import com.tolover.tolover.config.ChatGPTConfig;
import com.tolover.tolover.domain.ChatRoomEntity;
import com.tolover.tolover.domain.UserEntity;
import com.tolover.tolover.dto.chat.ChatMessage;
import com.tolover.tolover.dto.chat.CreateChatRoomReqDTO;
import com.tolover.tolover.dto.chat.CreateChatRoomResDTO;
import com.tolover.tolover.repository.ChatRoomRepository;
import com.tolover.tolover.repository.MessageHistoryRepository;
import com.tolover.tolover.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final MessageHistoryRepository messageHistoryRepository;
    private final UserRepository userRepository;
    private static final String ENDPOINT = "https://api.openai.com/v1/completions";

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
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));

        chatRoomBuilder.user(user);
        if(createChatRoomReqDTO.getCharacteristic() != null) {
            chatRoomBuilder.characteristic(createChatRoomReqDTO.getCharacteristic());
        }

        if(createChatRoomReqDTO.getMbti() != null) {
            chatRoomBuilder.mbti(createChatRoomReqDTO.getMbti());
        }

        if(createChatRoomReqDTO.getConversation_subject() != null) {
            chatRoomBuilder.conversation_subject(createChatRoomReqDTO.getConversation_subject());
        }

        System.out.println("제발 떠라" + createChatRoomReqDTO.getSpecific_situation());
        if(createChatRoomReqDTO.getSpecific_situation() != null) {
            chatRoomBuilder.specific_situation(createChatRoomReqDTO.getSpecific_situation());

        }

        ChatRoomEntity chatRoom = chatRoomBuilder.build();

        ChatRoomEntity savedRoom = chatRoomRepository.save(chatRoom);

        //message 전송 및 first message


        return new CreateChatRoomResDTO(savedRoom.getId(),"hi");

    }


    public void sendMessage(ArrayList<ChatMessage> messageArrayList){

    }

    public void getMessageList(long chatRoomId){

    }

//    public String generateText(String prompt, float temperature, int maxTokens) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Autho addrization", "Bearer " + secretKey);
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("model","text-davinci-003");
//        requestBody.put("prompt", prompt);
//        requestBody.put("temperature", temperature);
//        requestBody.put("max_tokens", maxTokens);
//
//        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);
//        return response.toString();
//    }
}
