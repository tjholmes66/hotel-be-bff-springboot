package org.tomholmes.opensource.hotel.bff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tomholmes.opensource.hotel.bff.model.ReservationEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByPrimaryGuest_Id(Long guestId);

    List<ReservationEntity> findByRoom_Id(Long roomId);

    Optional<ReservationEntity> findByPrimaryGuest_IdAndRoom_IdAndCheckinDate(Long guestId, Long roomId,
                                                                       LocalDateTime checkinDate);
}
