package com.tolover.tolover.repository;

import com.tolover.tolover.domain.Diary;
import com.tolover.tolover.domain.Todo;
import com.tolover.tolover.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("SELECT mp FROM Diary mp WHERE mp.user = :user AND DATE(mp.createdAt) = :date")
    List<Diary> findByUserAndCreatedAt(@Param("user") UserEntity user, @Param("date") LocalDate date);

    List<Diary> findByUserAndCreatedAtAndTodo(UserEntity user, LocalDate createdAt, Todo todo);
}
