package org.tomholmes.opensource.hotel.bff.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.tomholmes.opensource.hotel.bff.dto.ReservationRecord;
import org.tomholmes.opensource.hotel.bff.model.ReservationEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ReservationServiceTest extends BaseServiceTests
{
    private static final Long GUEST_ID = 1L;
    private static final Long OTHER_GUEST_ID = 2L;
    private static final LocalDate CHECKIN = LocalDate.of(2026, 11, 3);
    private static final LocalDate CHECKOUT = LocalDate.of(2026, 11, 6);
    private static final String KEYCLOAK_ID = "3c42807f-f1ac-4baa-9e02-444523b2cfeb";

    @Autowired
    private ReservationService reservationService;

    @BeforeEach
    public void setUpSecurityContext()
    {
        OidcIdToken idToken = new OidcIdToken("token", null, null, Map.of("sub", KEYCLOAK_ID, "preferred_username", "testuser"));
        OidcUser oidcUser = new DefaultOidcUser(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")), idToken);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(oidcUser, null, oidcUser.getAuthorities()));
        SecurityContextHolder.setContext(context);
    }

    @AfterEach
    public void clearSecurityContext()
    {
        SecurityContextHolder.clearContext();
    }

    @Test
    //List<ReservationEntity> getAllReservationEntities();
    public void testGetAllReservationEntities()
    {
        List<ReservationEntity> allReservationEntities = reservationService.getAllReservationEntities();
        assertNotNull(allReservationEntities);
    }

    @Test
    // List<ReservationRecord> getAllReservationRecords();
    public void testGetAllReservationRecords()
    {
        List<ReservationRecord> allReservationRecords = reservationService.getAllReservationRecords();
        assertNotNull(allReservationRecords);
        assertEquals(reservationService.getAllReservationEntities().size(), allReservationRecords.size());
    }

    @Test
    // List<ReservationEntity> getReservationEntitiesByPrimaryGuestIdAndCheckinDate(Long guestId, LocalDate checkinDate);
    public void testGetReservationEntitiesByPrimaryGuestIdAndCheckinDate()
    {
        List<ReservationEntity> reservationEntities =
                reservationService.getReservationEntitiesByPrimaryGuestIdAndCheckinDate(GUEST_ID, CHECKIN);
        assertNotNull(reservationEntities);
        assertEquals(1, reservationEntities.size());
    }

    @Test
    // List<ReservationRecord> getReservationRecordsByPrimaryGuestIdAndCheckinDate(Long guestId, LocalDate checkinDate);
    public void testGetReservationRecordsByPrimaryGuestIdAndCheckinDate()
    {
        List<ReservationRecord> reservationRecords =
                reservationService.getReservationRecordsByPrimaryGuestIdAndCheckinDate(GUEST_ID, CHECKIN);
        assertNotNull(reservationRecords);
        assertEquals(1, reservationRecords.size());
        assertEquals(GUEST_ID, reservationRecords.get(0).primaryGuestId());
    }

    @Test
    public void testGetReservationsByPrimaryGuestIdAndCheckinDate_returnsEmptyWhenNothingMatches()
    {
        assertFalse(reservationService.getReservationEntitiesByPrimaryGuestIdAndCheckinDate(OTHER_GUEST_ID, CHECKIN).iterator().hasNext());
        assertFalse(reservationService.getReservationRecordsByPrimaryGuestIdAndCheckinDate(GUEST_ID, CHECKOUT).iterator().hasNext());
    }

    @Test
    public void testGetReservationEntitiesByPrimaryGuestLastNameAndCheckinDate()
    {
        List<ReservationEntity> reservationEntities =
                reservationService.getReservationEntitiesByPrimaryGuestLastNameAndCheckinDate("Holmes", CHECKIN);
        assertNotNull(reservationEntities);
    }

    @Test
    public void testGetReservationRecordsByPrimaryGuestLastNameAndCheckinDate()
    {
        List<ReservationRecord> reservationRecords =
                reservationService.getReservationRecordsByPrimaryGuestLastNameAndCheckinDate("Holmes", CHECKIN);
        assertNotNull(reservationRecords);
    }

    @Test
    public void testReservationCheckIn()
    {
        // get an existing entity from our database
        Long reservationId = 1L;
        ReservationRecord reservationRecord = reservationService.getReservationRecordById(reservationId);
        assertNotNull(reservationRecord);
        assertEquals(GUEST_ID, reservationRecord.primaryGuestId());
        // this is is an existing reservation in the database, we know these fields should be NOT NULL
        assertNotNull(reservationRecord.checkinDate());
        assertNotNull(reservationRecord.createdDate());
        assertNotNull(reservationRecord.reservationNumber());
        // these fields should ALWAYS be NULL because we have NOT checked-in yet
        assertNull(reservationRecord.actualCheckin());
        assertNull(reservationRecord.actualCheckout());
        assertNull(reservationRecord.actualCheckinBy());
        assertNull(reservationRecord.actualCheckoutBy());

        LocalDateTime updatedDate = reservationRecord.updatedDate();

        reservationRecord =  reservationService.reservationCheckIn(reservationId);
        assertNotNull(reservationRecord);
        // after the checkin, the updated date should be updated, so it doesn't match what it was
        assertNotEquals(updatedDate,  reservationRecord.updatedDate());
        assertNotNull(reservationRecord.actualCheckin());
        assertNotNull(reservationRecord.actualCheckinBy());
    }

    @Test
    public void testReservationCheckOut()
    {
        // get an existing entity from our database
        Long reservationId = 1L;
        ReservationRecord reservationRecord = reservationService.getReservationRecordById(reservationId);
        assertNotNull(reservationRecord);
        assertEquals(GUEST_ID, reservationRecord.primaryGuestId());
        // this is is an existing reservation in the database, we know these fields should be NOT NULL
        assertNotNull(reservationRecord.checkinDate());
        assertNotNull(reservationRecord.createdDate());
        assertNotNull(reservationRecord.reservationNumber());
        // these fields should ALWAYS be NULL because we have NOT checked-in yet
        assertNull(reservationRecord.actualCheckin());
        assertNull(reservationRecord.actualCheckout());
        assertNull(reservationRecord.actualCheckinBy());
        assertNull(reservationRecord.actualCheckoutBy());

        LocalDateTime updatedDate = reservationRecord.updatedDate();

        reservationRecord =  reservationService.reservationCheckIn(reservationId);
        assertNotNull(reservationRecord);
        // after the checkin, the updated date should be updated, so it doesn't match what it was
        assertNotEquals(updatedDate,  reservationRecord.updatedDate());
        assertNotNull(reservationRecord.actualCheckin());
        assertNotNull(reservationRecord.actualCheckinBy());

        updatedDate = reservationRecord.updatedDate();

        reservationRecord =  reservationService.reservationCheckOut(reservationId);
        assertNotNull(reservationRecord);
        // after the checkin, the updated date should be updated, so it doesn't match what it was
        assertNotEquals(updatedDate,  reservationRecord.updatedDate());
        assertNotNull(reservationRecord.actualCheckout());
        assertNotNull(reservationRecord.actualCheckoutBy());
    }

}
