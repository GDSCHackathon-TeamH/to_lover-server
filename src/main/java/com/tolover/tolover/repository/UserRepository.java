package com.tolover.tolover.repository;

import com.tolover.tolover.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByKakaoId(Long kakaoId);
}
