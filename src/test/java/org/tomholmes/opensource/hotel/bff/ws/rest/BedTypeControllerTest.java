package org.tomholmes.opensource.hotel.bff.ws.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@Transactional
public class BedTypeControllerTest
{
    private static final String BASE_URL = "/api/v1/bedType";

    @Autowired
    protected WebApplicationContext context;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setUp()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(springSecurity())
        .defaultRequest(MockMvcRequestBuilders.get("/")
        .with(csrf())
        .with(oidcLogin().idToken(t -> t
        .subject("3c42807f-f1ac-4baa-9e02-444523b2cfeb")
        .claim("preferred_username", "testuser"))
        .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
        .build();
    }

    @Test
    // public ResponseEntity<List<BedTypeRecord>> getAllBedTypes()
    public void testGetAllBedTypes() throws Exception
    {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL);
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    // public ResponseEntity<BedTypeRecord> getBedTypeById(@PathVariable Long bedTypeId)
    public void testGetBedTypeById() throws Exception
    {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "/1");
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    // public ResponseEntity<BedTypeRecord> createNewBedType(@RequestBody BedTypeRecord bedTypeRecord)
    public void testCreateBedType() throws Exception
    {
        String json = """
                {
                    "bedType": "Queen Bunk"
                }
                """;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testGetAllBedTypes_ForbiddenForNonAdmin() throws Exception
    {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL)
                .with(oidcLogin().idToken(t -> t
                        .subject("3c42807f-f1ac-4baa-9e02-444523b2cfeb")
                        .claim("preferred_username", "testuser"))
                .authorities(new SimpleGrantedAuthority("ROLE_USER")));
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isForbidden());
    }
}
