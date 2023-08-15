package com.github.backend1st.repository.likes;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "like_id")
@ToString
@Builder
@Entity
@Table(name = "likes")
public class LikesEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "reply_id")
    private Long replyId;

    @Column(name = "created_time")
    private LocalDateTime createTime;

    public LikesEntity(Long likeId, Long memberId, Long replyId, LocalDateTime createTime) {
        this.likeId = likeId;
        this.memberId = memberId;
        this.replyId = replyId;
        this.createTime = createTime;
    }
}
