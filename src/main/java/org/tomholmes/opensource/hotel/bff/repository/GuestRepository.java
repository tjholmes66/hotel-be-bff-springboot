package org.tomholmes.opensource.hotel.bff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tomholmes.opensource.hotel.bff.model.GuestEntity;

import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<GuestEntity, Long> {

    Optional<GuestEntity> findByGuestEmail(String guestEmail);

    List<GuestEntity> findByGuestNameContainingIgnoreCase(String guestName);
}
