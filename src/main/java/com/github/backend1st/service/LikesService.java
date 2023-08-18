package com.github.backend1st.service;

import com.github.backend1st.repository.likes.LikesEntity;
import com.github.backend1st.repository.likes.LikesJpaRepository;
import com.github.backend1st.service.exceptions.NotFoundException;
import com.github.backend1st.service.mapper.LikesMapper;
import com.github.backend1st.web.dto.Likes;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LikesService {
    private final LikesJpaRepository likesJpaRepository;
    public String savaItem(Long memberId, Long replyId) {
        List<LikesEntity> likesEntities =  likesJpaRepository.findLikesByMemberIdAndReplyId(memberId,replyId);
        //likesEntities.stream().map(LikesMapper.INSTANCE::likeEntityToItem).collect(Collectors.toList())
        if(!likesEntities.isEmpty()){
            throw new NotFoundException("같은 값은 입력될 수 없습니다.");
        }
        LocalDateTime now = LocalDateTime.now();
        //값이 없으면 likes테이블에 입력
        LikesEntity likes1 = new LikesEntity();
        likes1.setReplyId(replyId);
        likes1.setMemberId(memberId);
        likes1.setCreateTime(now);
        likesJpaRepository.save(likes1);
        return "입력완료";
    }

    public Long deleteLike(Long memberId, Long replyId) {
        if (memberId == null || replyId == null) {
            throw new IllegalArgumentException("입력된 정보가 없습니다.");
        }
        //memberId,replyId의 값으로 like_no값 호출 하기
        List<LikesEntity> likesEntities =  likesJpaRepository.findLikesByMemberIdAndReplyId(memberId,replyId);
        List<Long> likeIds = likesEntities.stream()
                .map(likeEntity -> likeEntity.getLikeId())
                .collect(Collectors.toList());
        //입력된 값 확인 처리
        if(likeIds.get(0) == 0){
            throw new NotFoundException("결과 값이 없어 삭제 불가능 합니다.");
        }
        //삭제 처리
        likesJpaRepository.deleteById(likeIds.get(0));
        return likeIds.get(0);
    }

    public long countLike(Long replyId) {
        return likesJpaRepository.countByReplyId(replyId);
    }

    public boolean existsByMemberIdAndReplyId(Long memberId, Long replyId) {
        if (memberId == null || replyId == null) {
            throw new IllegalArgumentException("입력된 정보가 없습니다.");
        }
        return likesJpaRepository.existsByMemberIdAndReplyId(memberId, replyId);
    }
}
