package com.tolover.tolover.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateChatRoomResDTO {
    private Long chatRoomId;
    private String firstMessage;
}
