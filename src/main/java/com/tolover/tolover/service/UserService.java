package com.tolover.tolover.service;

import com.tolover.tolover.domain.UserEntity;
import com.tolover.tolover.dto.user.CreateUserReqDTO;
import com.tolover.tolover.dto.user.LoginResDTO;
import com.tolover.tolover.exception.BaseException;
import com.tolover.tolover.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RedisService redisService;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, RedisService redisService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.redisService = redisService;
        this.jwtService = jwtService;
    }

    public UserEntity getUser(Long kakaoId){
        Optional<UserEntity> user = userRepository.findByKakaoId(kakaoId);
        return user.orElse(null);
    }

    @Transactional
    public LoginResDTO login(UserEntity user) throws BaseException {

        // JWT !!!!!
        String jwtAccessToken = jwtService.createAccessToken(user.getId());
        String jwtRefreshToken = jwtService.createRefreshToken(user.getId());

        return new LoginResDTO(
                "로그인에 성공하였습니다.",
                user.getId(),
                user.getNickname(),
                jwtAccessToken,
                jwtRefreshToken
        );
    }


    public LoginResDTO createUser(CreateUserReqDTO createUserReqDTO, long kakaoIdx) {
        UserEntity user = UserEntity.builder()
                        .nickname(createUserReqDTO.getNickname())
                        .kakaoId(kakaoIdx).build();
        UserEntity savedUser = userRepository.save(user);

        String jwtAccessToken = jwtService.createAccessToken(user.getId());
        String jwtRefreshToken = jwtService.createRefreshToken(user.getId());

        return new LoginResDTO(
                "회원가입에 성공하였습니다.",
                savedUser.getId(),
                savedUser.getNickname(),
                jwtAccessToken,
                jwtRefreshToken
        );
    }

    public String temp(long kakaoIdx){
        UserEntity user = getUser(kakaoIdx);
        return jwtService.createAccessToken(user.getId());
    }
}
