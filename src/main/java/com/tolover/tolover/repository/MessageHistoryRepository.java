package com.tolover.tolover.repository;

import com.tolover.tolover.domain.MessageHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageHistoryRepository extends JpaRepository<MessageHistoryEntity,Long> {
}
