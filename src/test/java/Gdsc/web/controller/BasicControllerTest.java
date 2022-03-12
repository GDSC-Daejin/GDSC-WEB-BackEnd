package Gdsc.web.controller;

import Gdsc.web.config.SecurityConfig;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BasicController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        })
class BasicControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mvc;

    @Before
    void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @WithMockUser(roles = "MEMBER")
    @Test
    void test1() throws Exception {
        String hello = "hi hi!!";

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }
}