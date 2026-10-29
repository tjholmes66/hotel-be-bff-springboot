package org.tomholmes.opensource.hotel.bff.service;

import org.tomholmes.opensource.hotel.bff.dto.GuestRecord;
import org.tomholmes.opensource.hotel.bff.model.GuestEntity;

import java.util.List;

public interface GuestService
{
    // CREATE
    GuestEntity create(GuestEntity guestEntity);

    GuestRecord createGuest(GuestRecord guestRecord);

    // RETRIEVE
    List<GuestEntity> getAllGuestEntities();

    List<GuestRecord> getAllGuestRecords();

    GuestEntity getGuestEntityById(Long guestId);

    GuestRecord getGuestRecordById(Long guestId);

    List<GuestEntity> findByLastNameContainingIgnoreCase(String lastName);

    List<GuestRecord> findByLastNameContainingIgnoreCaseRecords(String lastName);

    List<GuestEntity> findByLastNameAndFirstNameContainingIgnoreCase(String lastName, String firstName);

    List<GuestRecord> findByLastNameAndFirstNameContainingIgnoreCaseRecords(String lastName, String firstName);

    // UPDATE
    GuestEntity update(GuestEntity guestEntity);

    // DELETE - no delete needed
    // void deleteGuestById(long id);
    // void deleteGuestByEntity(GuestEntity guestEntity);
}
