package com.tolover.tolover.service;

import com.tolover.tolover.domain.Diary;
import com.tolover.tolover.domain.Todo;
import com.tolover.tolover.domain.UserEntity;
import com.tolover.tolover.dto.user.DiaryRequestDto;
import com.tolover.tolover.dto.user.DiaryResponseDto;
import com.tolover.tolover.repository.DiaryRepository;
import com.tolover.tolover.repository.TodoRepository;
import com.tolover.tolover.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiarySerivce {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final TodoService todoService;

    @Transactional
    public DiaryResponseDto createDiary(DiaryRequestDto diaryRequestDto, Long userId, Long todoId){
        UserEntity user=userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Todo todo = todoService.getTodoById(todoId);

        Diary newDiary = Diary.builder()
                .body(diaryRequestDto.getBody())
                .user(user)
                .todo(todo)
                .build();

        Diary savedDiary = diaryRepository.save(newDiary);
        return savedDiary.toDto();
    }

    @Transactional
    public List<DiaryResponseDto> getPostsByUserAndDate(Long userId, LocalDate date) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<Diary> posts = diaryRepository.findByUserAndCreatedAt(user, date);

        // 조회된 게시글을 MyPostResponseDto로 변환하여 반환
        return posts.stream()
                .map(Diary::toDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public List<DiaryResponseDto> getDiariesByUserAndDateAndTodoId(Long userId, LocalDate date, Long todoId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Todo todo = todoService.getTodoById(todoId);

        List<Diary> diaries = diaryRepository.findByUserAndCreatedAtAndTodo(user, date, todo);

        return diaries.stream()
                .map(Diary::toDto)
                .collect(Collectors.toList());
    }
}
