package com.tolover.tolover.controller;


import com.tolover.tolover.dto.chat.CreateChatRoomReqDTO;
import com.tolover.tolover.dto.chat.CreateChatRoomResDTO;
import com.tolover.tolover.exception.BaseException;
import com.tolover.tolover.exception.BaseResponse;
import com.tolover.tolover.service.ChatService;
import com.tolover.tolover.service.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final JwtService jwtService;
    public ChatController(ChatService chatService, JwtService jwtService) {
        this.chatService = chatService;
        this.jwtService = jwtService;
    }


    // 채팅방 있는지 여부 확인


    // create chat room
    @PostMapping("/room")
    public BaseResponse<CreateChatRoomResDTO> createChatRoom(@RequestBody CreateChatRoomReqDTO createChatRoomReqDTO) throws BaseException {
        Long userid = jwtService.getUserIdx();
        CreateChatRoomResDTO result = chatService.createChatRoom(createChatRoomReqDTO, userid);

        return new BaseResponse<>(result);
    }


    // message 보내기 (기존 것 포함 해서 가지고)



    // 채팅 했던 내용 불러오기



    //
}
