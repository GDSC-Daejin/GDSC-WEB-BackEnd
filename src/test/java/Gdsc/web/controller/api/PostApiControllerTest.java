package Gdsc.web.controller.api;

import Gdsc.web.common.CategoryEntityFactory;
import Gdsc.web.common.MemberEntityFactory;
import Gdsc.web.common.PostEntityFactory;
import Gdsc.web.controller.AbstractControllerTest;
import Gdsc.web.post.dto.PostRequestDto;
import Gdsc.web.category.entity.Category;
import Gdsc.web.member.entity.Member;
import Gdsc.web.post.entity.Post;
import Gdsc.web.category.repository.JpaCategoryRepository;
import Gdsc.web.member.repository.MemberRepository;
import Gdsc.web.member.repository.JpaMemberInfoRepository;
import Gdsc.web.post.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class PostApiControllerTest extends AbstractControllerTest {
    @Autowired
    private WebApplicationContext context;



    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JpaCategoryRepository categoryRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private JpaMemberInfoRepository memberInfoRepository;
    private Member member;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        // Local data time ???????????? ??????
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(new CharacterEncodingFilter("UTF-8", true))  // ?????? ??????
                .alwaysDo(print())
                .build();
        member = MemberEntityFactory.adminMemberEntity();
        memberRepository.saveAndFlush(member);
        // ???????????? ????????? ????????? ?????? , save  ?????? ???????????? ????????????
        member = memberRepository.findByEmail(member.getEmail());

    }

    @After
    public void tearDown() throws Exception {
        postRepository.deleteAll();
        memberRepository.deleteAll();
        categoryRepository.deleteAll();
    }


    @Test
    @WithMockUser(roles="MEMBER")
    @DisplayName("????????? ????????? ?????? ?????????")
    void saveJsonPost() throws Exception {
        //given
        Category category = CategoryEntityFactory.categoryBackendEntity();

        Post post = PostEntityFactory.falseBlockFalseTmpStorePostEntity(member , category);
        categoryRepository.save(category);

        postRepository.save(post);
        //when



        String url = "http://localhost:" + 8080 + "/api/v1/post/list";
        //when
        mvc.perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(post)))
                .andExpect(status().isOk());

        //then
        List<Post> posts = postRepository.findAll();
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.get(0).getTitle()).isEqualTo(post.getTitle());
        assertThat(posts.get(0).getContent()).isEqualTo(post.getContent());
        assertThat(posts.get(0).getCategory().getCategoryName()).isEqualTo(post.getCategory().getCategoryName());
        assertThat(posts.get(0).getMemberInfo().getMember()).isEqualTo(post.getMemberInfo().getMember());


    }
    @Test
    @WithMockUser(roles="MEMBER" , username = "user")
    @DisplayName("/api/member/v2/post/{postId} ????????? ???????????? ?????????")
    void updateJsonPost() throws Exception {
        //given
        Category category = CategoryEntityFactory.categoryBackendEntity();
        Post post = PostEntityFactory.falseBlockFalseTmpStorePostEntity(member , category);
        post.setCategory(category);
        categoryRepository.save(category);
        postRepository.save(post);

        Long updateId = postRepository.findAll().get(0).getPostId();
        String expectedTitle = "update title test";
        String expectedContent = "update content test";
        Category expectedCategory = CategoryEntityFactory.categoryFrontEndEntity();


        PostRequestDto postRequestDto = PostRequestDto.builder()
                .postHashTags("update hash tag test")
                .title(expectedTitle)
                .content(expectedContent)
                .category(expectedCategory)
                .build();
        categoryRepository.save(expectedCategory);
        String url = "http://localhost:" + 8080 + "/api/member/v2/post/" + updateId;


        //when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(postRequestDto)))
                .andExpect(status().isOk());


        //then
        List<Post> posts = postRepository.findAll();
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(posts.get(0).getContent()).isEqualTo(expectedContent);
        assertThat(posts.get(0).getCategory()).isEqualTo(expectedCategory);

    }



}