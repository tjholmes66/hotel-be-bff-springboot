package org.tomholmes.opensource.hotel.bff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tomholmes.opensource.hotel.bff.model.SeasonEntity;

import java.util.List;
import java.util.Optional;

public interface SeasonRepository extends JpaRepository<SeasonEntity, Long> {

    Optional<SeasonEntity> findBySeasonCode(String seasonCode);

    List<SeasonEntity> findBySeasonNameContainingIgnoreCase(String seasonName);

    SeasonEntity findSeasonEntityById(Long seasonId);
}
