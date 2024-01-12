package com.tolover.tolover.repository;

import com.tolover.tolover.domain.Diary;
import com.tolover.tolover.domain.Todo;
import com.tolover.tolover.domain.UserEntity;
import com.tolover.tolover.dto.user.DiaryResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT d FROM Diary d WHERE d.user = :user AND d.createdAt BETWEEN :startOfDay AND :endOfDay")
    List<Diary> findByUserAndCreatedAtBetween(@Param("user") UserEntity user,
                                              @Param("startOfDay") LocalDateTime startOfDay,
                                              @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT d FROM Diary d WHERE d.user = :user AND d.createdAt BETWEEN :startOfDay AND :endOfDay AND d.todo = :todo")
    List<Diary> findByUserAndCreatedAtAndTodo(@Param("user") UserEntity user,
                                              @Param("startOfDay") LocalDateTime startOfDay,
                                              @Param("endOfDay") LocalDateTime endOfDay,
                                              @Param("todo") Todo todo);
    List<Diary> findByUserId(Long userId);
}
