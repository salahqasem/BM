package com.brightminds.test.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.brightminds.test.dao.bean.Statement;
import com.brightminds.test.service.AccountService;

@WebMvcTest
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @WithMockUser(username = "admin", roles = "admin")
    @Test
    public void shouldReturnStatementsForAdmin() throws Exception {

        List<Statement> statements = new ArrayList<>();
        Statement statement = new Statement("123", "1234123456123", new Date(), new BigDecimal("100"));
        Statement statement2 = new Statement("456", "1234123456123", new Date(), new BigDecimal("300"));

        statements.add(statement);
        statements.add(statement2);

        when(accountService.getStatements(any(), any())).thenReturn(statements);
        when(accountService.getLast3MonthsStatements(any())).thenReturn(statements);

        mockMvc.perform(get("/admin/statement/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("123")));
    }

    @WithMockUser(username = "admin", roles = "admin")
    @Test
    public void shouldReturnBadRequestWhenParamsAreNotValid() throws Exception {

        mockMvc.perform(get("/admin/statement/1?to_date=1234"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString("is not a valid date")));
    }

    @WithMockUser(username = "user", roles = "user")
    @Test
    public void shouldReturn301ForNonAdminUsers() throws Exception {

        List<Statement> statements = new ArrayList<>();
        Statement statement = new Statement("123", "1234123456123", new Date(), new BigDecimal("100"));
        Statement statement2 = new Statement("456", "1234123456123", new Date(), new BigDecimal("300"));

        statements.add(statement);
        statements.add(statement2);

        when(accountService.getStatements(any(), any())).thenReturn(statements);
        when(accountService.getLast3MonthsStatements(any())).thenReturn(statements);

        mockMvc.perform(get("/admin/statement/1"))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    @WithMockUser(username = "user", roles = "user")
    @Test
    public void shouldReturnLast3MonthsStatments() throws Exception {

        List<Statement> statements = new ArrayList<>();
        Statement statement = new Statement("123", "1234123456123", new Date(), new BigDecimal("100"));
        Statement statement2 = new Statement("456", "1234123456123", new Date(), new BigDecimal("300"));

        statements.add(statement);
        statements.add(statement2);

        when(accountService.getStatements(any(), any())).thenReturn(statements);
        when(accountService.getLast3MonthsStatements(any())).thenReturn(statements);

        mockMvc.perform(get("/statement/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("123")));
    }

    @WithMockUser(username = "admin", roles = "admin")
    @Test
    public void adminCanPerformNonAdminAPIs() throws Exception {

        List<Statement> statements = new ArrayList<>();
        Statement statement = new Statement("123", "1234123456123", new Date(), new BigDecimal("100"));
        Statement statement2 = new Statement("456", "1234123456123", new Date(), new BigDecimal("300"));

        statements.add(statement);
        statements.add(statement2);

        when(accountService.getStatements(any(), any())).thenReturn(statements);
        when(accountService.getLast3MonthsStatements(any())).thenReturn(statements);

        mockMvc.perform(get("/statement/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("123")));
    }

    @Test
    public void shouldRedirectToLoginPage() throws Exception {

        List<Statement> statements = new ArrayList<>();
        Statement statement = new Statement("123", "1234123456123", new Date(), new BigDecimal("100"));
        Statement statement2 = new Statement("456", "1234123456123", new Date(), new BigDecimal("300"));

        statements.add(statement);
        statements.add(statement2);

        when(accountService.getStatements(any(), any())).thenReturn(statements);
        when(accountService.getLast3MonthsStatements(any())).thenReturn(statements);

        mockMvc.perform(get("/statement/1"))
            .andDo(print())
            .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(username = "admin", roles = "admin")
    @Test
    public void ShouldReturnInternalServerErrorForAdminApi() throws Exception {

        when(accountService.getStatements(any(), any())).thenThrow(SQLException.class);
        when(accountService.getLast3MonthsStatements(any())).thenThrow(SQLException.class);

        mockMvc.perform(get("/admin/statement/1"))
            .andDo(print())
            .andExpect(status().isInternalServerError())
            .andExpect(content().string(containsString("Service not available.")));
    }

    @WithMockUser(username = "user", roles = "user")
    @Test
    public void ShouldReturnInternalServerErrorForUserApi() throws Exception {

        when(accountService.getStatements(any(), any())).thenThrow(SQLException.class);
        when(accountService.getLast3MonthsStatements(any())).thenThrow(SQLException.class);

        mockMvc.perform(get("/statement/1"))
            .andDo(print())
            .andExpect(status().isInternalServerError())
            .andExpect(content().string(containsString("Service not available.")));
    }
}
