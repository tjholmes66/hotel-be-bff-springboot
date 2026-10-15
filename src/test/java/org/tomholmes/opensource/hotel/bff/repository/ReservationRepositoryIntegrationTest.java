package org.tomholmes.opensource.hotel.bff.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.tomholmes.opensource.hotel.bff.model.GuestEntity;
import org.tomholmes.opensource.hotel.bff.model.ReservationEntity;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservationRepositoryIntegrationTest {

    private static final LocalDateTime CHECKIN = LocalDateTime.of(2026, 11, 1, 15, 0);
    private static final LocalDateTime CHECKOUT = LocalDateTime.of(2026, 11, 5, 11, 0);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BedTypeRepository bedTypeRepository;

    @Autowired
    private RoomStatusRepository roomStatusRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        roomRepository.deleteAll();
        guestRepository.deleteAll();
    }

    private GuestEntity guest(String name, String email, String phone) {
        GuestEntity guest = new GuestEntity();
        guest.setGuestName(name);
        guest.setGuestEmail(email);
        guest.setGuestPhone(phone);
        return guestRepository.save(guest);
    }

    private RoomEntity room(long id, int roomNumber) {
        RoomEntity room = new RoomEntity();
        room.setId(id);
        room.setRoomNumber(roomNumber);
        room.setRoomName("Seaside Breeze");
        room.setRoomDescription("A comfortable room with an ocean view");
        room.setOccupants(2);
        room.setBedType(bedTypeRepository.findById(1L).orElseThrow());
        room.setRoomStatus(roomStatusRepository.findByRoomStatusCode("AVAILABLE").orElseThrow());
        return roomRepository.saveAndFlush(room);
    }

    private ReservationEntity reservation(GuestEntity guest, RoomEntity room, LocalDateTime checkin, LocalDateTime checkout) {
        ReservationEntity reservation = new ReservationEntity();
        reservation.setPrimaryGuest(guest);
        reservation.setRoom(room);
        reservation.setCheckinDate(checkin);
        reservation.setCheckoutDate(checkout);
        reservation.setNumberAdults(2);
        reservation.setNumberChildren(1);
        return reservation;
    }

    @Test
    void save_findById_roundTripsReservation() {
        GuestEntity guest = guest("Grace Hopper", "grace@example.com", "555-0101");
        RoomEntity room = room(1L, 101);

        ReservationEntity saved = reservationRepository.saveAndFlush(reservation(guest, room, CHECKIN, CHECKOUT));

        assertThat(saved.getId()).isNotNull();

        entityManager.clear();

        ReservationEntity persisted = reservationRepository.findById(saved.getId()).orElseThrow();
        assertThat(persisted.getPrimaryGuest().getGuestEmail()).isEqualTo("grace@example.com");
        assertThat(persisted.getRoom().getRoomNumber()).isEqualTo(101);
        assertThat(persisted.getCheckinDate()).isEqualTo(CHECKIN);
        assertThat(persisted.getCheckoutDate()).isEqualTo(CHECKOUT);
        assertThat(persisted.getNumberAdults()).isEqualTo(2);
        assertThat(persisted.getNumberChildren()).isEqualTo(1);
    }

    @Test
    void save_appliesDatabaseDefaults() {
        GuestEntity guest = guest("Ada Lovelace", "ada@example.com", "555-0102");
        RoomEntity room = room(1L, 102);

        ReservationEntity reservation = reservation(guest, room, CHECKIN, CHECKOUT);
        reservation.setNumberAdults(null);
        reservation.setNumberChildren(null);

        ReservationEntity saved = reservationRepository.saveAndFlush(reservation);

        entityManager.clear();

        ReservationEntity persisted = reservationRepository.findById(saved.getId()).orElseThrow();
        assertThat(persisted.getNumberAdults()).isEqualTo(1);
        assertThat(persisted.getNumberChildren()).isEqualTo(0);
        assertThat(persisted.getCreatedDate()).isNotNull();
        assertThat(persisted.getUpdatedDate()).isNotNull();
    }

    @Test
    void findByPrimaryGuest_Id_returnsGuestReservations() {
        GuestEntity guest = guest("Alan Turing", "alan@example.com", "555-0103");
        RoomEntity room = room(1L, 103);

        reservationRepository.saveAndFlush(
                reservation(guest, room, CHECKIN, CHECKOUT));
        reservationRepository.saveAndFlush(
                reservation(guest, room, LocalDateTime.of(2026, 12, 1, 15, 0), LocalDateTime.of(2026, 12, 3, 11, 0)));

        entityManager.clear();

        List<ReservationEntity> reservations = reservationRepository.findByPrimaryGuest_Id(guest.getId());

        assertThat(reservations).hasSize(2);
        assertThat(reservationRepository.findByPrimaryGuest_Id(999L)).isEmpty();
    }

    @Test
    void findByRoom_Id_returnsRoomReservations() {
        GuestEntity guest = guest("Katherine Johnson", "katherine@example.com", "555-0104");
        RoomEntity room1 = room(1L, 104);
        RoomEntity room2 = room(2L, 105);

        reservationRepository.saveAndFlush(reservation(guest, room1, CHECKIN, CHECKOUT));
        reservationRepository.saveAndFlush(
                reservation(guest, room2, LocalDateTime.of(2026, 12, 1, 15, 0), LocalDateTime.of(2026, 12, 3, 11, 0)));

        entityManager.clear();

        assertThat(reservationRepository.findByRoom_Id(room1.getId()))
                .extracting(reservation -> reservation.getRoom().getRoomNumber())
                .containsExactly(104);
        assertThat(reservationRepository.findByRoom_Id(99L)).isEmpty();
    }

    @Test
    void findByPrimaryGuest_IdAndRoom_IdAndCheckinDate_returnsMatchingReservation() {
        GuestEntity guest = guest("Hedy Lamarr", "hedy@example.com", "555-0105");
        RoomEntity room = room(1L, 106);

        ReservationEntity saved = reservationRepository.saveAndFlush(reservation(guest, room, CHECKIN, CHECKOUT));

        entityManager.clear();

        assertThat(reservationRepository.findByPrimaryGuest_IdAndRoom_IdAndCheckinDate(
                guest.getId(), room.getId(), CHECKIN))
                .hasValueSatisfying(reservation -> assertThat(reservation.getId()).isEqualTo(saved.getId()));
        assertThat(reservationRepository.findByPrimaryGuest_IdAndRoom_IdAndCheckinDate(
                guest.getId(), room.getId(), LocalDateTime.of(2026, 11, 2, 15, 0))).isEmpty();
        assertThat(reservationRepository.findByPrimaryGuest_IdAndRoom_IdAndCheckinDate(
                999L, room.getId(), CHECKIN)).isEmpty();
    }

    @Test
    void uniqueConstraint_rejectsDuplicateGuestRoomAndCheckin() {
        GuestEntity guest = guest("Sally Ride", "sally@example.com", "555-0106");
        RoomEntity room = room(1L, 107);

        reservationRepository.saveAndFlush(reservation(guest, room, CHECKIN, CHECKOUT));

        ReservationEntity duplicate = reservation(guest, room, CHECKIN, LocalDateTime.of(2026, 11, 6, 11, 0));

        assertThatThrownBy(() -> reservationRepository.saveAndFlush(duplicate))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void delete_removesReservation() {
        GuestEntity guest = guest("Mae Jemison", "mae@example.com", "555-0107");
        RoomEntity room = room(1L, 108);
        ReservationEntity saved = reservationRepository.saveAndFlush(reservation(guest, room, CHECKIN, CHECKOUT));

        reservationRepository.delete(saved);
        reservationRepository.flush();

        assertThat(reservationRepository.findById(saved.getId())).isEmpty();
        assertThat(reservationRepository.findByRoom_Id(room.getId())).isEmpty();
    }

    @Test
    void equalsAndHashCode_areIdBased() {
        GuestEntity guest = guest("John Glenn", "john@example.com", "555-0108");
        RoomEntity room = room(1L, 109);
        ReservationEntity saved = reservationRepository.saveAndFlush(reservation(guest, room, CHECKIN, CHECKOUT));

        entityManager.clear();

        ReservationEntity first = reservationRepository.findById(saved.getId()).orElseThrow();
        ReservationEntity second = reservationRepository.findById(saved.getId()).orElseThrow();

        assertThat(first).isEqualTo(second).hasSameHashCodeAs(second);
        assertThat(first).isNotEqualTo(new ReservationEntity());
    }

}
