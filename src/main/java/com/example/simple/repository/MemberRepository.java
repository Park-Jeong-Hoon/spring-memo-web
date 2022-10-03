package com.example.simple.repository;

import com.example.simple.dto.members.MemberDto;
import com.example.simple.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findMemberByUsername(String username);

    List<Member> findAllByUsername(String username);

    @Query(value = "SELECT new com.example.simple.dto.members.MemberDto(m.username, m.name, m.email) " +
            "FROM Member m " +
            "WHERE m.id = :id")
    MemberDto getMemberDtoById(@Param(value = "id")Long id);
}
