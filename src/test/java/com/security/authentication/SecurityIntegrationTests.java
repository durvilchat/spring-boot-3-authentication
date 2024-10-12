package com.security.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenUserAccessPublic_thenSuccess() throws Exception {
        mockMvc.perform(get("/public/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World!")));
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenAdminAccessSecure_thenSuccess() throws Exception {
        mockMvc.perform(get("/admin/secure"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Admin only data")));
    }
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenUserTriesToAccessAdmin_thenForbidden() throws Exception {
        mockMvc.perform(get("/admin/secure"))
                .andExpect(status().isForbidden());
    }
}