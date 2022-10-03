package com.example.simple.service;

import com.example.simple.dto.members.JoinDto;
import com.example.simple.dto.memos.ContentDto;
import com.example.simple.model.Memo;
import com.example.simple.repository.MemoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class MemoServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemoService memoService;
    @Autowired MemoRepository memoRepository;

    @Test
    public void create_memo() throws Exception {

        // given
        Long memberId = joinMember();

        // when
        Long memoId = memoService.add(memberId, new ContentDto("hello"));

        // then
        Memo memo = memoRepository.findById(memoId).get();
        Assertions.assertEquals(memo.getId(), memoId);
        Assertions.assertEquals(memo.getMember().getId(), memberId);
    }

    @Test
    public void update_memo() throws Exception {

        // given
        Long memberId = joinMember();
        Long memoId = memoService.add(memberId, new ContentDto("hello"));

        // when
        Long changeId = memoService.update(memoId, new ContentDto("change"));

        // then
        Memo memo = memoRepository.findById(changeId).get();
        Assertions.assertEquals(memo.getContent(), "change");
    }

    private Long joinMember() {
        JoinDto joinDto = new JoinDto("abc", "123", "abc", "abc@com");
        Long memberId = memberService.join(joinDto);
        return memberId;
    }
}
