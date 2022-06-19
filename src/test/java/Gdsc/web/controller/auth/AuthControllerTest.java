package Gdsc.web.controller.auth;

import Gdsc.web.common.MemberEntityFactory;
import Gdsc.web.controller.AbstractControllerTest;
import Gdsc.web.dto.auth.AuthReqModel;
import Gdsc.web.entity.Member;
import Gdsc.web.repository.member.CustomMemberRepository;
import Gdsc.web.repository.member.MemberRepository;
import Gdsc.web.repository.member.MemberRepositoryImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends AbstractControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;
    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("개발 환경 로그인 테스트")
    void join() throws Exception {
        Member member = MemberEntityFactory.memberEntity();
        mvc.perform(post("/test/auth/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(member)))
                .andExpect(status().isOk())
                .andDo(print());
        Member checkMember = Optional.ofNullable(memberRepository.findByEmail(member.getEmail()))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        Assert.assertEquals(member.getUserId(), checkMember.getUserId());
        // bcryte password 라서 암호화된 값이 다르다.
        Assert.assertNotEquals(member.getPassword(), checkMember.getPassword());
        Assert.assertEquals(member.getEmail(), checkMember.getEmail());
        Assert.assertEquals(member.getProfileImageUrl(), checkMember.getProfileImageUrl());
        Assert.assertNull(member.getMemberInfo().getBlogUrl());

    }

    @Test
    @DisplayName("개발 환경 로그인 테스트")
    void login() throws Exception {
        // given
        Member saveModel = MemberEntityFactory.memberEntity();
        saveModel.setPassword(new BCryptPasswordEncoder().encode(saveModel.getPassword()));
        memberRepository.save(saveModel);


        AuthReqModel authReqModel = new AuthReqModel();
        authReqModel.setId(MemberEntityFactory.memberEntity().getUserId());
        authReqModel.setPassword(MemberEntityFactory.memberEntity().getPassword());
        // when
        mvc.perform(post("/test/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authReqModel)))
                .andExpect(status().isOk())
                .andDo(print());
        // then

    }
}