package com.sparta.hanghaeblog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    // 로그인에서 필요한 정보를 담는 DTO
    private String username;
    private String password;
}
