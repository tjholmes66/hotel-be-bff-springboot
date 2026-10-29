package org.tomholmes.opensource.hotel.bff.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.tomholmes.opensource.hotel.bff.dto.RoomRecord;
import org.tomholmes.opensource.hotel.bff.model.BedTypeEntity;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;
import org.tomholmes.opensource.hotel.bff.model.RoomStatusEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RoomMapperTest
{
    private RoomMapper roomMapper;

    @BeforeEach
    void setUp()
    {
        roomMapper = Mappers.getMapper(RoomMapper.class);
    }

    private RoomEntity roomEntity(long id, int roomNumber, long roomStatusId, long bedTypeId)
    {
        RoomStatusEntity roomStatusEntity = new RoomStatusEntity();
        roomStatusEntity.setId(roomStatusId);

        BedTypeEntity bedTypeEntity = new BedTypeEntity();
        bedTypeEntity.setId(bedTypeId);

        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(id);
        roomEntity.setRoomNumber(roomNumber);
        roomEntity.setRoomName("Room " + roomNumber);
        roomEntity.setRoomDescription("Description " + roomNumber);
        roomEntity.setRoomStatus(roomStatusEntity);
        roomEntity.setOccupants(2);
        roomEntity.setBedType(bedTypeEntity);
        roomEntity.setCreatedBy(10L);
        roomEntity.setCreatedDate(LocalDateTime.of(2026, 1, 2, 3, 4, 5));
        roomEntity.setUpdatedBy(20L);
        roomEntity.setUdpatedDate(LocalDateTime.of(2026, 2, 3, 4, 5, 6));
        return roomEntity;
    }

    @Test
    void toRecord_mapsAllFieldsAndForeignKeys()
    {
        RoomEntity roomEntity = roomEntity(1L, 101, 3L, 7L);

        RoomRecord roomRecord = roomMapper.toRecord(roomEntity);

        assertThat(roomRecord).isNotNull();
        assertThat(roomRecord.id()).isEqualTo(1L);
        assertThat(roomRecord.roomNumber()).isEqualTo(101);
        assertThat(roomRecord.roomName()).isEqualTo("Room 101");
        assertThat(roomRecord.roomDescription()).isEqualTo("Description 101");
        assertThat(roomRecord.roomStatusId()).isEqualTo(3L);
        assertThat(roomRecord.occupants()).isEqualTo(2);
        assertThat(roomRecord.bedTypeId()).isEqualTo(7L);
        assertThat(roomRecord.createdBy()).isEqualTo(10L);
        assertThat(roomRecord.createdDate()).isEqualTo(LocalDateTime.of(2026, 1, 2, 3, 4, 5));
        assertThat(roomRecord.updatedBy()).isEqualTo(20L);
        assertThat(roomRecord.udpatedDate()).isEqualTo(LocalDateTime.of(2026, 2, 3, 4, 5, 6));
    }

    @Test
    void toRecord_withNull_returnsNull()
    {
        assertThat(roomMapper.toRecord((RoomEntity) null)).isNull();
    }

    @Test
    void toRecord_withNullRelationships_mapsForeignKeyIdsToNull()
    {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(1L);
        roomEntity.setRoomNumber(101);

        RoomRecord roomRecord = roomMapper.toRecord(roomEntity);

        assertThat(roomRecord).isNotNull();
        assertThat(roomRecord.id()).isEqualTo(1L);
        assertThat(roomRecord.roomNumber()).isEqualTo(101);
        assertThat(roomRecord.roomStatusId()).isNull();
        assertThat(roomRecord.bedTypeId()).isNull();
    }

    @Test
    void toRecordList_mapsAllEntitiesPreservingOrder()
    {
        RoomEntity first = roomEntity(1L, 101, 1L, 1L);
        RoomEntity second = roomEntity(2L, 102, 2L, 2L);

        List<RoomRecord> roomRecords = roomMapper.toRecordList(List.of(first, second));

        assertThat(roomRecords).hasSize(2);
        assertThat(roomRecords).extracting(RoomRecord::id).containsExactly(1L, 2L);
        assertThat(roomRecords).extracting(RoomRecord::roomNumber).containsExactly(101, 102);
    }

    @Test
    void toRecordList_withEmptyList_returnsEmptyList()
    {
        assertThat(roomMapper.toRecordList(List.of())).isEmpty();
    }

    @Test
    void toRecordList_withNull_returnsNull()
    {
        assertThat(roomMapper.toRecordList(null)).isNull();
    }
}
