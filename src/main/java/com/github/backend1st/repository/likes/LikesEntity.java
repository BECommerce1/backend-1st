package com.github.backend1st.repository.likes;

import com.github.backend1st.repository.comments.Comments;
import com.github.backend1st.repository.member.Member;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "likes")

public class LikesEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comments comment;

    @Column(name = "created_time")
    @CreationTimestamp
    private LocalDateTime createTime;

    @Builder
    public LikesEntity(Member member, Comments comment) {
        this.member = member;
        this.comment = comment;
    }
}
