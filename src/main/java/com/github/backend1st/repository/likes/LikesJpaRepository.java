package com.github.backend1st.repository.likes;

import com.github.backend1st.repository.comments.Comments;
import com.github.backend1st.repository.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LikesJpaRepository extends JpaRepository<LikesEntity,Long> {
    List<LikesEntity> findLikesEntityByMemberAndComment(Member member, Comments comment);
    List<LikesEntity> findByMemberAndComment(Member member, Comments comment);
    Long countByComment(Comments comment);
    boolean existsByMemberAndComment(Member member, Comments comment);
}
