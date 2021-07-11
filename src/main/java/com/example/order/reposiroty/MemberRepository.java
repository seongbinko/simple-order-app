package com.example.order.reposiroty;

import com.example.order.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByNickname(String nickname);

    boolean existsByNickname(String nickName);
}
