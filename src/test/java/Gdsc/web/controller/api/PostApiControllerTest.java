package Gdsc.web.controller.api;

import Gdsc.web.dto.requestDto.PostRequestDto;
import Gdsc.web.dto.requestDto.PostResponseDto;
import Gdsc.web.entity.Category;
import Gdsc.web.entity.Member;
import Gdsc.web.entity.MemberInfo;
import Gdsc.web.entity.Post;
import Gdsc.web.model.RoleType;
import Gdsc.web.oauth.entity.ProviderType;
import Gdsc.web.oauth.entity.UserPrincipal;
import Gdsc.web.repository.category.JpaCategoryRepository;
import Gdsc.web.repository.member.JpaMemberRepository;
import Gdsc.web.repository.post.JpaPostRepository;
import Gdsc.web.service.MemberService;
import Gdsc.web.service.PostService;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)

class PostApiControllerTest {
    @Autowired
    private WebApplicationContext context;


    @MockBean
    PostService postService;

    private MockMvc mvc;

    @MockBean
    private UserPrincipal user;
    @MockBean
    private MemberService memberService;
    @MockBean
    private JpaMemberRepository memberRepository;
    private Member member;
    @Before
    public void setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        MemberInfo memberInfo = new MemberInfo();
        LocalDateTime now = LocalDateTime.now();
        member = new Member(
                "UserId",
                "Developer",
                "GDSC-TEST@gmail.com",
                "Y",
                "https://ca.slack-edge.com/T02BE2ERU5A-U02C8B72LT1-e35fe9b38122-512",
                ProviderType.GOOGLE,
                RoleType.GUEST,
                now,
                now,
                memberInfo
        );
        memberInfo.setMember(member);
        memberService.회원가입(member);

    }
    @Before
    public void setupPrincipal(){
        user = new UserPrincipal(
                 member.getUserId(),
                 member.getPassword(),
                 member.getProviderType(),
                 member.getRole(),
                Collections.singletonList(new SimpleGrantedAuthority(member.getRole().getCode()))
        );

    }
    @After
    public void tearDown() throws Exception {
        memberService.deleteMemberForTest(user.getUserId());
    }


    @Test
    void saveFormData() {
    }

    @Test
    @WithMockUser(roles="MEMBER")
    @DisplayName("포스트 데이터 저장 테스트")
    void saveJsonPost() throws IOException {
        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setContent("content");
        Category category = new Category();
        postRequestDto.setCategory(category);
        postRequestDto.setTitle("title");
        postService.save(postRequestDto , user.getUserId());
    }


}