package com.tolover.tolover.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name="user")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column
    private String nickname;

    @Column(name="kakao_id")
    private Long kakaoId;


}
