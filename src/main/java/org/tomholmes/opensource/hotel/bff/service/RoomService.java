package org.tomholmes.opensource.hotel.bff.service;

import org.tomholmes.opensource.hotel.bff.dto.RoomRecord;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;

import java.util.List;

public interface RoomService
{
    // CREATE
    RoomEntity create(RoomEntity roomEntity);

    RoomRecord createRoom(RoomRecord roomRecord);

    // RETRIEVE
    List<RoomEntity> getAllRoomEntities();

    List<RoomRecord> getAllRoomRecords();

    RoomEntity getRoomEntityById(Long roomId);

    RoomRecord getRoomRecordById(Long roomId);

    // UPDATE
    RoomEntity update(RoomEntity roomEntity);

    // DELETE - no delete needed
    // void deleteRoomById(long id);
    // void deleteRoomByEntity(RoomEntity roomEntity);
}
