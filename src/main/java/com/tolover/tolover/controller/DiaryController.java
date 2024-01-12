package com.tolover.tolover.controller;

import com.tolover.tolover.domain.Diary;
import com.tolover.tolover.dto.user.DiaryListResDTO;
import com.tolover.tolover.dto.user.DiaryRequestDto;
import com.tolover.tolover.dto.user.DiaryResponseDto;
import com.tolover.tolover.dto.user.DiaryTodoListResDTO;
import com.tolover.tolover.exception.BaseException;
import com.tolover.tolover.service.DiarySerivce;
import com.tolover.tolover.service.JwtService;
import com.tolover.tolover.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class DiaryController {

    private final DiarySerivce diarySerivce;
    private final JwtService jwtService;
    private final TodoService todoService;
    @PostMapping("/save")
<<<<<<< HEAD
    public ResponseEntity<DiaryListResDTO> createPost(@RequestBody DiaryRequestDto diaryRequestDto) throws BaseException {
=======
    public String createPost(@RequestBody DiaryRequestDto diaryRequestDto) throws BaseException {


>>>>>>> 494b017473415f58388a737d00f5b3942382a1aa
        String body = diaryRequestDto.getBody();
        Long userId = jwtService.getUserIdx();
        Long todoId = diaryRequestDto.getTodoId();

        // DiaryResponseDto createdPost = diarySerivce.createDiary(diaryRequestDto, userId, todoId);
       //String createdAt = createdPost.getCreatedAt().toString();
        //return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        DiaryListResDTO createdPost = diarySerivce.createDiary(diaryRequestDto, userId, todoId);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);

    }
    @GetMapping("/findByUserAndDate")
    public ResponseEntity<List<DiaryResponseDto>> getPostsByUserAndDate(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) throws BaseException {
        try {
            Long userId = jwtService.getUserIdx();
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

            List<DiaryResponseDto> diaries = diarySerivce.getDiariesByUserAndDate(userId, date);

            return new ResponseEntity<>(diaries, HttpStatus.OK);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    @GetMapping("/findByUserAndDateAndTodoId")
    public ResponseEntity<List<DiaryTodoListResDTO>> getDiariesByUserAndDateAndTodoId(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Long todoId) throws BaseException {
        try {
            Long userId = jwtService.getUserIdx();

            List<DiaryTodoListResDTO> diaries = diarySerivce.getDiariesByUserAndDateAndTodoId(userId, date, todoId);

            return new ResponseEntity<>(diaries, HttpStatus.OK);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

  //  @GetMapping("/user/{userId}")
   // public List<Diary> getDiariesByUserId(@PathVariable Long userId) {
   //     return diarySerivce.getDiariesByUserId(userId);
  //  }
}
