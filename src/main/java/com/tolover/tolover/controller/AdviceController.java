package com.tolover.tolover.controller;

import com.tolover.tolover.domain.Advice;
import com.tolover.tolover.service.AdviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdviceController {

    private final AdviceService adviceService;

    @GetMapping("/getRandomAdvice")
    public ResponseEntity<List<Advice>> getRandomAdvice() {
        List<Advice> advices = adviceService.getRandomAdvice(3);
        return new ResponseEntity<>(advices, HttpStatus.OK);
    }
}
