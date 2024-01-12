package com.tolover.tolover.controller;

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
    public BaseResponse<Integer> login(@RequestBody KakaoLoginReqDTO kakaoLoginReqDTO){

        try{
            long kakaoIdx = kakaoService.checkKakaoUser(kakaoLoginReqDTO.getAccessToken());

            //만약 DB에 이미 존재하는 유저라면 로그인



            //존재하지 않는다면 회원가입
//            LoginResDTO result = userService.loginUser(kakaoIdx);
//            return new BaseResponse<>(result);

        }catch (BaseException e){
            log.error(" API : api/login" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }


//        try {
//            // 사용자 생성
//            long kakaoIdx = kakaoService.checkKakaoUser(createUserReqDTO.getAccessToken());
//            KakaoMemberCheckResDTO kakaoMemberCheckResDTO = userService.checkKakoMember(kakaoIdx);
//            if(kakaoMemberCheckResDTO.getIsMember()==true){
//                return new BaseResponse<>(SIGNUP_ALREADY_EXIST_KAKAO_MEMBER);
//            }
//
//            if (kakaoIdx != 0) {
//                CreateUserResDTO result = userService.createUser(createUserReqDTO,kakaoIdx);
//                return new BaseResponse<>(result);
//            }else {
//                return new BaseResponse<>(INVALID_ACCESS_KAKAO);
//            }
//
//        } catch (BaseException e) {
//            log.error(" API : api/signup" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
//            return new BaseResponse<>(e.getStatus());
//        }
        return new BaseResponse<>(1);
    }
}
