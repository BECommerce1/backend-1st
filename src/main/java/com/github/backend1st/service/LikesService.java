package com.github.backend1st.service;

import com.github.backend1st.repository.comments.Comments;
import com.github.backend1st.repository.comments.CommentsRepository;
import com.github.backend1st.repository.likes.LikesEntity;
import com.github.backend1st.repository.likes.LikesJpaRepository;
import com.github.backend1st.repository.member.Member;
import com.github.backend1st.repository.member.MemberRepository;
import com.github.backend1st.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LikesService {
    private final LikesJpaRepository likesJpaRepository;
    private final MemberRepository memberRepository;
    private final CommentsRepository commentsRepository;

    // 좋아요
    public void savaItem(Long memberId, Long commentId) {
        Comments comments = commentsRepository.findById(commentId)
                .orElseThrow(()-> new NotFoundException(commentId + " 번의 댓글은 존재하지 않습니다."));
        Member member = memberRepository
                .findById(memberId).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));

        List<LikesEntity> likesEntities = likesJpaRepository.findLikesEntityByMemberAndComment(member, comments);
        if(!likesEntities.isEmpty()){
            throw new NotFoundException("같은 값은 입력될 수 없습니다.");
        }
        likesJpaRepository.save(new LikesEntity(member,comments));
    }

    // 좋아요 취소
    public void deleteLike(Long memberId, Long commentId) {
        Comments comments = commentsRepository.findById(commentId)
                .orElseThrow(()-> new NotFoundException(commentId + " 번의 댓글은 존재하지 않습니다."));
        Member member = memberRepository
                .findById(memberId).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));

        List<LikesEntity> likes =  likesJpaRepository.findByMemberAndComment(member,comments);
        List<Long> likeIds = likes.stream()
                .map(likeEntity -> likeEntity.getLikeId())
                .collect(Collectors.toList());

        //입력된 값 확인 처리
        if(likeIds.get(0) == 0){
            throw new NotFoundException("결과 값이 없어 삭제 불가능 합니다.");
        }
        likesJpaRepository.deleteById(likeIds.get(0));
    }

    public long countLike(Long commentId) {
        Comments comments = commentsRepository.findById(commentId)
                .orElseThrow(()-> new NotFoundException(commentId + " 번의 댓글은 존재하지 않습니다."));
        return likesJpaRepository.countByComment(comments);
    }

    // 좋아요 클릭 여부
    public boolean existsByMemberIdAndReplyId(Long memberId, Long commentId) {
        Comments comments = commentsRepository.findById(commentId)
                .orElseThrow(()-> new NotFoundException(commentId + " 번의 댓글은 존재하지 않습니다."));
        Member member = memberRepository
                .findById(memberId).orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));
        return likesJpaRepository.existsByMemberAndComment(member, comments);
    }
}
