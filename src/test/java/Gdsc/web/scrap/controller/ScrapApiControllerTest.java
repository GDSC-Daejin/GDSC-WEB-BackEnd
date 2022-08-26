package Gdsc.web.scrap.controller;

import Gdsc.web.controller.AbstractControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

public class ScrapApiControllerTest  extends AbstractControllerTest {
    @Autowired
    private WebApplicationContext context;

    private ObjectMapper mapper = new ObjectMapper();
}