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

    public Todo getRandomTodo() {
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
        todoRepository.save(new Todo("5년 후의 내가 되어 오늘의 나에게 편지 쓰기"));
        todoRepository.save(new Todo("침대에 누워서 눈 감고 제일 좋아하는 노래 듣기"));
        todoRepository.save(new Todo("길 가다 보이는 알록달록한 건물 찍어보기"));
        todoRepository.save(new Todo("소중한 순간들을 회상하며 사진을 정리 해보기"));
        todoRepository.save(new Todo("새로운 레시피를 시도하고 자신만의 요리를 만들어 보기"));
        todoRepository.save(new Todo("새로운 스타일로 머리를 자르거나 옷을 바꿔보기"));
        todoRepository.save(new Todo("주변을 돌아다니며 새로운 장소를 발견해 보기"));
        todoRepository.save(new Todo("밤하늘에 나타난 별들을 관찰해 보기"));
        todoRepository.save(new Todo("복잡한 퍼즐 풀어보기"));
        todoRepository.save(new Todo("명상에 적합한 음악을 찾아 놓고 편안한 자세에서 감상해 보기"));
        todoRepository.save(new Todo("자연 속에서 텐트를 치고 캠핑을 즐겨보기"));
        todoRepository.save(new Todo("미술관이나 갤러리를 방문하거나, 온라인으로 작품을 감상해 보기"));
        todoRepository.save(new Todo("새로운 언어를 배우거나 기존 언어 스킬을 향상시켜 보기"));
        todoRepository.save(new Todo("길 없는 여행을 통해 모르는 동네를 발견해 보기"));
        todoRepository.save(new Todo("시, 소설, 혹은 에세이를 쓰며 창의적인 작업에 도전해 보기"));
        todoRepository.save(new Todo("예전에 읽었던 동화책 다시 읽어보기"));
        todoRepository.save(new Todo("꿈꾸는 여행지를 찾아보고 여행 계획을 세워보기"));
        todoRepository.save(new Todo("선호하는 영화 시리즈나 감독의 작품을 연이어 감상 해보기"));
        todoRepository.save(new Todo("다양한 원두의 커피를 시음하며 커피의 다양성을 경험해 보기"));
        todoRepository.save(new Todo("창문을 열어 신선한 공기를 마시며 주변 풍경을 감상해 보기"));

    }

    public Todo getTodoById(Long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 Todo를 찾을 수 없습니다."));
    }

}
