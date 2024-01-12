package com.tolover.tolover.dto.user;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DiaryResponseDto {
    private String body;
    private Date createdAt;
    private Long todoId;
}
