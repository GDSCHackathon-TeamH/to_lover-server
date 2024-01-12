package com.tolover.tolover.dto.user;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

    @Data
    @Builder
    public class DiaryResponseDto {
        private String body;
        private LocalDateTime createdAt;
        private Long todoId;
        private Long userId;

        public static class DiaryResponseDtoBuilder {
            private Long userId;

            public DiaryResponseDtoBuilder userId(Long userId) {
                this.userId = userId;
                return this;
            }
        }
    }
