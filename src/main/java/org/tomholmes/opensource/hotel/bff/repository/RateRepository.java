package org.tomholmes.opensource.hotel.bff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tomholmes.opensource.hotel.bff.model.RateEntity;

import java.util.List;
import java.util.Optional;

public interface RateRepository extends JpaRepository<RateEntity, Long> {

    Optional<RateEntity> findByRoom_IdAndSeason_Id(Long roomId, Long seasonId);

    List<RateEntity> findByRoomId(Long roomId);

    List<RateEntity> findBySeasonId(Long seasonId);

    RateEntity findRateEntityById(Long rateId);
}
