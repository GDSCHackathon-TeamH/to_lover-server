package com.tolover.tolover.controller;

import com.tolover.tolover.domain.Advice;
import com.tolover.tolover.service.AdviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdviceController {

    private final AdviceService adviceService;

    @GetMapping("/getRandomAdvice")
    public Advice getRandomAdvice() {
        return adviceService.getRandomAdvice();
    }
}
