package com.github.backend1st.repository.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LikesJpaRepository extends JpaRepository<LikesEntity,Long> {


    List<LikesEntity> findLikesByMemberIdAndReplyId(Long memberId, Long replyId);

    long countByReplyId(Long replyId);

    boolean existsByMemberIdAndReplyId(Long memberId, Long replyId);
}
