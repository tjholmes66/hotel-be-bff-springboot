package org.tomholmes.opensource.hotel.bff.ws.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.tomholmes.opensource.hotel.bff.model.GuestEntity;
import org.tomholmes.opensource.hotel.bff.repository.GuestRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@Transactional
public class GuestControllerTest
{
    private static final String BASE_URL = "/api/v1/guest";

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private GuestRepository guestRepository;

    protected MockMvc mockMvc;

    private Long guestId;

    @BeforeEach
    public void setUp()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        GuestEntity guest = new GuestEntity();
        guest.setFirstName("Get");
        guest.setLastName("Guest");
        guest.setGuestEmail("get.guest@example.com");
        guest.setGuestPhone("555-0120");
        this.guestId = guestRepository.saveAndFlush(guest).getId();
    }

    @Test
    // public ResponseEntity<List<GuestRecord>> getAllGuests()
    public void testGetAllGuests() throws Exception
    {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL);
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    // public ResponseEntity<GuestRecord> getGuestById(@PathVariable Long guestId)
    public void testGetGuestById() throws Exception
    {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "/" + guestId);
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testGetGuestsByLastName() throws Exception
    {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "/search/lastname")
                .param("lastName", "Guest");
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testGetGuestsByLastNameAndFirstName() throws Exception
    {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "/search/fullname")
                .param("lastName", "Guest")
                .param("firstName", "Get");
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    // public ResponseEntity<GuestRecord> createNewGuest(@RequestBody GuestRecord guestRecord)
    public void testCreateGuest() throws Exception
    {
        String json = """
                {
                    "firstName": "Test",
                    "lastName": "Guest",
                    "guestEmail": "test.guest@example.com",
                    "guestPhone": "555-0100",
                    "guestAdut": true
                }
                """;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }
}
