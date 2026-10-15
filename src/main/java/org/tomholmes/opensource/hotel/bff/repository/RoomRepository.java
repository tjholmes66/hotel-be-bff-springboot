package org.tomholmes.opensource.hotel.bff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    Optional<RoomEntity> findByRoomNumber(Integer roomNumber);

    List<RoomEntity> findByRoomStatus_Id(Long roomStatusId);

    List<RoomEntity> findByBedTypeId(Long bedTypeId);
}
