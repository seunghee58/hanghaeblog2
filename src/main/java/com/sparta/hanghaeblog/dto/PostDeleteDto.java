package com.sparta.hanghaeblog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostDeleteDto { // 게시글 삭제와 관련된 데이터를 전송하기 위한 DTO

    private String msg;

    public void setMsg(String msg){
        this.msg = msg;
    }
}