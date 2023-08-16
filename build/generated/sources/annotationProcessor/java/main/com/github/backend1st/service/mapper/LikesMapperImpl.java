package com.github.backend1st.service.mapper;

import com.github.backend1st.repository.likes.LikesEntity;
import com.github.backend1st.web.dto.Likes;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-16T23:59:39+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 11.0.11 (AdoptOpenJDK)"
)
public class LikesMapperImpl implements LikesMapper {

    @Override
    public Likes likeEntityToItem(LikesEntity likesEntity) {
        if ( likesEntity == null ) {
            return null;
        }

        Likes likes = new Likes();

        return likes;
    }
}
