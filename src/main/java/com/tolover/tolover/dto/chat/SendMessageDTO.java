package com.tolover.tolover.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageDTO {
    String message;
    Long chatRoomId;
}
