package com.tolover.tolover.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResDTO {
    private String resultMessage;
    private Integer userId;
    private String nickname;
    private String jwtAccessToken;
    private String jwtRefreshToken;
}
