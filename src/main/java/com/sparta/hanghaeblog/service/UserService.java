package com.sparta.hanghaeblog.service;

import com.sparta.hanghaeblog.dto.LoginRequestDto;
import com.sparta.hanghaeblog.dto.SignupRequestDto;
import com.sparta.hanghaeblog.entity.User;
import com.sparta.hanghaeblog.jwt.JwtUtil;
import com.sparta.hanghaeblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public String signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        // username은 알파벳 소문자, 숫자로 4-10자리
        if(!Pattern.matches("^[a-z0-9]{4,10}$", username)) {
            return "아이디는 알파벳 소문자, 숫자로 작성해주세요.";
        }
        // password는 알파벳 대소문자, 숫자로 8-15자리
        if(!Pattern.matches("^[a-zA-Z0-9]{8,15}$", password)) {
            return "비밀번호는 알파벳 대소문자, 숫자로 작성해주세요.";
        }
        else {
            // 회원 중복 확인
            Optional<User> found = userRepository.findByUsername(username);
            if (found.isPresent()) {
                throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
            }

            User user = new User(username, password);
            userRepository.save(user);
            return "회원가입 성공";
        }
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));
    }
}