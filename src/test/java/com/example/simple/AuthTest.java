package com.example.simple;

import com.example.simple.dto.members.JoinDto;
import com.example.simple.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AuthTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    @BeforeEach
    void beforeEach() {
        JoinDto joinDto = new JoinDto("abc", "123", "abc", "abc@com");
        memberService.join(joinDto);
    }

    @Test
    public void login_success() throws Exception{

        mockMvc.perform(formLogin().user("abc").password("123"))
                .andDo(print())
                .andExpect(authenticated());
    }

    @Test
    public void login_fail() throws Exception{

        mockMvc.perform(formLogin().user("abc").password("1234"))
                .andDo(print())
                .andExpect(unauthenticated());
    }
}