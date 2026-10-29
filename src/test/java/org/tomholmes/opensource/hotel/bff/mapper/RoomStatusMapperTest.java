package org.tomholmes.opensource.hotel.bff.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.tomholmes.opensource.hotel.bff.dto.RoomStatusRecord;
import org.tomholmes.opensource.hotel.bff.model.RoomStatusEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RoomStatusMapperTest
{
    private RoomStatusMapper roomStatusMapper;

    @BeforeEach
    void setUp()
    {
        roomStatusMapper = Mappers.getMapper(RoomStatusMapper.class);
    }

    @Test
    void toRecord_mapsAllFields()
    {
        LocalDateTime createdDate = LocalDateTime.of(2026, 1, 2, 3, 4, 5);
        LocalDateTime updatedDate = LocalDateTime.of(2026, 2, 3, 4, 5, 6);

        RoomStatusEntity roomStatusEntity = new RoomStatusEntity();
        roomStatusEntity.setId(1L);
        roomStatusEntity.setRoomStatusCode("AVAILABLE");
        roomStatusEntity.setRoomStatusName("Available");
        roomStatusEntity.setRoomStatusDescription("Available");
        roomStatusEntity.setCreatedBy(10L);
        roomStatusEntity.setCreatedDate(createdDate);
        roomStatusEntity.setUpdatedBy(20L);
        roomStatusEntity.setUpdatedDate(updatedDate);

        RoomStatusRecord roomStatusRecord = roomStatusMapper.toRecord(roomStatusEntity);

        assertThat(roomStatusRecord).isNotNull();
        assertThat(roomStatusRecord.id()).isEqualTo(1L);
        assertThat(roomStatusRecord.roomStatusCode()).isEqualTo("AVAILABLE");
        assertThat(roomStatusRecord.roomStatusName()).isEqualTo("Available");
        assertThat(roomStatusRecord.roomStatusDescription()).isEqualTo("Available");
        assertThat(roomStatusRecord.createdBy()).isEqualTo(10L);
        assertThat(roomStatusRecord.createdDate()).isEqualTo(createdDate);
        assertThat(roomStatusRecord.updatedBy()).isEqualTo(20L);
        assertThat(roomStatusRecord.updatedDate()).isEqualTo(updatedDate);
    }

    @Test
    void toRecord_withNull_returnsNull()
    {
        assertThat(roomStatusMapper.toRecord((RoomStatusEntity) null)).isNull();
    }

    @Test
    void toRecordList_mapsAllEntitiesPreservingOrder()
    {
        RoomStatusEntity first = new RoomStatusEntity();
        first.setId(1L);
        first.setRoomStatusCode("AVAILABLE");

        RoomStatusEntity second = new RoomStatusEntity();
        second.setId(2L);
        second.setRoomStatusCode("TURNOVER");

        List<RoomStatusRecord> roomStatusRecords = roomStatusMapper.toRecordList(List.of(first, second));

        assertThat(roomStatusRecords).hasSize(2);
        assertThat(roomStatusRecords).extracting(RoomStatusRecord::id).containsExactly(1L, 2L);
        assertThat(roomStatusRecords).extracting(RoomStatusRecord::roomStatusCode)
                .containsExactly("AVAILABLE", "TURNOVER");
    }

    @Test
    void toRecordList_withEmptyList_returnsEmptyList()
    {
        assertThat(roomStatusMapper.toRecordList(List.of())).isEmpty();
    }

    @Test
    void toRecordList_withNull_returnsNull()
    {
        assertThat(roomStatusMapper.toRecordList(null)).isNull();
    }
}
