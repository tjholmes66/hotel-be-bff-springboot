package org.tomholmes.opensource.hotel.bff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tomholmes.opensource.hotel.bff.model.BedTypeEntity;

import java.util.List;
import java.util.Optional;

public interface BedTypeRepository extends JpaRepository<BedTypeEntity, Long> {

    Optional<BedTypeEntity> findByBedType(String bedType);

    List<BedTypeEntity> findByBedTypeContainingIgnoreCase(String bedType);
}
