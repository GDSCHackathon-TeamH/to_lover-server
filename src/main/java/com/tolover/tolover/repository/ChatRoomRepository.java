package com.tolover.tolover.repository;

import com.tolover.tolover.domain.ChatRoomEntity;
import com.tolover.tolover.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
}
