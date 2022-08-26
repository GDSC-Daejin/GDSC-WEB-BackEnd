package Gdsc.web.scrap.controller;

import Gdsc.web.annotation.WithCustomMockUser;
import Gdsc.web.category.entity.Category;
import Gdsc.web.category.repository.JpaCategoryRepository;
import Gdsc.web.common.CategoryEntityFactory;
import Gdsc.web.common.PostEntityFactory;
import Gdsc.web.controller.AbstractControllerTest;
import Gdsc.web.post.entity.Post;
import Gdsc.web.post.repository.PostRepository;
import Gdsc.web.scrap.entity.MemberScrapPost;
import Gdsc.web.scrap.repository.JpaScrapRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
public class ScrapApiControllerTest  extends AbstractControllerTest {

    @Autowired
    private WebApplicationContext context;

    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private  PostRepository postRepository;
    @Autowired
    private  JpaCategoryRepository categoryRepository;
    @Autowired
    private  JpaScrapRepository scrapRepository;



    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }
    @Test
    @DisplayName("스크랩 하기")
    @WithCustomMockUser(username = "test", role = "USER")
    public void scrap() throws Exception {
        //given
        Category category = CategoryEntityFactory.categoryBackendEntity();
        categoryRepository.save(category);
        Post post = PostEntityFactory.falseBlockFalseTmpStorePostEntity("test",category);
        postRepository.save(post);
        //when
        String url = "http://localhost:" + 8080 + "/api/guest/v1/scrap/" + post.getPostId();
        mvc.perform(post(url))
                .andDo(print())
                .andExpect(status().isOk());
        //then
        MemberScrapPost memberScrapPost = scrapRepository.findByUserIdAndPost_PostId("test",post.getPostId());
        assertNotNull(memberScrapPost);
    }

    @Test
    public void myScrap() {
    }

    @Test
    public void myScrapByCategory() {
    }

    @Test
    public void myScrapList() {
    }
}