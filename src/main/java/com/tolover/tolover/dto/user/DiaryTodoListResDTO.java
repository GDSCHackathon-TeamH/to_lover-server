package com.tolover.tolover.dto.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryTodoListResDTO {
    String todo;
    String diaryText;
    LocalDateTime createdAt;

}
