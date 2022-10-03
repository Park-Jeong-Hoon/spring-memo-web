package com.example.simple.service;

import com.example.simple.dto.members.EditDto;
import com.example.simple.dto.members.JoinDto;
import com.example.simple.dto.members.PasswordDto;
import com.example.simple.model.Member;
import com.example.simple.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void save() throws Exception {

        // given
        JoinDto joinDto = new JoinDto("abc", "123", "abc", "abc@com");
        Long id = memberService.join(joinDto);

        // when
        Member member = memberRepository.findById(id).get();

        // then
        Assertions.assertEquals("abc", member.getUsername());
    }

    @Test
    public void validateDuplicate() throws Exception {

        // given
        String username = "abc";
        JoinDto joinDto1 = new JoinDto(username, "123", "abc", "abc@com");
        JoinDto joinDto2 = new JoinDto(username, "1234", "xyz", "xyz@com");

        // when
        memberService.join(joinDto1);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.join(joinDto2);
        });
    }

    @Test
    public void update() throws Exception {

        // given
        JoinDto joinDto = new JoinDto("abc", "123", "abc", "abc@com");
        Long joinId = memberService.join(joinDto);

        // when
        Long updateId = memberService.update(joinId, new EditDto("change", "change@com"));
        Member updated = memberRepository.findById(updateId).get();

        // then
        Assertions.assertEquals(joinId, updated.getId());
        Assertions.assertEquals("change", updated.getName());
    }

    @Test
    public void update_password() throws Exception {

        // given
        JoinDto joinDto = new JoinDto("abc", "123", "abc", "abc@com");
        Long joinId = memberService.join(joinDto);

        // when
        Long updateId = memberService.updatePassword(joinId, new PasswordDto("1234"));
        Member updated = memberRepository.findById(updateId).get();

        // then
        Assertions.assertEquals(joinId, updateId);
        Assertions.assertEquals(bCryptPasswordEncoder.matches("1234", updated.getPassword()), true);
    }
}