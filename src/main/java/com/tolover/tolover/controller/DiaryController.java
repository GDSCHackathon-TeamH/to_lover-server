package com.tolover.tolover.controller;

import com.tolover.tolover.dto.user.DiaryRequestDto;
import com.tolover.tolover.dto.user.DiaryResponseDto;
import com.tolover.tolover.service.DiarySerivce;
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

    @PostMapping("/save")
    public ResponseEntity<DiaryResponseDto> createPost(@RequestBody DiaryRequestDto diaryRequestDto, Principal principal) {
        try {
            Long userId = Long.parseLong(principal.getName());

            DiaryResponseDto createdPost = diarySerivce.createDiary(diaryRequestDto, userId);

            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
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
}
