package Gdsc.web.member.controller;

import Gdsc.web.annotation.WithCustomMockUser;
import Gdsc.web.common.MemberEntityFactory;
import Gdsc.web.controller.AbstractControllerTest;
import Gdsc.web.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberApiControllerTest extends AbstractControllerTest {
    @Autowired
    private  WebApplicationContext context;
    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
        memberRepository.save(MemberEntityFactory.memberEntity());
        memberRepository.save(MemberEntityFactory.adminMemberEntity());
    }

    @After
    public void tearDown() throws Exception {
        memberRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "MEMBER" , username = "member")
    public void getUserV2() throws Exception {

        mvc.perform(get("/api/guest/v1/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("memberName"))
                .andExpect(jsonPath("$.roles[0]").value("MEMBER"))
                .andExpect(jsonPath("$.userId").value("member"))
                .andExpect(jsonPath("$.email").value("memberEmail@gmail.com"));
    }

    @Test
    public void getMemberInfo() {
    }

    @Test
    public void update() {
    }

    @Test
    public void validationNickname() {
    }
}