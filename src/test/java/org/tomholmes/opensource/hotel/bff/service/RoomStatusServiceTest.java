package org.tomholmes.opensource.hotel.bff.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tomholmes.opensource.hotel.bff.dto.RoomStatusRecord;
import org.tomholmes.opensource.hotel.bff.model.RoomStatusEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoomStatusServiceTest extends BaseServiceTests
{
    @Autowired
    private RoomStatusService roomStatusService;

    @Test
    //List<RoomStatusEntity> getAllRoomStatusEntities();
    public void testGetAllRoomStatusEntities()
    {
        List<RoomStatusEntity> allRoomStatusEntities = roomStatusService.getAllRoomStatusEntities();
        assertNotNull(allRoomStatusEntities);
        assertEquals(4, allRoomStatusEntities.size());
    }

    @Test
    // List<RoomStatusRecord> getAllRoomStatusRecords();
    public void testGetAllRoomStatusRecords()
    {
        List<RoomStatusRecord> allRoomStatusRecords = roomStatusService.getAllRoomStatusRecords();
        assertNotNull(allRoomStatusRecords);
        assertEquals(4, allRoomStatusRecords.size());
    }

}
