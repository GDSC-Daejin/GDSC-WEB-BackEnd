package Gdsc.web.controller.api;

import Gdsc.web.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AdminApiController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
@ComponentScan(basePackages = "Gdsc.web")
class AdminApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void updateRole() {
    }

    @Test
    void retrieveMemberList() {
    }

    @Test
    void retrieveGuestList() {
    }

    @Test
    void giveWarning() {
    }
}