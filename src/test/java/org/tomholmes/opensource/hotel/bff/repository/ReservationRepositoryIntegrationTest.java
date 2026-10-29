package org.tomholmes.opensource.hotel.bff.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.tomholmes.opensource.hotel.bff.model.GuestEntity;
import org.tomholmes.opensource.hotel.bff.model.ReservationEntity;
import org.tomholmes.opensource.hotel.bff.model.ReservationStatusEntity;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;
import org.tomholmes.opensource.hotel.bff.utils.HotelUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * These tests run against the live database and rely on the seeded data:
 * 1 reservation (id 1), 2 guests and 9 rooms.
 * No test data is created for the guests or the rooms.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservationRepositoryIntegrationTest {

    private static final Long RESERVATION_ID = 1L;
    private static final Long GUEST_ID = 1L;
    private static final Long OTHER_GUEST_ID = 2L;
    private static final Long ROOM_ID = 1L;
    private static final Long OTHER_ROOM_ID = 2L;
    private static final Long STATUS_ID = 1L;
    private static final Long OTHER_STATUS_ID = 2L;
    private static final String RESERVATION_NUMBER = "JHFDU346";
    private static final LocalDate CHECKIN = LocalDate.of(2026, 11, 3);
    private static final LocalDate CHECKOUT = LocalDate.of(2026, 11, 6);
    private static final LocalDate NEW_CHECKIN = LocalDate.of(2026, 12, 1);
    private static final LocalDate NEW_CHECKOUT = LocalDate.of(2026, 12, 5);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationStatusRepository reservationStatusRepository;

    @Autowired
    private EntityManager entityManager;

    private GuestEntity guest()
    {
        return guestRepository.findById(GUEST_ID).orElseThrow();
    }

    private RoomEntity room()
    {
        return roomRepository.findById(ROOM_ID).orElseThrow();
    }

    private ReservationStatusEntity status()
    {
        return reservationStatusRepository.findById(STATUS_ID).orElseThrow();
    }

    private ReservationEntity reservation(LocalDate checkin, LocalDate checkout) {
        ReservationEntity reservation = new ReservationEntity();
        reservation.setPrimaryGuest(guest());
        reservation.setRoom(room());
        reservation.setCheckinDate(checkin);
        reservation.setCheckoutDate(checkout);
        reservation.setNumberAdults(2);
        reservation.setNumberChildren(0);
        reservation.setReservationNumber(HotelUtils.generateConfirmationCode());
        reservation.setReservationStatus(status());
        return reservation;
    }

    @Test
    public void testCreateReservation()
    {
        LocalDate CHECKIN_DATE =  LocalDate.of(2026, 10, 3);
        LocalDate CHECKOUT_DATE = LocalDate.of(2026, 10, 6);
        ReservationEntity reservation =  reservation(CHECKIN_DATE, CHECKOUT_DATE);
        reservation.setReservationNumber(HotelUtils.generateConfirmationCode());
        reservationRepository.saveAndFlush(reservation);
        assertNotNull(reservation);
    }

    @Test
    /*
     List<ReservationEntity> findOverlappingReservations(
    @Param("roomId") Long roomId,
    @Param("checkInDate") LocalDate checkInDate,
    @Param("checkOutDate") LocalDate checkOutDate
     */
    public void testFindOverlappingReservations()
    {
        LocalDate CHECKIN_DATE =  LocalDate.of(2026, 10, 3);
        LocalDate CHECKOUT_DATE = LocalDate.of(2026, 10, 6);
        ReservationEntity reservation =  reservation(CHECKIN_DATE, CHECKOUT_DATE);
        reservation.setReservationNumber(HotelUtils.generateConfirmationCode());
        reservationRepository.saveAndFlush(reservation);
        assertNotNull(reservation);

        Long roomId = reservation.getRoom().getId();
        LocalDate checkInDate =  LocalDate.of(2026, 10, 4);
        LocalDate checkOutDate = LocalDate.of(2026, 10, 7);
        List<ReservationEntity> list = reservationRepository.findOverlappingReservations(roomId, checkInDate, checkOutDate);
        assertEquals(1, list.size());
    }

    @Test
    void findAll_returnsSeededReservations() {
        List<ReservationEntity> reservations = reservationRepository.findAll();

        assertThat(reservations).hasSizeGreaterThanOrEqualTo(1);
        assertThat(reservations).extracting(ReservationEntity::getReservationNumber).contains(RESERVATION_NUMBER);
    }

    @Test
    void findById_returnsSeededReservation() {
        entityManager.clear();

        ReservationEntity reservation = reservationRepository.findById(RESERVATION_ID).orElseThrow();

        assertThat(reservation.getPrimaryGuest().getFirstName()).isEqualTo("Tom");
        assertThat(reservation.getPrimaryGuest().getLastName()).isEqualTo("Holmes");
        assertThat(reservation.getPrimaryGuest().getGuestEmail()).isEqualTo("tom@tomholmes.net");
        assertThat(reservation.getRoom().getRoomNumber()).isEqualTo(101);
        assertThat(reservation.getRoom().getFloor()).isEqualTo(1);
        assertThat(reservation.getCheckinDate()).isEqualTo(CHECKIN);
        assertThat(reservation.getCheckoutDate()).isEqualTo(CHECKOUT);
        assertThat(reservation.getNumberAdults()).isEqualTo(2);
        assertThat(reservation.getNumberChildren()).isZero();
        assertThat(reservation.getReservationNumber()).isEqualTo(RESERVATION_NUMBER);
        assertThat(reservation.getActualCheckin()).isNull();
        assertThat(reservation.getActualCheckout()).isNull();
    }

    @Test
    void findById_returnsEmptyForUnknownId() {
        assertThat(reservationRepository.findById(999L)).isEmpty();
    }

    @Test
    void countAndExistsById_reflectSeededReservations() {
        assertThat(reservationRepository.count()).isGreaterThanOrEqualTo(1);
        assertThat(reservationRepository.existsById(RESERVATION_ID)).isTrue();
        assertThat(reservationRepository.existsById(999L)).isFalse();
    }

    @Test
    void findByPrimaryGuestId_returnsReservationsForGuest() {
        List<ReservationEntity> reservations = reservationRepository.findByPrimaryGuestId(GUEST_ID);

        assertThat(reservations).hasSize(1);
        assertThat(reservations).extracting(ReservationEntity::getReservationNumber).containsExactly(RESERVATION_NUMBER);
    }

    @Test
    void findByPrimaryGuestId_returnsEmptyForGuestWithoutReservations() {
        assertThat(reservationRepository.findByPrimaryGuestId(OTHER_GUEST_ID)).isEmpty();
    }

    @Test
    void findByRoomId_returnsReservationsForRoom() {
        List<ReservationEntity> reservations = reservationRepository.findByRoomId(ROOM_ID);

        assertThat(reservations).hasSize(1);
        assertThat(reservations).extracting(ReservationEntity::getReservationNumber).containsExactly(RESERVATION_NUMBER);
    }

    @Test
    void findByRoomId_returnsEmptyForRoomWithoutReservations() {
        assertThat(reservationRepository.findByRoomId(OTHER_ROOM_ID)).isEmpty();
    }

    @Test
    void findByPrimaryGuest_IdAndRoom_IdAndCheckinDate_returnsMatchingReservation() {
        assertThat(reservationRepository.findByPrimaryGuest_IdAndRoom_IdAndCheckinDate(GUEST_ID, ROOM_ID, CHECKIN))
                .hasValueSatisfying(reservation -> assertThat(reservation.getId()).isEqualTo(RESERVATION_ID));
    }

    @Test
    void findByPrimaryGuest_IdAndRoom_IdAndCheckinDate_returnsEmptyWhenNothingMatches() {
        assertThat(reservationRepository.findByPrimaryGuest_IdAndRoom_IdAndCheckinDate(GUEST_ID, ROOM_ID, CHECKOUT)).isEmpty();
        assertThat(reservationRepository.findByPrimaryGuest_IdAndRoom_IdAndCheckinDate(GUEST_ID, OTHER_ROOM_ID, CHECKIN)).isEmpty();
        assertThat(reservationRepository.findByPrimaryGuest_IdAndRoom_IdAndCheckinDate(OTHER_GUEST_ID, ROOM_ID, CHECKIN)).isEmpty();
    }

    @Test
    void findReservationEntityByReservationNumber_returnsMatchingReservation() {
        ReservationEntity reservation = reservationRepository.findReservationEntityByReservationNumber(RESERVATION_NUMBER);

        assertThat(reservation.getId()).isEqualTo(RESERVATION_ID);
        assertThat(reservation.getPrimaryGuest().getGuestEmail()).isEqualTo("tom@tomholmes.net");
        assertThat(reservation.getRoom().getRoomNumber()).isEqualTo(101);
    }

    @Test
    void findReservationEntityByReservationNumber_returnsNullWhenNotFound() {
        assertThat(reservationRepository.findReservationEntityByReservationNumber("NOTEXISTS")).isNull();
    }

    @Test
    void save_persistsNewReservationForExistingGuestAndRoom() {
        ReservationEntity saved = reservationRepository.saveAndFlush(reservation(NEW_CHECKIN, NEW_CHECKOUT));

        assertThat(saved.getId()).isNotNull();

        entityManager.clear();

        ReservationEntity persisted = reservationRepository.findById(saved.getId()).orElseThrow();
        assertThat(persisted.getPrimaryGuest().getId()).isEqualTo(GUEST_ID);
        assertThat(persisted.getRoom().getId()).isEqualTo(ROOM_ID);
        assertThat(persisted.getCheckinDate()).isEqualTo(NEW_CHECKIN);
        assertThat(persisted.getCheckoutDate()).isEqualTo(NEW_CHECKOUT);
        assertThat(persisted.getNumberAdults()).isEqualTo(2);
        assertThat(persisted.getNumberChildren()).isZero();
        assertThat(persisted.getReservationNumber()).isNotBlank();
    }

    @Test
    void save_appliesDatabaseDefaults() {
        ReservationEntity reservation = new ReservationEntity();
        reservation.setPrimaryGuest(guest());
        reservation.setRoom(room());
        reservation.setCheckinDate(NEW_CHECKIN);
        reservation.setCheckoutDate(NEW_CHECKOUT);
        reservation.setReservationStatus(status());
        reservation.setReservationNumber(HotelUtils.generateConfirmationCode());

        ReservationEntity saved = reservationRepository.saveAndFlush(reservation);

        entityManager.clear();

        ReservationEntity persisted = reservationRepository.findById(saved.getId()).orElseThrow();
        assertThat(persisted.getNumberAdults()).isEqualTo(1);
        assertThat(persisted.getNumberChildren()).isZero();
        assertThat(persisted.getCreatedDate()).isNotNull();
        assertThat(persisted.getUpdatedDate()).isNotNull();
    }

    @Test
    void save_persistsActualCheckinAndCheckout() {
        ReservationEntity reservation = reservation(NEW_CHECKIN, NEW_CHECKOUT);
        reservation.setActualCheckin(LocalDateTime.of(2026, 12, 1, 15, 30));
        reservation.setActualCheckout(LocalDateTime.of(2026, 12, 5, 11, 15));

        ReservationEntity saved = reservationRepository.saveAndFlush(reservation);

        entityManager.clear();

        ReservationEntity persisted = reservationRepository.findById(saved.getId()).orElseThrow();
        assertThat(persisted.getActualCheckin()).isEqualTo(LocalDateTime.of(2026, 12, 1, 15, 30));
        assertThat(persisted.getActualCheckout()).isEqualTo(LocalDateTime.of(2026, 12, 5, 11, 15));
    }

    @Test
    void save_updatesExistingReservation() {
        ReservationEntity reservation = reservationRepository.findById(RESERVATION_ID).orElseThrow();
        reservation.setNumberAdults(3);
        reservation.setNumberChildren(2);

        reservationRepository.saveAndFlush(reservation);

        entityManager.clear();

        ReservationEntity persisted = reservationRepository.findById(RESERVATION_ID).orElseThrow();
        assertThat(persisted.getNumberAdults()).isEqualTo(3);
        assertThat(persisted.getNumberChildren()).isEqualTo(2);
        assertThat(persisted.getReservationNumber()).isEqualTo(RESERVATION_NUMBER);
    }

    @Test
    void uniqueConstraint_rejectsDuplicateReservationNumber() {
        ReservationEntity duplicate = reservation(NEW_CHECKIN, NEW_CHECKOUT);
        duplicate.setReservationNumber(RESERVATION_NUMBER);

        assertThatThrownBy(() -> reservationRepository.saveAndFlush(duplicate))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void uniqueConstraint_rejectsDuplicateGuestRoomAndCheckin() {
        ReservationEntity duplicate = reservation(CHECKIN, CHECKOUT);

        assertThatThrownBy(() -> reservationRepository.saveAndFlush(duplicate))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void delete_removesReservation() {
        ReservationEntity reservation = reservationRepository.findById(RESERVATION_ID).orElseThrow();

        reservationRepository.delete(reservation);
        reservationRepository.flush();

        assertThat(reservationRepository.findById(RESERVATION_ID)).isEmpty();
    }

    @Test
    void equalsAndHashCode_matchForSamePersistedReservation() {
        entityManager.clear();

        ReservationEntity first = reservationRepository.findById(RESERVATION_ID).orElseThrow();
        ReservationEntity second = reservationRepository.findById(RESERVATION_ID).orElseThrow();

        assertThat(first).isEqualTo(second).hasSameHashCodeAs(second);
        assertThat(first).isNotEqualTo(new ReservationEntity());
    }

    @Test
    void findByPrimaryGuestLastNameAndCheckinDate_returnsMatchingReservations() {
        List<ReservationEntity> reservations = reservationRepository.findByPrimaryGuest_LastNameAndCheckinDate("Holmes", CHECKIN);

        assertThat(reservations).isNotEmpty();
        assertThat(reservations.get(0).getReservationNumber()).isEqualTo(RESERVATION_NUMBER);
    }

    @Test
    void toString_includesFields() {
        ReservationEntity reservation = reservationRepository.findById(RESERVATION_ID).orElseThrow();

        assertThat(reservation.toString())
                .contains("ReservationEntity{")
                .contains("id=" + RESERVATION_ID)
                .contains("reservationNumber='" + RESERVATION_NUMBER + "'")
                .contains("numberAdults=2")
                .contains("numberChildren=0");
    }
}
