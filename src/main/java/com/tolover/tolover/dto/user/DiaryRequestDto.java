package com.tolover.tolover.dto.user;

import lombok.Data;

@Data
public class DiaryRequestDto {
    private String body;
    private Long todoId;

    public Long getTodoId() {
        return this.todoId;
    }
}
