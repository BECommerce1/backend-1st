package com.github.backend1st.service;

import com.github.backend1st.repository.member.Member;
import com.github.backend1st.repository.member.MemberRepository;
import com.github.backend1st.repository.posts.Posts;
import com.github.backend1st.repository.posts.PostsRepository;
import com.github.backend1st.service.exceptions.NotFoundException;
import com.github.backend1st.web.dto.PostsRequestDto;
import com.github.backend1st.web.dto.PostsResponseDto;
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
public class PostsService {

    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;


    // 게시글 등록
    public void insertPost(PostsRequestDto postsRequestDto, Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("게시글 등록 실패!! 해당회원은 존재하지 않습니다."));
        Posts insertPost = postsRequestDto.toEntity(member);
        postsRepository.save(insertPost);
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long postId){
        Posts posts =  postsRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다. ,"+ postId ));
        return new PostsResponseDto(posts);
    }


    // 게시글 목록 조회
    @Transactional(readOnly = true)
    public List<PostsResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(posts -> new PostsResponseDto(posts)).collect(Collectors.toList());
        //return postsRepository.findAll(Sort.by(Sort.Direction.DESC, "post_id"));
    }

    // 게시글 수정
    public void updatePost(PostsRequestDto postsRequestDto, Long memberId, Long postId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("게시글 수정 실패!! 해당회원은 존재하지 않습니다."));
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글 수정 실패!! 해당 게시글은 존재하지 않습니다. ," + postId));
        post.updatePost(postsRequestDto.getTitle(), postsRequestDto.getContent());
    }

    // 게시글 삭제
    public void deletePost(Long postId){
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글 삭제 실패!! 해당 게시글은 존재하지 않습니다. ," + postId));
        postsRepository.delete(post);
    }

}
