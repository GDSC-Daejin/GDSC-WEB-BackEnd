package Gdsc.web.controller.api;

import Gdsc.web.common.MemberEntityFactory;
import Gdsc.web.dto.requestDto.MemberRoleUpdateDto;
import Gdsc.web.entity.Member;
import Gdsc.web.model.RoleType;
import Gdsc.web.repository.member.JpaMemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AdminApiControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private JpaMemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("/api/admin/v1/update/role 권한 수정")
    void updateRole() throws Exception {
        // given
        Member member = MemberEntityFactory.memberEntity();
        memberRepository.save(member);
        String url = "http://localhost:" + 8080 + "/api/admin/v1/update/role";
        MemberRoleUpdateDto memberRoleUpdateDto = new MemberRoleUpdateDto();
        memberRoleUpdateDto.setUserId(member.getUserId());
        memberRoleUpdateDto.setRole(RoleType.CORE);
        RoleType expected = RoleType.CORE;
        // when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(memberRoleUpdateDto)))
                .andDo(print())
                .andExpect(status().isOk());

        // then
        List<Member> memberList = memberRepository.findAll();
        assertEquals(1, memberList.size());
        assertEquals(expected, memberList.get(0).getRole());
    }
}
