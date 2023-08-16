package com.github.backend1st.repository.comments;
import com.github.backend1st.repository.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByPostsOrderByCommentIdAsc(Posts posts);
}
