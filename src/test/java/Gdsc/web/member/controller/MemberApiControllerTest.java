package Gdsc.web.member.controller;

import Gdsc.web.annotation.WithCustomMockUser;
import Gdsc.web.common.MemberEntityFactory;
import Gdsc.web.controller.AbstractControllerTest;
import Gdsc.web.member.dto.MemberInfoRequestDto;
import Gdsc.web.member.entity.Member;
import Gdsc.web.member.entity.MemberInfo;
import Gdsc.web.member.model.PositionType;
import Gdsc.web.member.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
public class MemberApiControllerTest extends AbstractControllerTest {
    @Autowired
    private  WebApplicationContext context;
    @Autowired
    private MemberRepository memberRepository;
    private ObjectMapper mapper = new ObjectMapper();

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
                .andExpect(jsonPath("$.body.data.username").value("memberName"))
                .andExpect(jsonPath("$.body.data.role").value("MEMBER"))
                .andExpect(jsonPath("$.body.data.userId").value("member"))
                .andExpect(jsonPath("$.body.data.email").value("memberEmail@gmail.com"));
    }


    /*
    @Param
    @WithCustomMockUser(roles = "MEMBER" , username = "member")
    {
    "generation" : 1,
    "introduce" : "안녕 업데이트",
    "nickname" : "Rocoli",
    "phoneNumber" : "010-1234-1234",
    "major" : "산경공",
    "gitEmail" : "test305@gmail.com",
    "studentID" : "20171910",
    "positionType" : "Backend",
    "hashTag" : "hihi",
    "gitHubUrl" : "hihi",
    "blogUrl" : "hihi",
    "etcUrl" : "hihi"
    }
    * */
    @Test
    @WithMockUser(roles = "MEMBER" , username = "member")
    public void getMemberInfo() throws Exception {

        mvc.perform(get("/api/guest/v1/info"))
                .andExpect(status().isOk())
                /*.andExpect(jsonPath("$.body.data.generation").value(null))
                .andExpect(jsonPath("$.body.data.introduce").value(null))
                .andExpect(jsonPath("$.body.data.nickname").value(null))
                .andExpect(jsonPath("$.body.data.phoneNumber").value(null))
                .andExpect(jsonPath("$.body.data.major").value(null))
                .andExpect(jsonPath("$.body.data.gitEmail").value(null))
                .andExpect(jsonPath("$.body.data.studentID").value(null))
                .andExpect(jsonPath("$.body.data.positionType").value(null))
                .andExpect(jsonPath("$.body.data.hashTag").value(null))
                .andExpect(jsonPath("$.body.data.gitHubUrl").value(null))
                .andExpect(jsonPath("$.body.data.blogUrl").value(null))
                .andExpect(jsonPath("$.body.data.etcUrl").value(null))*/
                .andDo(print());


    }
    // regularExpressionNickname
    @Test
    @WithMockUser(roles = "MEMBER" , username = "member")
    public void updateCheckRegularExpression() throws Exception {
        MemberInfoRequestDto memberInfoRequestDto = MemberInfoRequestDto.builder()
                .generation(1)
                .introduce("안녕 업데이트")
                .nickname("Rocoli2")
                .phoneNumber("010-1234-1234")
                .major("산경공")
                .gitEmail("tesgmail.com")
                .studentID("20171910")
                .positionType(PositionType.Backend)
                .hashTag("hihi")
                .gitHubUrl("hihi")
                .blogUrl("hihi")
                .etcUrl("hihi")
                .build();
        mvc.perform(put("/api/guest/v1/me")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(memberInfoRequestDto)))
                .andDo(print());
        MemberInfo memberInfo = memberRepository.findByUserId("member").getMemberInfo();
        MemberInfo originMemberInfo = MemberEntityFactory.memberEntity().getMemberInfo();
        assertThat(memberInfo.getNickname()).isEqualTo(originMemberInfo.getNickname());
        assertThat(memberInfo.getPhoneNumber()).isEqualTo("010-1234-1234");
        assertThat(memberInfo.getMajor()).isEqualTo("산경공");
        assertThat(memberInfo.getGitEmail()).isEqualTo(originMemberInfo.getGitEmail());
        assertThat(memberInfo.getStudentID()).isEqualTo("20171910");
        assertThat(memberInfo.getPositionType()).isEqualTo(PositionType.Backend);
        assertThat(memberInfo.getHashTag()).isEqualTo("hihi");
        assertThat(memberInfo.getGitHubUrl()).isEqualTo("hihi");
        assertThat(memberInfo.getBlogUrl()).isEqualTo("hihi");
        assertThat(memberInfo.getEtcUrl()).isEqualTo("hihi");


    }

    @Test
    public void validationNickname() {
    }
}