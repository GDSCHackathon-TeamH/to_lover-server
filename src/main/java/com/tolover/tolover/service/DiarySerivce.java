package com.tolover.tolover.service;

import com.tolover.tolover.domain.Diary;
import com.tolover.tolover.domain.Todo;
import com.tolover.tolover.domain.UserEntity;
import com.tolover.tolover.dto.user.DiaryListResDTO;
import com.tolover.tolover.dto.user.DiaryRequestDto;
import com.tolover.tolover.dto.user.DiaryResponseDto;
import com.tolover.tolover.dto.user.DiaryTodoListResDTO;
import com.tolover.tolover.repository.DiaryRepository;
import com.tolover.tolover.repository.TodoRepository;
import com.tolover.tolover.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiarySerivce {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final TodoService todoService;

    @Transactional
    public DiaryListResDTO createDiary(DiaryRequestDto diaryRequestDto, Long userId, Long todoId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // DiaryRequestDto에서 직접 todoId를 받아오도록 수정
        Todo todo = todoService.getTodoById(diaryRequestDto.getTodoId());

        Diary newDiary = Diary.builder()
                .body(diaryRequestDto.getBody())
                .user(user)
                .todo(todo)
                .build();

        Diary savedDiary = diaryRepository.save(newDiary);
        return DiaryListResDTO.builder()
                .todoId(savedDiary.getTodo().getId())
                .diaryText(savedDiary.getBody())
                .createdAt(savedDiary.getCreatedAt())
                .build();
    }

    @Transactional
    public List<DiaryResponseDto> getDiariesByUserAndDate(Long userId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<Diary> diaries = diaryRepository.findByUserAndCreatedAtBetween(user, startOfDay, endOfDay);


        return diaries.stream()
                .map(Diary::toDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public List<DiaryTodoListResDTO> getDiariesByUserAndDateAndTodoId(Long userId, LocalDate date, Long todoId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Todo todo = todoService.getTodoById(todoId);

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<Diary> diaries = diaryRepository.findByUserAndCreatedAtAndTodo(user, startOfDay, endOfDay, todo);

        List<DiaryTodoListResDTO> diaryTodoListResDTOS = new ArrayList<>();
        for (Diary diary: diaries){
            diaryTodoListResDTOS.add(new DiaryTodoListResDTO(
                    diary.getTodo().getText(),
                    diary.getBody(),
                    diary.getCreatedAt()
            ));
        }
        return diaryTodoListResDTOS;
//        return diaries.stream()
//                .map(Diary::toDto)
//                .collect(Collectors.toList());
    }

}
