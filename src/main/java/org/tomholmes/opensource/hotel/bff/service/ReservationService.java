package org.tomholmes.opensource.hotel.bff.service;

import org.tomholmes.opensource.hotel.bff.dto.ReservationRecord;
import org.tomholmes.opensource.hotel.bff.model.ReservationEntity;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService
{
    // CREATE
    ReservationEntity create(ReservationEntity reservationEntity);

    ReservationRecord createReservation(ReservationRecord reservationRecord);

    // RETRIEVE
    List<ReservationEntity> getAllReservationEntities();

    List<ReservationRecord> getAllReservationRecords();

    ReservationEntity getReservationEntityById(Long reservationId);

    ReservationRecord getReservationRecordById(Long reservationId);

    ReservationEntity getReservationEntityByReservationNumber(String reservationNumber);

    ReservationRecord getReservationRecordByReservationNumber(String reservationNumber);

    List<ReservationEntity> getReservationEntitiesByPrimaryGuestIdAndCheckinDate(Long guestId, LocalDate checkinDate);

    List<ReservationRecord> getReservationRecordsByPrimaryGuestIdAndCheckinDate(Long guestId, LocalDate checkinDate);

    List<ReservationEntity> getReservationEntitiesByPrimaryGuestLastNameAndCheckinDate(String lastName, LocalDate checkinDate);

    List<ReservationRecord> getReservationRecordsByPrimaryGuestLastNameAndCheckinDate(String lastName, LocalDate checkinDate);

    // UPDATE
    ReservationEntity update(ReservationEntity reservationEntity);

    ReservationRecord reservationCheckIn(Long reservationId);

    ReservationRecord reservationCheckOut(Long reservationId);

    // DELETE - no delete needed
    // void deleteReservationById(long id);
    // void deleteReservationByEntity(ReservationEntity reservationEntity);
}
