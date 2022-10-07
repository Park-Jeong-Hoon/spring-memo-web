package com.example.simple.service;

import com.example.simple.dto.members.EditDto;
import com.example.simple.dto.members.JoinDto;
import com.example.simple.dto.members.MemberDto;
import com.example.simple.dto.members.PasswordDto;
import com.example.simple.model.Member;
import com.example.simple.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    public MemberService(BCryptPasswordEncoder bCryptPasswordEncoder, MemberRepository memberRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.memberRepository = memberRepository;
    }

    public Long join(JoinDto joinDto) { // 회원가입
        List<Member> members = memberRepository.findAllByUsername(joinDto.getUsername());

        if (!members.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        Member member = new Member();
        member.setUsername(joinDto.getUsername());
        member.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
        member.setName(joinDto.getName());
        member.setEmail(joinDto.getEmail());
        member.setRole("ROLE_USER");
        member.setCreateDate(LocalDateTime.now());
        memberRepository.save(member);
        return member.getId();
    }

    public Long update(Long id, EditDto editDto) { // 회원 정보 수정
        Member member = memberRepository.findById(id).get();
        member.setName(editDto.getName());
        member.setEmail(editDto.getEmail());
        return member.getId();
    }

    public Long updatePassword(Long id, PasswordDto passwordDto) { // 회원 비밀번호 변경
        Member member = memberRepository.findById(id).get();
        member.setPassword(bCryptPasswordEncoder.encode(passwordDto.getPassword()));
        return member.getId();
    }

    public MemberDto getMemberDtoById(Long id) { // 회원 정보
        return memberRepository.getMemberDtoById(id);
    }
}