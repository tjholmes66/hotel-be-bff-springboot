package org.tomholmes.opensource.hotel.bff.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.tomholmes.opensource.hotel.bff.dto.BedTypeRecord;
import org.tomholmes.opensource.hotel.bff.model.BedTypeEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BedTypeMapperTest
{
    private BedTypeMapper bedTypeMapper;

    @BeforeEach
    void setUp()
    {
        bedTypeMapper = Mappers.getMapper(BedTypeMapper.class);
    }

    @Test
    void toRecord_mapsIdAndBedType()
    {
        BedTypeEntity bedTypeEntity = new BedTypeEntity();
        bedTypeEntity.setId(1L);
        bedTypeEntity.setBedType("Twin/Single");

        BedTypeRecord bedTypeRecord = bedTypeMapper.toRecord(bedTypeEntity);

        assertThat(bedTypeRecord).isNotNull();
        assertThat(bedTypeRecord.id()).isEqualTo(1L);
        assertThat(bedTypeRecord.bedType()).isEqualTo("Twin/Single");
    }

    @Test
    void toRecord_withNull_returnsNull()
    {
        assertThat(bedTypeMapper.toRecord((BedTypeEntity) null)).isNull();
    }

    @Test
    void toRecordList_mapsAllEntitiesPreservingOrder()
    {
        BedTypeEntity first = new BedTypeEntity();
        first.setId(1L);
        first.setBedType("Twin/Single");

        BedTypeEntity second = new BedTypeEntity();
        second.setId(2L);
        second.setBedType("Double Queen");

        List<BedTypeRecord> bedTypeRecords = bedTypeMapper.toRecordList(List.of(first, second));

        assertThat(bedTypeRecords)
                .hasSize(2)
                .containsExactly(
                        new BedTypeRecord(1L, "Twin/Single"),
                        new BedTypeRecord(2L, "Double Queen"));
    }

    @Test
    void toRecordList_withEmptyList_returnsEmptyList()
    {
        assertThat(bedTypeMapper.toRecordList(List.of())).isEmpty();
    }

    @Test
    void toRecordList_withNull_returnsNull()
    {
        assertThat(bedTypeMapper.toRecordList(null)).isNull();
    }
}
