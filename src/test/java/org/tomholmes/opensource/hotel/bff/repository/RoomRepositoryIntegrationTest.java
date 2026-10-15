package org.tomholmes.opensource.hotel.bff.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomRepositoryIntegrationTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

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
        return room;
    }

    @Test
    void save_findById_roundTripsRoom() {
        RoomEntity saved = roomRepository.saveAndFlush(room(1L, 101));

        entityManager.clear();

        RoomEntity persisted = roomRepository.findById(saved.getId()).orElseThrow();
        assertThat(persisted.getRoomNumber()).isEqualTo(101);
        assertThat(persisted.getRoomName()).isEqualTo("Seaside Breeze");
        assertThat(persisted.getRoomDescription()).isEqualTo("A comfortable room with an ocean view");
        assertThat(persisted.getOccupants()).isEqualTo(2);
        assertThat(persisted.getBedType().getBedType()).isEqualTo("Twin/Single");
        assertThat(persisted.getRoomStatus().getRoomStatusCode()).isEqualTo("AVAILABLE");
    }

    @Test
    void save_appliesDatabaseDefaults() {
        RoomEntity saved = roomRepository.saveAndFlush(room(1L, 102));

        entityManager.clear();

        RoomEntity persisted = roomRepository.findById(saved.getId()).orElseThrow();
        assertThat(persisted.getCreatedBy()).isEqualTo(1L);
        assertThat(persisted.getUpdatedBy()).isEqualTo(1L);
        assertThat(persisted.getCreatedDate()).isNotNull();
        assertThat(persisted.getUdpatedDate()).isNotNull();
    }

    @Test
    void findByRoomNumber_returnsMatchingRoom() {
        roomRepository.saveAndFlush(room(1L, 201));

        assertThat(roomRepository.findByRoomNumber(201))
                .hasValueSatisfying(room -> assertThat(room.getId()).isEqualTo(1L));
        assertThat(roomRepository.findByRoomNumber(999)).isEmpty();
    }

    @Test
    void findByRoomStatus_Id_returnsRoomStatusMatches() {
        roomRepository.saveAndFlush(room(1L, 301));

        assertThat(roomRepository.findByRoomStatus_Id(1L))
                .extracting(RoomEntity::getRoomNumber)
                .containsExactly(301);
        assertThat(roomRepository.findByRoomStatus_Id(99L)).isEmpty();
    }

    @Test
    void findByBedTypeId_returnsBedTypeMatches() {
        RoomEntity thisRoom = room(1L, 401);
        RoomEntity roomEntity = roomRepository.saveAndFlush(thisRoom);
        assertNotNull(roomEntity);
        assertEquals(1L, roomEntity.getBedType().getId());
        assertThat(roomRepository.findByBedTypeId(99L)).isEmpty();
    }

    @Test
    void delete_removesRoom() {
        RoomEntity saved = roomRepository.saveAndFlush(room(1L, 501));

        roomRepository.delete(saved);
        roomRepository.flush();

        assertThat(roomRepository.findById(saved.getId())).isEmpty();
        assertThat(roomRepository.findByRoomNumber(501)).isEmpty();
    }

    @Test
    void equalsAndHashCode_areIdBased() {
        roomRepository.saveAndFlush(room(1L, 601));

        entityManager.clear();

        RoomEntity first = roomRepository.findById(1L).orElseThrow();
        RoomEntity second = roomRepository.findById(1L).orElseThrow();

        assertThat(first).isEqualTo(second).hasSameHashCodeAs(second);
        assertThat(first).isNotEqualTo(new RoomEntity());
    }

}
