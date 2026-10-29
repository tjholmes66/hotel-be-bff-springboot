package org.tomholmes.opensource.hotel.bff.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tomholmes.opensource.hotel.bff.model.ReservationEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByPrimaryGuestId(Long guestId);

    List<ReservationEntity> findByRoomId(Long roomId);

    Optional<ReservationEntity> findByPrimaryGuest_IdAndRoom_IdAndCheckinDate(Long guestId, Long roomId,
                                                                       LocalDate checkinDate);

    List<ReservationEntity> findByPrimaryGuestIdAndCheckinDate(Long guestId, LocalDate checkinDate);

    List<ReservationEntity> findByPrimaryGuest_LastNameAndCheckinDate(String lastName, LocalDate checkinDate);

    ReservationEntity findReservationEntityByReservationNumber(String reservationNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(
    "SELECT r FROM ReservationEntity r WHERE r.room.id = :roomId AND " +
    "r.reservationStatus.statusCode != 'CANCELLED' " +
    "AND r.checkinDate < :checkOutDate " +
    "AND r.checkoutDate > :checkInDate")
    List<ReservationEntity> findOverlappingReservations(
    @Param("roomId") Long roomId,
    @Param("checkInDate") LocalDate checkInDate,
    @Param("checkOutDate") LocalDate checkOutDate);
}
