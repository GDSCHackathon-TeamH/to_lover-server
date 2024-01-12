package com.tolover.tolover.controller;

import com.tolover.tolover.domain.Todo;
import com.tolover.tolover.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/getRandomTodo")
    public Todo getRandomTodo() {
        return todoService.getRandomTodo();
    }
}

