package org.tomholmes.opensource.hotel.bff.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.tomholmes.opensource.hotel.bff.model.ReservationStatusEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservationStatusRepositoryIntegrationTest {

    @Autowired
    private ReservationStatusRepository reservationStatusRepository;

    @Test
    void findAll_returnsSeededReservationStatuses() {
        List<ReservationStatusEntity> reservationStatuses = reservationStatusRepository.findAll();

        assertThat(reservationStatuses).hasSizeGreaterThanOrEqualTo(3);
        assertThat(reservationStatuses).extracting(ReservationStatusEntity::getStatusCode)
                .contains("ACTIVE", "CANCELLED", "ON-HOLD");
    }

    @Test
    void findByStatusCode_returnsMatchingStatus() {
        assertThat(reservationStatusRepository.findByStatusCode("ACTIVE"))
                .satisfies(reservationStatus -> {
                    assertThat(reservationStatus.getId()).isEqualTo(1L);
                    assertThat(reservationStatus.getStatusName()).isEqualTo("Active");
                    assertThat(reservationStatus.getStatusDescription()).isEqualTo("Active");
                });
    }

    @Test
    void findById_returnsMatchingStatus() {
        assertThat(reservationStatusRepository.findById(1L))
                .hasValueSatisfying(reservationStatus -> {
                    assertThat(reservationStatus.getStatusCode()).isEqualTo("ACTIVE");
                    assertThat(reservationStatus.getStatusName()).isEqualTo("Active");
                });
        assertThat(reservationStatusRepository.findById(999L)).isEmpty();
    }

    @Test
    void save_findById_roundTripsNewReservationStatus() {
        ReservationStatusEntity reservationStatus = new ReservationStatusEntity();
        reservationStatus.setStatusCode("PENDING");
        reservationStatus.setStatusName("Pending");
        reservationStatus.setStatusDescription("Pending");

        ReservationStatusEntity saved = reservationStatusRepository.save(reservationStatus);

        assertThat(saved.getId()).isNotNull();
        assertThat(reservationStatusRepository.findById(saved.getId()))
                .hasValueSatisfying(persisted -> assertThat(persisted.getStatusCode()).isEqualTo("PENDING"));
    }

    @Test
    void delete_removesReservationStatus() {
        ReservationStatusEntity reservationStatus = new ReservationStatusEntity();
        reservationStatus.setStatusCode("EXPIRED");
        reservationStatus.setStatusName("Expired");
        reservationStatus.setStatusDescription("Expired");
        ReservationStatusEntity saved = reservationStatusRepository.save(reservationStatus);
        reservationStatusRepository.flush();

        reservationStatusRepository.delete(saved);
        reservationStatusRepository.flush();

        assertThat(reservationStatusRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void equalsAndHashCode_areIdBased() {
        ReservationStatusEntity first = reservationStatusRepository.findById(1L).orElseThrow();
        ReservationStatusEntity second = reservationStatusRepository.findById(1L).orElseThrow();

        assertThat(first).isEqualTo(second).hasSameHashCodeAs(second);
        assertThat(first).isNotEqualTo(new ReservationStatusEntity());
    }

    @Test
    void toString_includesFields() {
        ReservationStatusEntity reservationStatus = reservationStatusRepository.findById(1L).orElseThrow();

        assertThat(reservationStatus.toString())
                .contains("ReservationStatusEntity{")
                .contains("id=1")
                .contains("statusCode='ACTIVE'");
    }
}
