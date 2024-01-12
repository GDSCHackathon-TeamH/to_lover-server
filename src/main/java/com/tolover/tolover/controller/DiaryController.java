package com.tolover.tolover.controller;

import com.tolover.tolover.dto.user.DiaryRequestDto;
import com.tolover.tolover.dto.user.DiaryResponseDto;
import com.tolover.tolover.exception.BaseException;
import com.tolover.tolover.service.DiarySerivce;
import com.tolover.tolover.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class DiaryController {

    private final DiarySerivce diarySerivce;
    private final JwtService jwtService;
    @PostMapping("/save")
    public ResponseEntity<DiaryResponseDto> createPost(@RequestBody DiaryRequestDto diaryRequestDto) throws BaseException {
            Long userId = jwtService.getUserIdx();
            Long todoId = diaryRequestDto.getTodoId();

            DiaryResponseDto createdPost = diarySerivce.createDiary(diaryRequestDto, userId, todoId);

            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);

    }
    @GetMapping("/findByUserAndDate")
    public ResponseEntity<List<DiaryResponseDto>> getPostsByUserAndDate(Principal principal,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            Long userId = Long.parseLong(principal.getName());

            List<DiaryResponseDto> diarys = diarySerivce.getPostsByUserAndDate(userId, date);

            return new ResponseEntity<>(diarys, HttpStatus.OK);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    @GetMapping("/findByUserAndDateAndTodoId")
    public ResponseEntity<List<DiaryResponseDto>> getDiariesByUserAndDateAndTodoId(Principal principal,
                                                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                                                   @RequestParam Long todoId) {
        try {
            Long userId = Long.parseLong(principal.getName());

            List<DiaryResponseDto> diaries = diarySerivce.getDiariesByUserAndDateAndTodoId(userId, date, todoId);

            return new ResponseEntity<>(diaries, HttpStatus.OK);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
