package com.github.backend1st.service.mapper;

import com.github.backend1st.repository.likes.LikesEntity;
import com.github.backend1st.web.dto.Likes;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LikesMapper {

    LikesMapper INSTANCE = Mappers.getMapper(LikesMapper.class);
    Likes likeEntityToItem(LikesEntity likesEntity);

}
