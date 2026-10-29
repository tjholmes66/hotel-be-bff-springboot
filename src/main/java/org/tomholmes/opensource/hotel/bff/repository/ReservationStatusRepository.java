package org.tomholmes.opensource.hotel.bff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tomholmes.opensource.hotel.bff.model.ReservationStatusEntity;
import org.tomholmes.opensource.hotel.bff.model.RoomStatusEntity;

import java.util.Optional;

public interface ReservationStatusRepository extends JpaRepository<ReservationStatusEntity, Long> {

    Optional<ReservationStatusEntity> findById(Long id);

    ReservationStatusEntity findByStatusCode(String code);
}
