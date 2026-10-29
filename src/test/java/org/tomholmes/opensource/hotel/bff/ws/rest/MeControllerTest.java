package org.tomholmes.opensource.hotel.bff.ws.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MeControllerTest
{
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp()
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new MeController()).build();
    }

    @Test
    public void testGetCurrentUser() throws Exception
    {
        OidcIdToken idToken = new OidcIdToken(
                "token",
                Instant.now(),
                Instant.now().plusSeconds(60),
                Map.of(
                        "sub", "keycloak-id",
                        "preferred_username", "tester",
                        "email", "tester@example.com",
                        "given_name", "Test",
                        "family_name", "User"));

        OidcUser oidcUser = new DefaultOidcUser(
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")), idToken);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                oidcUser, null, oidcUser.getAuthorities());

        this.mockMvc.perform(get("/api/v1/me").principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("tester"))
                .andExpect(jsonPath("$.email").value("tester@example.com"))
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("User"))
                .andExpect(jsonPath("$.keycloakId").value("keycloak-id"))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_ADMIN"));
    }

    @Test
    public void testGetCurrentUserWithoutOidcPrincipal() throws Exception
    {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "tester", null, List.of());

        this.mockMvc.perform(get("/api/v1/me").principal(authentication))
                .andExpect(status().isUnauthorized());
    }
}
