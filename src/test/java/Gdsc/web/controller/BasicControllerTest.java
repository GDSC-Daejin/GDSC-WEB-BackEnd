package Gdsc.web.controller;


import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class BasicControllerTest  extends AbstractControllerTest {
    @Autowired
    private WebApplicationContext context;


    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }
   @Test
    public void test1() throws Exception {
        String hello = "hi hi!!";

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }
}