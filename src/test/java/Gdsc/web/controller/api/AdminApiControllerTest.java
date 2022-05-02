package Gdsc.web.controller.api;

import Gdsc.web.common.MemberEntityFactory;
import Gdsc.web.dto.WarningDto;
import Gdsc.web.dto.requestDto.MemberRoleUpdateDto;
import Gdsc.web.entity.Member;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.WarnDescription;
import Gdsc.web.model.RoleType;
import Gdsc.web.repository.member.JpaMemberRepository;
import Gdsc.web.repository.warnDescription.JpaWarnDescription;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private Member memberGuest;
    private Member memberAdmin;
    private Member memberMember;
    @Autowired
    private JpaMemberRepository memberRepository;
    @Autowired
    private JpaWarnDescription jpaWarnDescription;

    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
        memberMember = MemberEntityFactory.memberEntity();
        memberAdmin = MemberEntityFactory.adminMemberEntity();
        memberGuest = MemberEntityFactory.guestMemberEntity();

        MemberInfo memberInfoMember = memberMember.getMemberInfo();
        memberInfoMember.setPhoneNumber("010-1234-5678");
        memberMember.setMemberInfo(memberInfoMember);
        MemberInfo memberInfoAdmin = memberAdmin.getMemberInfo();
        memberInfoAdmin.setPhoneNumber("010-1111-1111");
        memberAdmin.setMemberInfo(memberInfoMember);
        MemberInfo memberInfoGuest = memberGuest.getMemberInfo();
        memberInfoMember.setPhoneNumber("010-2222-2222");
        memberGuest.setMemberInfo(memberInfoMember);

        memberRepository.saveAndFlush(memberMember);
        memberRepository.saveAndFlush(memberAdmin);
        memberRepository.saveAndFlush(memberGuest);
    }

    @Test
    @DisplayName("/api/admin/v1/update/role 권한 수정")
    void updateRole() throws Exception {
        // given
        MemberRoleUpdateDto memberRoleUpdateDto = new MemberRoleUpdateDto();
        memberRoleUpdateDto.setUserId(memberMember.getUserId());
        memberRoleUpdateDto.setRole(RoleType.CORE);
        RoleType expected = RoleType.CORE;
        // when
        String url = "http://localhost:" + 8080 + "/api/admin/v1/update/role";
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(memberRoleUpdateDto)))
                .andDo(print())
                .andExpect(status().isOk());

        // then
        Member testMember = memberRepository.findByUserId(memberMember.getUserId());
        assertEquals(expected, testMember.getRole());
    }

    @Test
    @DisplayName("/api/admin/v1/all/list 모든 회원 조회")
    void retrieveUserList() throws Exception{
        // given

        // when
        String url = "http://localhost:" + 8080 + "/api/admin/v1/all/list";
        mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk());
        // then
        List<Member> list = memberRepository.findAll();
        assertEquals(3, list.size());
    }

    @Test
    @DisplayName("/api/admin/v1/member/list 게스트 제외 멤버만 조회")
    void retrieveMemberList() throws Exception{

        // when
        String url = "http://localhost:" + 8080 + "/api/admin/v1/member/list";
        mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk());
        // then
        List<RoleType> roleTypes = new ArrayList<>();
        roleTypes.add(RoleType.CORE);
        roleTypes.add(RoleType.LEAD);
        roleTypes.add(RoleType.MEMBER);
        List<Member> list = memberRepository.findMembersByRoleInAndMemberInfo_PhoneNumberIsNotNull(roleTypes);
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("v1/guest/list 게스트만 조회")
    void retrieveGuestList() throws Exception{

        // when
        String url = "http://localhost:" + 8080 + "/api/admin/v1/guest/list";
        mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk());
        // then
        List<RoleType> roleTypes = new ArrayList<>();
        roleTypes.add(RoleType.GUEST);
        List<Member> list = memberRepository.findMembersByRoleInAndMemberInfo_PhoneNumberIsNotNull(roleTypes);
        assertEquals(1, list.size());
        assertEquals(RoleType.GUEST, list.get(0).getRole());
    }

    @Test
    @DisplayName("/api/admin/v1/warning 경고 주기")
    void giveWarning() throws Exception{
        // given
        String expected = "경고";

        WarningDto warningDto = new WarningDto();
        warningDto.setTitle(expected);
        warningDto.setContent(expected);
        warningDto.setToUser(memberMember.getUserId());

        // when

        String url = "http://localhost:" + 8080 + "/api/admin/v1/warning";
        mvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(warningDto)))
                .andDo(print())
                .andExpect(status().isOk());
        // then

        List<WarnDescription> list = jpaWarnDescription.findAll();
        assertEquals(1, list.size());
        assertEquals(expected, list.get(0).getContent());
        assertEquals(expected, list.get(0).getTitle());
        assertEquals(memberAdmin, list.get(0).getFromUser());
    }
}