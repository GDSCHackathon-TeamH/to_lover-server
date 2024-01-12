package com.tolover.tolover.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreateUserReqDTO {
    @NotBlank(message = "EMPTY_ACCESS_TOKEN")
    private String accessToken;

    @NotBlank(message = "EMPTY_NICKNAME")
    @Pattern(regexp = "^([ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]{2,12})$", message = "SIGNUP_INVALID_USER_NICKNAME")
    private String nickname;
}
