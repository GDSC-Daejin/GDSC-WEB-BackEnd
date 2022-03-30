package Gdsc.web.controller.api;

import Gdsc.web.config.SecurityConfig;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.Store;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@RunWith(SpringRunner.class)
//@DataJpaTest(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class}))
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK )
@AutoConfigureMockMvc
class PostApiControllerTest {
    @Autowired
    private WebApplicationContext context;



    @Autowired
    private PostService postService;

    private
    MockMvc mvc;

    @MockBean
    private UserPrincipal user;
    @Autowired
    private MemberService memberService;
    @Autowired
    private JpaMemberRepository memberRepository;
    @Autowired
    private JpaCategoryRepository categoryRepository;
    private Member member;
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();;


    }

    @After
    public void tearDown() throws Exception {
        memberService.deleteMemberForTest(user.getUserId());
    }


    @Test
    @WithMockUser(roles="MEMBER")
    @DisplayName("포스트 데이터 저장 테스트")
    void saveJsonPost() throws Exception {
        //given
        MemberInfo memberInfo = new MemberInfo();
        LocalDateTime now = LocalDateTime.now();
        member = new Member(
                "UserId",
                "Developer",
                "GDSC-TEST@gmail.com",
                "Y",
                "https://ca.slack-edge.com/T02BE2ERU5A-U02C8B72LT1-e35fe9b38122-512",
                ProviderType.GOOGLE,
                RoleType.MEMBER,
                now,
                now,
                memberInfo
        );
        memberInfo.setMember(member);
        memberService.회원가입(member);

        user = new UserPrincipal(
                member.getUserId(),
                member.getPassword(),
                member.getProviderType(),
                member.getRole(),
                Collections.singletonList(new SimpleGrantedAuthority(member.getRole().getCode()))
        );


        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setContent("content");
        Category category = new Category();
        category.setCategoryName("category");
        categoryRepository.save(category);

        postRequestDto.setCategory(category);
        postRequestDto.setTitle("title");
        postService.save(postRequestDto , user.getUserId());

        String url = "http://localhost:" + 8080 + "/api/v1/post/list";
        //when
        mvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(postRequestDto)))
                .andExpect(status().isOk());
    }


}