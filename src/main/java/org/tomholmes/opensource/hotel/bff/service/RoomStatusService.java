package org.tomholmes.opensource.hotel.bff.service;

import org.tomholmes.opensource.hotel.bff.dto.RoomStatusRecord;
import org.tomholmes.opensource.hotel.bff.model.RoomStatusEntity;

import java.util.List;

public interface RoomStatusService
{
    // CREATE
    RoomStatusEntity create(RoomStatusEntity roomStatusEntity);

    RoomStatusRecord createRoomStatus(RoomStatusRecord roomStatusRecord);

    // RETRIEVE
    List<RoomStatusEntity> getAllRoomStatusEntities();

    List<RoomStatusRecord> getAllRoomStatusRecords();

    RoomStatusEntity getRoomStatusEntityById(Long roomStatusId);

    RoomStatusRecord getRoomStatusRecordById(Long roomStatusId);

    // UPDATE
    RoomStatusEntity update(RoomStatusEntity roomStatusEntity);

    // DELETE - no delete needed
    // void deleteRoomStatusById(long id);
    // void deleteRoomStatusByEntity(RoomStatusEntity roomStatusEntity);
}
