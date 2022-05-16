package Gdsc.web.controller.api;

import Gdsc.web.common.CategoryEntityFactory;
import Gdsc.web.common.MemberEntityFactory;
import Gdsc.web.common.PostEntityFactory;
import Gdsc.web.entity.*;
import Gdsc.web.repository.category.JpaCategoryRepository;
import Gdsc.web.repository.member.JpaMemberRepository;
import Gdsc.web.repository.post.JpaPostRepository;
import Gdsc.web.repository.scrap.JpaScrapRepository;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ScrapApiControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private JpaMemberRepository memberRepository;
    @Autowired
    private JpaPostRepository postRepository;
    @Autowired
    private JpaScrapRepository scrapRepository;
    @Autowired
    private JpaCategoryRepository categoryRepository;

    private Member memberMember;

    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
        memberMember = MemberEntityFactory.memberEntity();

        MemberInfo memberInfoMember = memberMember.getMemberInfo();
        memberInfoMember.setPhoneNumber("010-1234-5678");
        memberMember.setMemberInfo(memberInfoMember);
        memberRepository.saveAndFlush(memberMember);
    }

    @Test
    @DisplayName("/api/member/v1/scrap/{postId} 스크랩")
    void scrap() throws Exception{
        // given
        Category category = CategoryEntityFactory.categoryBackendEntity();
        categoryRepository.save(category);
        Post post = PostEntityFactory.falseBlockFalseTmpStorePostEntity(memberMember , category);
        postRepository.save(post);
        Long postId = post.getPostId();
        MemberScrapPost memberScrapPost = scrapRepository.findByMemberInfo_Member_UserIdAndPost_PostId(memberMember.getUserId(), postId);
        if(memberScrapPost == null){
            MemberScrapPost newMemberScrapPost = new MemberScrapPost();
            newMemberScrapPost.setMemberInfo(memberMember.getMemberInfo());
            newMemberScrapPost.setPost(post);
            scrapRepository.save(newMemberScrapPost);
        } else{
            scrapRepository.delete(memberScrapPost);
        }
        // when
        String url = "http://localhost:" + 8080 + "/api/member/v1/scrap/"+postId;
        mvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(post)))
                .andExpect(status().isOk());

        // then
        assertThat(memberScrapPost).isNotNull();
    }

}