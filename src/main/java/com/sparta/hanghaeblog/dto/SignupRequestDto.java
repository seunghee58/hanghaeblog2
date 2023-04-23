package com.sparta.hanghaeblog.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
public class SignupRequestDto {
    // 회원가입 요청에서 필요한 정보를 담는 DTO

    @Size (min = 4, max = 10)
    private String username;

    @Size (min = 8, max = 15)
    private String password;
}
