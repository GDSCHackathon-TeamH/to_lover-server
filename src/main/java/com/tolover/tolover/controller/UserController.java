package com.tolover.tolover.controller;

import com.tolover.tolover.domain.UserEntity;
import com.tolover.tolover.dto.user.CreateUserReqDTO;
import com.tolover.tolover.dto.user.KakaoLoginReqDTO;
import com.tolover.tolover.dto.user.LoginResDTO;
import com.tolover.tolover.exception.BaseException;
import com.tolover.tolover.exception.BaseResponse;
import com.tolover.tolover.service.KakaoService;
import com.tolover.tolover.service.UserService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tolover.tolover.exception.BaseResponseStatus.INVALID_ACCESS_KAKAO;
import static com.tolover.tolover.exception.BaseResponseStatus.SIGNUP_ALREADY_EXIST_KAKAO_MEMBER;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    public UserController(UserService userService, KakaoService kakaoService) {
        this.userService = userService;
        this.kakaoService = kakaoService;
    }

    @PostMapping("/login")
    public BaseResponse<LoginResDTO> login(@RequestBody KakaoLoginReqDTO kakaoLoginReqDTO){

        try{
            long kakaoIdx = kakaoService.checkKakaoUser(kakaoLoginReqDTO.getAccessToken());

            UserEntity user = userService.getUser(kakaoIdx);
            //만약 DB에 이미 존재하는 유저라면 로그인
            if(user != null){
                LoginResDTO result = userService.login(user);
                return new BaseResponse<>(result);
            }
            else//존재 하지 않는다면 회원가입 요청
            {
                LoginResDTO result = LoginResDTO.builder()
                        .resultMessage("회원가입 필요")
                        .build();
                return new BaseResponse<>(result);
            }

        }catch (BaseException e){
            log.error(" API : api/login" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }

    }

    @PostMapping("/signup")
    public BaseResponse<LoginResDTO> signUp(@RequestBody CreateUserReqDTO createUserReqDTO){

        try{
            long kakaoIdx = kakaoService.checkKakaoUser(createUserReqDTO.getAccessToken());
            //존재 하지 않는다면 회원가입 요청
            LoginResDTO result = userService.createUser(createUserReqDTO, kakaoIdx);
            return new BaseResponse<>(result);

        }catch (BaseException e){
            log.error(" API : api/login" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }

    }

}
