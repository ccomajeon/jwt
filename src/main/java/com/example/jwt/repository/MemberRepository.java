package com.example.jwt.repository;

import com.example.jwt.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findByMemberName(String memberName);
}
