package com.tolover.tolover.service;

import com.tolover.tolover.domain.Advice;
import com.tolover.tolover.domain.Todo;
import com.tolover.tolover.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    public Todo getRandomAdvice() {
        List<Todo> todos = todoRepository.findAll();
        if (todos.isEmpty()) {
            addDefaultTOdos();

            todos = todoRepository.findAll();
        }
        if (todos.isEmpty()) {
            throw new RuntimeException("데이터베이스에 조언이 추가되지 않았습니다.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(todos.size());
        return todos.get(randomIndex);
    }

    private void addDefaultTOdos() {
        todoRepository.save(new Todo("천천히 가세요."));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));
        todoRepository.save(new Todo(""));

    }

}
