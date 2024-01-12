package com.tolover.tolover.domain;

import com.tolover.tolover.controller.UserController;
import com.tolover.tolover.dto.user.DiaryResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BODY")
    private String body;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt; // LocalDate에서 LocalDateTime으로 변경

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "TODO_ID")
    private Todo todo;

    @Builder
    public Diary(String body, UserEntity user, Todo todo){
        this.body=body;
        this.user=user;
        this.todo=todo;
        this.createdAt = LocalDateTime.now(); // 생성 시점의 날짜와 시간으로 초기화
    }

    public DiaryResponseDto toDto(){
        DiaryResponseDto diaryResponseDto = DiaryResponseDto.builder()
                .body(this.getBody())
                .createdAt(this.getCreatedAt())
                .todoId(this.todo.getId())
                .userId(Long.valueOf(this.user.getId()))
                .build();
        return diaryResponseDto;
    }
}
