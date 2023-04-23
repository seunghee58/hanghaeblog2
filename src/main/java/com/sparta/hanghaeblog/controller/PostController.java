package com.sparta.hanghaeblog.controller;

import com.sparta.hanghaeblog.dto.PostDeleteDto;
import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.entity.Post;
import com.sparta.hanghaeblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // Post 작성 API
    @PostMapping("/api/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto) {  // 객체 형식으로 넘어오기 때문에 RequestBody를 사용
        return postService.createPost(requestDto);
    }

    // 전체 Post 조회 API
    @GetMapping("/api/posts")
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    // 선택 Post 조회 API
    @GetMapping("/api/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // Post 수정 API
    @PutMapping("/api/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        return postService.updatepost(id,postRequestDto, postRequestDto.getPassword());
    }

    // Post 삭제 API
    @DeleteMapping("/api/posts/{id}")
    public PostDeleteDto deletePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        return postService.deletepost(id, postRequestDto.getPassword());
    }
}


// status code;
//