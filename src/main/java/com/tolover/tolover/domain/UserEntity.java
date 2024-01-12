package com.tolover.tolover.domain;


import jakarta.persistence.*;

@Entity
@Table(name="user")

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column
    private String nickname;

    @Column(name="created_at")
    private String createdAt;

    @Column(name="updated_at")
    private String updatedAt;
    @Column
    private String status;
}
