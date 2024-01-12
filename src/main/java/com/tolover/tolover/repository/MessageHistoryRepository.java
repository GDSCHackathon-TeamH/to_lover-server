package com.tolover.tolover.repository;

import com.tolover.tolover.domain.ChatRoomEntity;
import com.tolover.tolover.domain.MessageHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageHistoryRepository extends JpaRepository<MessageHistoryEntity,Long> {
    List<MessageHistoryEntity> findByChatRoomOrderByCreatedAtAsc(ChatRoomEntity chatRoom);
}
