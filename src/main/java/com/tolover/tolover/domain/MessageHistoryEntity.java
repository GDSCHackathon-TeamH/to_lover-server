package com.tolover.tolover.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="message_history")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", referencedColumnName = "id", insertable = true, updatable = true)
    private ChatRoomEntity chatRoom;

    @Column(name="created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="message", columnDefinition="TEXT")
    private String message;

    @Column(name="role")
    private String role;
}
