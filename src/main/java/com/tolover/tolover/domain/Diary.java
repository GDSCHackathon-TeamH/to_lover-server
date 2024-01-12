package com.tolover.tolover.domain;

import com.tolover.tolover.controller.UserController;
import com.tolover.tolover.dto.user.DiaryResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

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

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", updatable = false)
    private Date createdAt;

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
    }

    public DiaryResponseDto toDto(){
        DiaryResponseDto diaryResponseDto = DiaryResponseDto.builder()
                .body(this.getBody())
                .createdAt(this.getCreatedAt())
                .build();
        return diaryResponseDto;
    }
}
