package com.example.simple.service;

import com.example.simple.dto.memos.ContentDto;
import com.example.simple.dto.memos.MemoDto;
import com.example.simple.model.Member;
import com.example.simple.model.Memo;
import com.example.simple.repository.MemberRepository;
import com.example.simple.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class MemoService {

    private final MemoRepository memoRepository;
    private final MemberRepository memberRepository;

    public MemoService(MemoRepository memoRepository, MemberRepository memberRepository) {
        this.memoRepository = memoRepository;
        this.memberRepository = memberRepository;
    }

    public Long add(Long memberId, ContentDto contentDto) { // 메모 추가
        Member member = memberRepository.findById(memberId).get();
        Memo memo = new Memo();
        memo.setMember(member);
        memo.setContent(contentDto.getContent());
        memo.setDate(LocalDateTime.now());
        memoRepository.save(memo);
        return memo.getId();
    }

    public Long update(Long id, ContentDto contentDto) { // 메모 수정
        Memo memo = memoRepository.findById(id).get();
        memo.setContent(contentDto.getContent());
        memo.setDate(LocalDateTime.now());
        return id;
    }

    @Transactional(readOnly = true)
    public MemoDto getMemoDtoById(Long id) { // 메모 정보
        return memoRepository.getMemoDtoById(id);
    }

    @Transactional(readOnly = true)
    public List<MemoDto> getMemoDtoList(Long memberId) { // 메모 목록
        return memoRepository.getMemoDtoListByMemberId(memberId);
    }
}