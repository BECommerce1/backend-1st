package com.github.backend1st.service;

import com.github.backend1st.repository.comments.Comments;
import com.github.backend1st.repository.comments.CommentsRepository;
import com.github.backend1st.repository.member.Member;
import com.github.backend1st.repository.member.MemberRepository;
import com.github.backend1st.repository.posts.Posts;
import com.github.backend1st.repository.posts.PostsRepository;
import com.github.backend1st.service.exceptions.NotFoundException;
import com.github.backend1st.web.dto.CommentsRequestDto;
import com.github.backend1st.web.dto.CommentsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final PostsRepository postRepository;
    private final MemberRepository memberRepository;


    // 댓글등록
    public void insertComment(CommentsRequestDto commentsRequestDto, Long memberId){

        Member member = memberRepository.findById(memberId).orElseThrow();
        Posts post =  postRepository.findById(commentsRequestDto.getPostId())
                .orElseThrow(() -> new NotFoundException("댓글 생성 실패!! 해당 게시글은 존재하지 않습니다. ," + commentsRequestDto.getPostId()));
        Comments comment = commentsRequestDto.toEntity();

        Comments saveComment = Comments.insertComment(member, post, comment);
        commentsRepository.save(saveComment);
    }

    // 댓글조회
    @Transactional(readOnly = true)
    public List<CommentsResponseDto> findByCommentList(Long postId){
        Posts posts =  postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("댓글 조회 실패!! 해당 게시글은 존재하지 않습니다. ," + postId));
        List<Comments> commentsList = commentsRepository.findByPostsOrderByCommentIdAsc(posts);
        return commentsList.stream()
                .map(comments -> new CommentsResponseDto(comments))
                .collect(Collectors.toList());
    }


    //댓글 전체조회
    @Transactional(readOnly = true)
    public List<CommentsResponseDto> findByCommentList(){
        List<Comments> commentsList = commentsRepository.findAll();
        return commentsList.stream()
                .map(comments -> new CommentsResponseDto(comments))
                .collect(Collectors.toList());
    }

    // 댓글수정
    public void updateComment(Long commentId, CommentsRequestDto commentsRequestDto){

        Posts posts =  postRepository.findById(commentsRequestDto.getPostId())
                .orElseThrow(() -> new NotFoundException("댓글 수정 실패!! 해당 게시글은 존재하지 않습니다. ," + commentsRequestDto.getPostId()));
        Comments comments  = commentsRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(posts.getPostId()+"번 게시글에는 해당 댓글은 존재하지 않습니다. ," + commentId));

        comments.updateComment(commentsRequestDto.getContent(), commentsRequestDto.toEntity().getUpdateAt());
    }


    // 댓글삭제
    public void deleteComment(Long commentId){
        Comments comments  = commentsRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("댓글 삭제 실패!! 해당 게시글은 존재하지 않습니다. ," + commentId));
        commentsRepository.delete(comments);

    }

}
