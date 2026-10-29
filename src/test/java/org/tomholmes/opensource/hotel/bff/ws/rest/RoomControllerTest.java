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
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;
import org.tomholmes.opensource.hotel.bff.repository.BedTypeRepository;
import org.tomholmes.opensource.hotel.bff.repository.RoomRepository;
import org.tomholmes.opensource.hotel.bff.repository.RoomStatusRepository;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@Transactional
public class RoomControllerTest
{
    private static final String BASE_URL = "/api/v1/room";

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BedTypeRepository bedTypeRepository;

    @Autowired
    private RoomStatusRepository roomStatusRepository;

    protected MockMvc mockMvc;

    private Long roomId;

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

        RoomEntity room = new RoomEntity();
        room.setId(1L);
        room.setRoomNumber(9002);
        room.setRoomName("Controller Test Room");
        room.setRoomDescription("Room for room controller test");
        room.setOccupants(2);
        room.setFloor(1);
        room.setCreatedBy(1L);
        room.setCreatedDate(LocalDateTime.now());
        room.setUpdatedBy(1L);
        room.setUdpatedDate(LocalDateTime.now());
        room.setBedType(bedTypeRepository.findById(1L).orElseThrow());
        room.setRoomStatus(roomStatusRepository.findByRoomStatusCode("AVAILABLE").orElseThrow());
        this.roomId = roomRepository.saveAndFlush(room).getId();
    }

    @Test
    // public ResponseEntity<List<RoomRecord>> getAllRooms()
    public void testGetAllRooms() throws Exception
    {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL);
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    // public ResponseEntity<RoomRecord> getRoomById(@PathVariable Long roomId)
    public void testGetRoomById() throws Exception
    {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "/" + roomId);
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    // public ResponseEntity<RoomRecord> createNewRoom(@RequestBody RoomRecord roomRecord)
    public void testCreateRoom() throws Exception
    {
        String json = """
                {
                    "id": 2,
                    "roomNumber": 999,
                    "roomName": "Test Room",
                    "roomDescription": "Test room description",
                    "roomStatusId": 1,
                    "occupants": 2,
                    "floor": 1,
                    "bedTypeId": 1
                }
                """;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }
}
