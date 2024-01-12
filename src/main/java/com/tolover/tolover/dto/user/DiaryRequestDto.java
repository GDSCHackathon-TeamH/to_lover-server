package com.tolover.tolover.dto.user;

import lombok.Data;

@Data
public class DiaryRequestDto {
    private Long userId;
    private String body;
    private Long todoId;
}
