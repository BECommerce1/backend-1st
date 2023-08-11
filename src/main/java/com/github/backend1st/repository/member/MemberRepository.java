package com.github.backend1st.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // TODO : 회원가입 전에 이메일로 유저가 있는지 검사하는 findByEmail(email)

}
