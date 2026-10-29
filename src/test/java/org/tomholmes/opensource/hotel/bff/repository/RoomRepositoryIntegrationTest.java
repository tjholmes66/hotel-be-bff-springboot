package org.tomholmes.opensource.hotel.bff.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.tomholmes.opensource.hotel.bff.model.BedTypeEntity;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;
import org.tomholmes.opensource.hotel.bff.model.RoomStatusEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomRepositoryIntegrationTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BedTypeRepository bedTypeRepository;

    @Autowired
    private RoomStatusRepository roomStatusRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testFindById()
    {
        Long roomId = 1L;
        RoomEntity roomEntity = roomRepository.findById(roomId).orElse(null);
        assertNotNull(roomEntity);
        assertEquals(roomEntity.getId(), roomId);
    }

    @Test
    void testGetAllRooms()
    {
        Long roomCount = 9L;
        List<RoomEntity> roomEntities = roomRepository.findAll();
        assertNotNull(roomEntities);
        assertEquals(roomCount, roomEntities.size());
    }

    private Integer roomNumber = 10;
    private Long bedTypeId = 1L;
    private Integer occupants = 2;
    private Long roomStatusId = 1L;
    private Integer floor = 1;

    private RoomEntity createRoom() {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(null);
        roomEntity.setRoomNumber(roomNumber);
        roomEntity.setOccupants(occupants);
        roomEntity.setFloor(floor);

        BedTypeEntity bedTypeEntity = new BedTypeEntity();
        bedTypeEntity.setId(bedTypeId);
        roomEntity.setBedType(bedTypeEntity);

        RoomStatusEntity roomStatusEntity = new RoomStatusEntity();
        roomStatusEntity.setId(roomStatusId);
        roomEntity.setRoomStatus(roomStatusEntity);

        roomEntity =  roomRepository.saveAndFlush(roomEntity);
        return roomEntity;
    }

    @Test
    void testCreateRoom()
    {
        RoomEntity roomEntity = createRoom();
        roomEntity = roomRepository.saveAndFlush(roomEntity);
        assertNotNull(roomEntity);
        assertEquals(roomNumber, roomEntity.getRoomNumber());
        assertEquals(occupants, roomEntity.getOccupants());
        assertEquals(floor, roomEntity.getFloor());
        assertEquals(roomStatusId, roomEntity.getRoomStatus().getId());
        assertEquals(bedTypeId, roomEntity.getBedType().getId());
    }

}
