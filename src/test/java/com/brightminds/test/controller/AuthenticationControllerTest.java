package com.brightminds.test.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountController accountController;

    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("<title>Login</title>")));
    }

    @WithMockUser("user")
    @Test
    public void testHomePageForAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/homePage"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("<title>Home Page</title>")));
    }

    @Test
    public void testHomePageForAnonymousUser() throws Exception {
        mockMvc.perform(get("/homePage"))
            .andDo(print())
            .andExpect(status().is3xxRedirection());
    }
}
