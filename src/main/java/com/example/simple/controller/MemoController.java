package com.example.simple.controller;

import com.example.simple.auth.PrincipalDetails;
import com.example.simple.dto.memos.ContentDto;
import com.example.simple.dto.memos.MemoDto;
import com.example.simple.service.MemoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemoController {

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping(value = "/memos/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        MemoDto memoInfo = memoService.getMemoDtoById(id);
        model.addAttribute("memoInfo", memoInfo);
        return "memos/memo";
    }

    @GetMapping(value = "/memos")
    public String findAll(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        List<MemoDto> memoList = memoService.getMemoDtoList(principalDetails.getMember().getId());
        model.addAttribute("memoList", memoList);
        return "memos/memoList";
    }

    @GetMapping(value = "/memos/new")
    public String getCreate() {
        return "memos/createMemo";
    }

    @PostMapping(value = "/memos/new")
    public String postCreate(@AuthenticationPrincipal PrincipalDetails principalDetails, ContentDto contentDto) {
        memoService.add(principalDetails.getMember().getId(), contentDto);
        return "redirect:/memos";
    }

    @GetMapping(value = "/memos/{id}/edit")
    public String getEdit(@PathVariable("id") Long id, Model model) {
        MemoDto memoInfo = memoService.getMemoDtoById(id);
        model.addAttribute("memoInfo", memoInfo);
        return "memos/editMemo";
    }

    @PostMapping(value = "/memos/{id}/edit")
    public String postEdit(@PathVariable("id") Long id, ContentDto contentDto) {
        memoService.update(id, contentDto);
        return "redirect:/memos";
    }
}