package org.tomholmes.opensource.hotel.bff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tomholmes.opensource.hotel.bff.model.RoomStatusEntity;

import java.util.Optional;

public interface RoomStatusRepository extends JpaRepository<RoomStatusEntity, Long> {

    Optional<RoomStatusEntity> findByRoomStatusCode(String roomStatusCode);
}
