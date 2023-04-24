package com.sparta.hanghaeblog.entity;

import com.sparta.hanghaeblog.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped { // 타임스탬프는 포스트 entity에 상속이 되어 사용

    @Id // 해당 필드를 엔티티의 기본 키(PK)로 사용함을 나타내는 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //기본 키 생성 방법을 지정하는 어노테이션, strategy 속성을 사용하여 여러방식 중 선택하고 그 중 GenerationType.AUTO를 사용하여 JPA가 알아서 기본 키를 설정하게 함
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id", insertable = true, updatable = true)
    private User user;


    // Post Entity의 생성자. 생성자란 객체를 생성할 때 객체의 초기화를 담당하는 메서드
    // Post 객체를 생성할 때 사용, 객체를 생성하고 저장함으로써 새로운 게시글을 데이터 베이스에 등록할 수 있다.
    public Post(PostRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.user = user;
        this.username = user.getUsername();
    }




    // Post Entity의 필드 값을 해당 객체의 필드 값으로 변경, 즉 게시글을 수정하고 업데이트함
    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();

    }
}
