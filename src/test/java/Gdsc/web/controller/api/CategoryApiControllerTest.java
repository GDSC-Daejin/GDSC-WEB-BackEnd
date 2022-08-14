package Gdsc.web.controller.api;

import Gdsc.web.annotation.WithCustomMockUser;
import Gdsc.web.common.CategoryEntityFactory;
import Gdsc.web.controller.AbstractControllerTest;
import Gdsc.web.category.dto.CategoryUpdateDto;
import Gdsc.web.category.entity.Category;
import Gdsc.web.category.repository.JpaCategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CategoryApiControllerTest extends AbstractControllerTest {
    @Autowired
    private WebApplicationContext context;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private JpaCategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mvc = MockMvcBuilders.webAppContextSetup(context).addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }
    @Test
    @DisplayName("/api/v1/category")
    void categoryList() throws Exception {
        //given
        Category category = CategoryEntityFactory.categoryBackendEntity();
        Category category2 = CategoryEntityFactory.categoryFrontEndEntity();
        categoryRepository.save(category);
        categoryRepository.save(category2);
        //when
        String url = "http://localhost:" + 8080 + "/api/v1/category";
        mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk());
        //then
        List<Category> categoryList = categoryRepository.findAll();
        assertEquals(2, categoryList.size());
        assertEquals("Backend", categoryList.get(0).getCategoryName());
        assertEquals("Frontend", categoryList.get(1).getCategoryName());


    }



    @Test
    @DisplayName("/api/v1/category 카테고리 삭제")
    @WithCustomMockUser(role = "ADMIN")
    void deleteCategory() throws Exception {
        Category category = CategoryEntityFactory.categoryBackendEntity();
        categoryRepository.save(category);
        String url = "http://localhost:" + 8080 + "/api/admin/v1/category/delete";
        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(category)))
                .andDo(print())
                .andExpect(status().isOk());
        assertEquals(0, categoryRepository.findAll().size());
    }

    @Test
    @DisplayName("/api/v1/category 카테고리 수정")
    void updateCategory() throws Exception {
        //given
        Category category = CategoryEntityFactory.categoryBackendEntity();
        categoryRepository.save(category);
        String expected = "updatedCategory";
        CategoryUpdateDto categoryUpdateDto = new CategoryUpdateDto();
        categoryUpdateDto.setUpdateCategoryName(expected);
        categoryUpdateDto.setCategoryName(category.getCategoryName());
        String url = "http://localhost:" + 8080 + "/api/admin/v1/category/update";
        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(categoryUpdateDto)))
                .andDo(print())
                .andExpect(status().isOk());

        //then
        List<Category> categoryList = categoryRepository.findAll();
        assertEquals(1, categoryList.size());
        assertEquals(expected, categoryList.get(0).getCategoryName());
    }
}