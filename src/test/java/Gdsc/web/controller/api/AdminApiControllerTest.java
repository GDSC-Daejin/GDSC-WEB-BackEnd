package Gdsc.web.controller.api;

import Gdsc.web.common.MemberEntityFactory;
import Gdsc.web.controller.AbstractControllerTest;
import Gdsc.web.admin.dto.MemberRoleUpdateDto;
import Gdsc.web.member.entity.Member;
import Gdsc.web.member.entity.MemberInfo;
import Gdsc.web.member.model.RoleType;
import Gdsc.web.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AdminApiControllerTest extends AbstractControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    private ObjectMapper mapper = new ObjectMapper();
    private Member memberGuest;
    private Member memberAdmin;
    private Member memberMember;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(new CharacterEncodingFilter("UTF-8", true))  // ?????? ??????
                .alwaysDo(print())
                .build();
        List<Member> memberList = new ArrayList<>();
        memberMember = MemberEntityFactory.memberEntity();
        memberAdmin = MemberEntityFactory.adminMemberEntity();
        memberGuest = MemberEntityFactory.guestMemberEntity();
        memberList.add(memberMember);
        memberList.add(memberAdmin);
        memberList.add(memberGuest);

        memberMember.getMemberInfo().setPhoneNumber("010-1234-5678");
        memberAdmin.getMemberInfo().setPhoneNumber("010-1111-1111");
        //memberGuest.getMemberInfo().setPhoneNumber("010-2222-2222");


        memberRepository.saveAll(memberList);
    }

   /* @Test
    @DisplayName("/api/admin/v1/update/role ?????? ??????")
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
    @DisplayName("v1/all/list ?????? ?????? ??????")
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
    @DisplayName("v1/member/list ????????? ?????? ????????? ??????")
    @WithMockUser(roles = "LEAD")
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
    }*/

    @Test
    @DisplayName("v1/guest/list ???????????? ??????")
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
}