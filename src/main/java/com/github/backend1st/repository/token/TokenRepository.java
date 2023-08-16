package com.github.backend1st.repository.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Boolean existsByToken(String token);
    Integer deleteByToken(String token);
}
