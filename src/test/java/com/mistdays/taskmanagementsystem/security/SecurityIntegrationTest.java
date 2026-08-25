package com.mistdays.taskmanagementsystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mistdays.taskmanagementsystem.entity.Role;
import com.mistdays.taskmanagementsystem.entity.User;
import com.mistdays.taskmanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void whenAccessProtectedEndpointWithoutToken_thenReturns401() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenLoginWithValidCredentials_thenReturnsJwtAndCanAccessProtectedEndpoint() throws Exception {
        // 1. Seed user in the test database with all required fields (including email)
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com"); // Set required non-null email field
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole(Role.USER);
        userRepository.save(user);

        // 2. Prepare login request
        Map<String, String> loginRequest = Map.of(
                "username", "testuser",
                "password", "password123"
        );

        // 3. Perform POST /api/auth/login
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        // 4. Extract JWT from response
        String responseBody = result.getResponse().getContentAsString();
        Map<?, ?> responseMap = objectMapper.readValue(responseBody, Map.class);
        String token = (String) responseMap.get("token");

        // 5. Access protected endpoint using the retrieved JWT
        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}