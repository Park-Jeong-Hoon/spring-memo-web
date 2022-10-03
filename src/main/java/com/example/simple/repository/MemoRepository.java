package com.example.simple.repository;

import com.example.simple.dto.memos.MemoDto;
import com.example.simple.model.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {

    @Query(value = "SELECT new com.example.simple.dto.memos.MemoDto(m.id, m.content) " +
            "FROM Memo m " +
            "WHERE m.id = :memoId")
    MemoDto getMemoDtoById(@Param(value = "memoId") Long memoId);

    @Query(value = "SELECT new com.example.simple.dto.memos.MemoDto(m.id, m.content) " +
            "FROM Memo m " +
            "WHERE m.member.id = :memberId")
    List<MemoDto> getMemoDtoListByMemberId(@Param(value = "memberId") Long memberId);
}