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
import org.tomholmes.opensource.hotel.bff.model.GuestEntity;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;
import org.tomholmes.opensource.hotel.bff.repository.BedTypeRepository;
import org.tomholmes.opensource.hotel.bff.repository.GuestRepository;
import org.tomholmes.opensource.hotel.bff.repository.ReservationStatusRepository;
import org.tomholmes.opensource.hotel.bff.repository.RoomRepository;
import org.tomholmes.opensource.hotel.bff.repository.RoomStatusRepository;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
@Transactional
public class ReservationControllerTest
{
    private static final String BASE_URL = "/api/v1/reservation";

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BedTypeRepository bedTypeRepository;

    @Autowired
    private RoomStatusRepository roomStatusRepository;

    @Autowired
    private ReservationStatusRepository  reservationStatusRepository;

    protected MockMvc mockMvc;

    private Long guestId;

    private Long roomId;

    private Long statusId;

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

        LocalDateTime now = LocalDateTime.now();

        GuestEntity guest = new GuestEntity();
        guest.setFirstName("Reservation");
        guest.setLastName("Guest");
        guest.setGuestEmail("reservation.guest@example.com");
        guest.setGuestPhone("555-0110");
        this.guestId = guestRepository.saveAndFlush(guest).getId();

        this.statusId = reservationStatusRepository.findById(1L).orElseThrow().getId();

        RoomEntity room = new RoomEntity();
        room.setId(1L);
        room.setRoomNumber(9001);
        room.setRoomName("Reservation Test Room");
        room.setRoomDescription("Room for reservation controller test");
        room.setOccupants(2);
        room.setFloor(9);
        room.setCreatedBy(1L);
        room.setCreatedDate(now);
        room.setUpdatedBy(1L);
        room.setUdpatedDate(now);
        room.setBedType(bedTypeRepository.findById(1L).orElseThrow());
        room.setRoomStatus(roomStatusRepository.findByRoomStatusCode("AVAILABLE").orElseThrow());
        this.roomId = roomRepository.saveAndFlush(room).getId();
    }

    @Test
    // public ResponseEntity<List<ReservationRecord>> getAllReservations()
    public void testGetAllReservations() throws Exception
    {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL);
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    // public ResponseEntity<ReservationRecord> getReservationById(@PathVariable Long reservationId)
    public void testGetReservationById() throws Exception
    {
        Long reservationId = createReservationAndReturnId();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "/" + reservationId);
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    // public ResponseEntity<ReservationRecord> getReservationByReservationNumber(@PathVariable String reservationNumber)
    public void testGetReservationByReservationNumber() throws Exception
    {

        String reservationNumber = "JHFDU346";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "/reservationNumber/" + reservationNumber);
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    // public ResponseEntity<List<ReservationRecord>> getReservationsByPrimaryGuestIdAndCheckinDate(
    //         @PathVariable Long primaryGuestId, @PathVariable LocalDate checkinDate)
    public void testGetReservationsByPrimaryGuestIdAndCheckinDate() throws Exception
    {
        createReservationAndReturnId();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                BASE_URL + "/primaryGuest/" + guestId + "/checkinDate/2027-02-01");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].primaryGuestId").value(guestId));
    }

    @Test
    public void testGetReservationsByPrimaryGuestLastNameAndCheckinDate() throws Exception
    {
        createReservationAndReturnId();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                BASE_URL + "/primaryGuest/lastName/Guest/checkinDate/2027-02-01");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    // public ResponseEntity<ReservationRecord> createNewReservation(@RequestBody ReservationRecord reservationRecord)
    public void testCreateReservation() throws Exception
    {
        String json = """
                {
                    "primaryGuestId": %d,
                    "roomId": %d,
                    "statusId": %d,
                    "checkinDate": "2027-01-01T15:00:00",
                    "checkoutDate": "2027-01-03T11:00:00",
                    "numberAdults": 2,
                    "numberChildren": 0
                }
                """.formatted(guestId, roomId, statusId);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    // public ResponseEntity<ReservationRecord> createNewReservation(@RequestBody ReservationRecord reservationRecord)
    public void testCreateReservation_WithoutAccess() throws Exception
    {
        String json = """
                {
                    "primaryGuestId": %d,
                    "roomId": %d,
                    "statusId": %d,
                    "checkinDate": "2027-01-01T15:00:00",
                    "checkoutDate": "2027-01-03T11:00:00",
                    "numberAdults": 2,
                    "numberChildren": 0
                }
                """.formatted(guestId, roomId, statusId);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL)
        .with(oidcLogin().idToken(t -> t
        .subject("3c42807f-f1ac-4baa-9e02-444523b2cfeb")
        .claim("preferred_username", "testuser"))
        .authorities(new SimpleGrantedAuthority("ROLE_USER")))
        .contentType(MediaType.APPLICATION_JSON)
        .content(json);
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isForbidden());
    }

    @Test
    // public ResponseEntity<ReservationRecord> reservationCheckIn(@RequestBody Long reservationId)
    public void testReservationCheckIn() throws Exception
    {
        Long reservationId = createReservationAndReturnId();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(BASE_URL + "/checkin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationId.toString());
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    @Test
    // public ResponseEntity<ReservationRecord> reservationCheckOut(@RequestBody Long reservationId)
    public void testReservationCheckOut() throws Exception
    {
        Long reservationId = createReservationAndReturnId();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(BASE_URL + "/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationId.toString());
        this.mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());
    }

    private Long createReservationAndReturnId() throws Exception
    {
        String json = """
                {
                    "primaryGuestId": %d,
                    "roomId": %d,
                    "statusId": %d,
                    "checkinDate": "2027-02-01T15:00:00",
                    "checkoutDate": "2027-02-03T11:00:00",
                    "numberAdults": 2,
                    "numberChildren": 0
                }
                """.formatted(guestId, roomId, statusId);
        String response = this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String idField = "\"id\":";
        int start = response.indexOf(idField) + idField.length();
        int end = response.indexOf(',', start);
        return Long.valueOf(response.substring(start, end).trim());
    }
}
