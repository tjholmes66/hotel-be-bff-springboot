package org.tomholmes.opensource.hotel.bff.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.tomholmes.opensource.hotel.bff.dto.RateRecord;
import org.tomholmes.opensource.hotel.bff.model.RateEntity;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;
import org.tomholmes.opensource.hotel.bff.model.SeasonEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RateMapperTest
{
    private RateMapper rateMapper;

    @BeforeEach
    void setUp()
    {
        rateMapper = Mappers.getMapper(RateMapper.class);
    }

    private RateEntity rateEntity(long id, long roomId, long seasonId, String amount)
    {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(roomId);

        SeasonEntity seasonEntity = new SeasonEntity();
        seasonEntity.setId(seasonId);

        RateEntity rateEntity = new RateEntity();
        rateEntity.setId(id);
        rateEntity.setRoom(roomEntity);
        rateEntity.setSeason(seasonEntity);
        rateEntity.setAmount(new BigDecimal(amount));
        return rateEntity;
    }

    @Test
    void toRecord_mapsAllFieldsAndForeignKeys()
    {
        RateEntity rateEntity = rateEntity(1L, 10L, 20L, "100.00");

        RateRecord rateRecord = rateMapper.toRecord(rateEntity);

        assertThat(rateRecord).isNotNull();
        assertThat(rateRecord.id()).isEqualTo(1L);
        assertThat(rateRecord.roomId()).isEqualTo(10L);
        assertThat(rateRecord.seasonId()).isEqualTo(20L);
        assertThat(rateRecord.amount()).isEqualByComparingTo("100.00");
    }

    @Test
    void toRecord_withNull_returnsNull()
    {
        assertThat(rateMapper.toRecord((RateEntity) null)).isNull();
    }

    @Test
    void toRecord_withNullRelationships_mapsForeignKeyIdsToNull()
    {
        RateEntity rateEntity = new RateEntity();
        rateEntity.setId(1L);

        RateRecord rateRecord = rateMapper.toRecord(rateEntity);

        assertThat(rateRecord).isNotNull();
        assertThat(rateRecord.id()).isEqualTo(1L);
        assertThat(rateRecord.roomId()).isNull();
        assertThat(rateRecord.seasonId()).isNull();
    }

    @Test
    void toRecordList_mapsAllEntitiesPreservingOrder()
    {
        RateEntity first = rateEntity(1L, 10L, 20L, "100.00");
        RateEntity second = rateEntity(2L, 11L, 21L, "120.00");

        List<RateRecord> rateRecords = rateMapper.toRecordList(List.of(first, second));

        assertThat(rateRecords).hasSize(2);
        assertThat(rateRecords).extracting(RateRecord::id).containsExactly(1L, 2L);
        assertThat(rateRecords).extracting(RateRecord::roomId).containsExactly(10L, 11L);
        assertThat(rateRecords).extracting(RateRecord::seasonId).containsExactly(20L, 21L);
    }

    @Test
    void toRecordList_withEmptyList_returnsEmptyList()
    {
        assertThat(rateMapper.toRecordList(List.of())).isEmpty();
    }

    @Test
    void toRecordList_withNull_returnsNull()
    {
        assertThat(rateMapper.toRecordList(null)).isNull();
    }

    @Test
    void toEntity_mapsFieldsAndForeignKeyIds()
    {
        RateRecord rateRecord = new RateRecord(1L, 10L, 20L, new BigDecimal("100.00"));

        RateEntity rateEntity = rateMapper.toEntity(rateRecord);

        assertThat(rateEntity).isNotNull();
        assertThat(rateEntity.getId()).isEqualTo(1L);
        assertThat(rateEntity.getRoom().getId()).isEqualTo(10L);
        assertThat(rateEntity.getSeason().getId()).isEqualTo(20L);
        assertThat(rateEntity.getAmount()).isEqualByComparingTo("100.00");
    }

    @Test
    void toEntity_withNullForeignKeyIds_leavesRelationshipsNull()
    {
        RateRecord rateRecord = new RateRecord(1L, null, null, new BigDecimal("100.00"));

        RateEntity rateEntity = rateMapper.toEntity(rateRecord);

        assertThat(rateEntity).isNotNull();
        assertThat(rateEntity.getRoom()).isNull();
        assertThat(rateEntity.getSeason()).isNull();
    }
}
