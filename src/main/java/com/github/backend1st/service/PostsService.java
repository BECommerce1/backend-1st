package com.github.backend1st.service;

import com.github.backend1st.repository.posts.Posts;
import com.github.backend1st.repository.posts.PostsRepository;
import com.github.backend1st.service.exceptions.NotFoundException;
import com.github.backend1st.web.dto.PostsRequestDto;
import com.github.backend1st.web.dto.PostsResponseDto;
import com.github.backend1st.web.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    // 게시글 등록
    public void insertPost(PostsRequestDto postsRequestDto){
        Posts insertPost = postsRequestDto.toEntity();
        ResponseDto responseDto = ResponseDto.builder().status(HttpStatus.OK.toString()).message("게시물이 성공적으로 작성되었습니다.").build();
        postsRepository.save(insertPost);
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long postId){
        Posts posts =  postsRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다. ,"+ postId ));
        return new PostsResponseDto(posts);



    }
}
