package com.sparta.hanghaeblog.service;

import com.sparta.hanghaeblog.dto.PostDeleteDto;
import com.sparta.hanghaeblog.dto.PostRequestDto;
import com.sparta.hanghaeblog.dto.PostResponseDto;
import com.sparta.hanghaeblog.entity.Post;
import com.sparta.hanghaeblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository; // 데이터베이스와 연결

    // Post 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    // 전체 Post 조회
    @Transactional
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    } // 내림차순 정렬

    // 선택 Post 조회
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 일치하지 않습니다."));
        return new PostResponseDto(post);
    }



    // Post 수정
    @Transactional
    public PostResponseDto updatepost(Long id, PostRequestDto postRequestDto, String password) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        PostResponseDto postResponseDto = new PostResponseDto(post);
        if (post.getPassword().equals(password)) {
            post.update(postRequestDto);
            postRepository.save(post);
            return new PostResponseDto(post);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    // Post 삭제
    @Transactional
    public PostDeleteDto deletepost (Long id, String password) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        PostDeleteDto postDeleteDto = new PostDeleteDto();
        if (post.getPassword().equals(password)) {
            postRepository.deleteById(id);
            postDeleteDto.setMsg("게시글 삭제가 완료되었습니다.");
        } else {
            postDeleteDto.setMsg("비밀번호가 일치하지 않습니다");
        }

        return postDeleteDto;

    }


}