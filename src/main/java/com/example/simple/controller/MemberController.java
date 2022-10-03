package com.example.simple.controller;

import com.example.simple.auth.PrincipalDetails;
import com.example.simple.dto.members.EditDto;
import com.example.simple.dto.members.JoinDto;
import com.example.simple.dto.members.MemberDto;
import com.example.simple.dto.members.PasswordDto;
import com.example.simple.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    private MemberService memberService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberController(MemberService memberService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberService = memberService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/join")
    public String getJoin() {
        return "members/join";
    }

    @PostMapping("/join")
    public String postJoin(JoinDto joinDto) {
        try {
            memberService.join(joinDto);
        } catch (Exception e) {
            return "members/join";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "members/login";
    }

    @GetMapping("/members/profile")
    public String profile(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        MemberDto memberInfo = memberService.getMemberDtoById(principalDetails.getMember().getId());
        model.addAttribute("memberInfo", memberInfo);
        return "members/profile";
    }

    @GetMapping("/members/edit")
    public String getEdit(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        MemberDto memberInfo = memberService.getMemberDtoById(principalDetails.getMember().getId());
        model.addAttribute("memberInfo", memberInfo);
        return "members/editMember";
    }

    @PostMapping("/members/edit")
    public String postEdit(@AuthenticationPrincipal PrincipalDetails principalDetails, EditDto editDto) {
        principalDetails.getMember().setName(editDto.getName());
        principalDetails.getMember().setEmail(editDto.getEmail());
        memberService.update(principalDetails.getMember().getId(), editDto);
        return "redirect:/members/profile";
    }

    @GetMapping("/members/password")
    public String getEditPassword() {
        return "members/editPassword";
    }

    @PostMapping("/members/password")
    public String postEditPassword(@AuthenticationPrincipal PrincipalDetails principalDetails, PasswordDto passwordDto) {
        principalDetails.getMember().setPassword(bCryptPasswordEncoder.encode(passwordDto.getPassword()));
        memberService.updatePassword(principalDetails.getMember().getId(), passwordDto);
        return "redirect:/members/profile";
    }
}
