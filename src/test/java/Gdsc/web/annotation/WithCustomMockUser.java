package Gdsc.web.annotation;

import Gdsc.web.config.WithMockUserSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithCustomMockUser {
    String username() default "user";
    String password() default "password";
    String role() default "ADMIN";
}
