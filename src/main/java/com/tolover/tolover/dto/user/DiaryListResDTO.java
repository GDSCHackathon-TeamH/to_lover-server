package com.tolover.tolover.dto.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryListResDTO {
    Long todoId;
    String diaryText;
    LocalDateTime createdAt;
}
