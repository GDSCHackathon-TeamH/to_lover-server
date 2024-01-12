package com.tolover.tolover.service;

import com.tolover.tolover.domain.Advice;
import com.tolover.tolover.repository.AdviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AdviceService {
    @Autowired
    private AdviceRepository adviceRepository;

    public Advice getRandomAdvice() {
        List<Advice> advices = adviceRepository.findAll();
        if (advices.isEmpty()) {
            addDefaultAdvices();

            advices = adviceRepository.findAll();
        }
        if (advices.isEmpty()) {
            throw new RuntimeException("데이터베이스에 조언이 추가되지 않았습니다.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(advices.size());
        return advices.get(randomIndex);
    }

    private void addDefaultAdvices() {
        adviceRepository.save(new Advice("내가 둘 중 더 가슴 아파하는 사람이라도 괜찮아요."));
        adviceRepository.save(new Advice("과식과 폭음, 쇼핑, 여러 사람들과의 수많은 데이트들이 당신의 기분을 좋게 만들게 해줄 거라 믿지 말아요."));
        adviceRepository.save(new Advice("내가 둘 중 더 가슴 아파하는 사람이라도 괜찮아요."));
        adviceRepository.save(new Advice("이별을 위한 완벽한 순간을 기다리지 말아요."));
        adviceRepository.save(new Advice("먼저 자기 자신부터 사랑해봐요."));
        adviceRepository.save(new Advice("완벽한 사랑도, 완벽한 인연도 없어요."));
        adviceRepository.save(new Advice("헤어진 사람은 당신에게 어울리는 사람이 아니에요."));
        adviceRepository.save(new Advice("만나고, 알고, 사랑하고, 그리고 이별하는 것이 우리 인간의 공통된 슬픈 이야기 입니다."));
        adviceRepository.save(new Advice("싱글을 즐기세요."));
        adviceRepository.save(new Advice("나가서 걸어봐요."));
        adviceRepository.save(new Advice("태어난 모든 것들은 기약조차 없는 이별을 준비하고 있어야 해요."));
        adviceRepository.save(new Advice("이별의 슬픔 속에서만 사랑의 깊이를 알게 됩니다."));
        adviceRepository.save(new Advice("사랑하는 사람과 어떻게 헤어지게 되었을까 후회하는 것만큼 깊은 상처는 없어요"));
        adviceRepository.save(new Advice("사랑을 잃었을 때 치료법은 더욱 사랑하는 것밖에 없는것 같아요"));
        adviceRepository.save(new Advice("아름다운 이별은 없어요. 다만 아름답게 사랑한 후에는 좋은 추억이 남아요."));
        adviceRepository.save(new Advice("이별은 가혹한 고통이지만 잘 견뎌내기만 하면 또 다른 사랑, 또 다른 행복의 문으로 들어갈 수 있어요."));
        adviceRepository.save(new Advice(""));
        adviceRepository.save(new Advice(""));
        adviceRepository.save(new Advice(""));
        adviceRepository.save(new Advice(""));
    }
}
