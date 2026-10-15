package org.tomholmes.opensource.hotel.bff.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.tomholmes.opensource.hotel.bff.model.RoomStatusEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomStatusRepositoryIntegrationTest {

    @Autowired
    private RoomStatusRepository roomStatusRepository;

    @Test
    void findAll_returnsSeededRoomStatuses() {
        List<RoomStatusEntity> roomStatuses = roomStatusRepository.findAll();

        assertThat(roomStatuses).hasSizeGreaterThanOrEqualTo(4);
        assertThat(roomStatuses).extracting(RoomStatusEntity::getRoomStatusCode)
                .contains("AVAILABLE", "TURNOVER", "CLOSED", "MAINTENANCE");
    }

    @Test
    void findByRoomStatusCode_returnsMatchingStatus() {
        assertThat(roomStatusRepository.findByRoomStatusCode("AVAILABLE"))
                .hasValueSatisfying(roomStatus -> {
                    assertThat(roomStatus.getId()).isEqualTo(1L);
                    assertThat(roomStatus.getRoomStatusName()).isEqualTo("Available");
                    assertThat(roomStatus.getRoomStatusDescription()).isEqualTo("Available");
                });
        assertThat(roomStatusRepository.findByRoomStatusCode("NONEXISTENT")).isEmpty();
    }

    @Test
    void save_findById_roundTripsNewRoomStatus() {
        RoomStatusEntity roomStatus = new RoomStatusEntity();
        roomStatus.setRoomStatusCode("OUT_OF_ORDER");
        roomStatus.setRoomStatusName("Out of Order");
        roomStatus.setRoomStatusDescription("Room is out of order");

        RoomStatusEntity saved = roomStatusRepository.save(roomStatus);

        assertThat(saved.getId()).isNotNull();
        assertThat(roomStatusRepository.findById(saved.getId()))
                .hasValueSatisfying(persisted -> assertThat(persisted.getRoomStatusCode()).isEqualTo("OUT_OF_ORDER"));
    }

    @Test
    void delete_removesRoomStatus() {
        RoomStatusEntity roomStatus = new RoomStatusEntity();
        roomStatus.setRoomStatusCode("VIP");
        roomStatus.setRoomStatusName("VIP Suite");
        roomStatus.setRoomStatusDescription("Reserved for VIPs");
        RoomStatusEntity saved = roomStatusRepository.save(roomStatus);
        roomStatusRepository.flush();

        roomStatusRepository.delete(saved);
        roomStatusRepository.flush();

        assertThat(roomStatusRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void equalsAndHashCode_areIdBased() {
        RoomStatusEntity first = roomStatusRepository.findById(1L).orElseThrow();
        RoomStatusEntity second = roomStatusRepository.findById(1L).orElseThrow();

        assertThat(first).isEqualTo(second).hasSameHashCodeAs(second);
        assertThat(first).isNotEqualTo(new RoomStatusEntity());
    }

    @Test
    void toString_includesFields() {
        RoomStatusEntity roomStatus = roomStatusRepository.findById(1L).orElseThrow();

        assertThat(roomStatus.toString())
                .contains("RoomStatusEntity{")
                .contains("id=1")
                .contains("roomStatusCode='AVAILABLE'");
    }
}
