package com.github.backend1st.repository.comments;


import com.github.backend1st.repository.member.Member;
import com.github.backend1st.repository.posts.Posts;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Comment")
@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId; //댓글아이디

    @Column(nullable = false, length = 100)
    private String content; // 댓글내용

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createAt; // 작성일자

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updateAt; // 수정일자


    // 연관관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Posts posts;


    @Builder
    public Comments(String content, LocalDateTime createAt, LocalDateTime updateAt, Posts posts, Member member) {
        this.content = content;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.posts = posts;
        this.member = member;
    }

    public void updateComment(String content, LocalDateTime updateAt){
        this.content = content;
        this.updateAt = updateAt;
    }


    public static Comments insertComment(Member member, Posts posts, Comments comments){
        return Comments.builder()
                .content(comments.getContent())
                .member(member)
                .posts(posts)
                .build();
    }








}
