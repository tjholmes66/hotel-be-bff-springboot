package org.tomholmes.opensource.hotel.bff.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tomholmes.opensource.hotel.bff.dto.RoomRecord;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoomServiceTest extends BaseServiceTests
{
    @Autowired
    private RoomService roomService;

    @Test
    //List<RoomEntity> getAllRoomEntities();
    public void testGetAllRoomEntities()
    {
        List<RoomEntity> allRoomEntities = roomService.getAllRoomEntities();
        assertNotNull(allRoomEntities);
    }

    @Test
    // List<RoomRecord> getAllRoomRecords();
    public void testGetAllRoomRecords()
    {
        List<RoomRecord> allRoomRecords = roomService.getAllRoomRecords();
        assertNotNull(allRoomRecords);
        assertEquals(roomService.getAllRoomEntities().size(), allRoomRecords.size());
    }

}
