package org.tomholmes.opensource.hotel.bff.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.tomholmes.opensource.hotel.bff.dto.SeasonRecord;
import org.tomholmes.opensource.hotel.bff.model.SeasonEntity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SeasonMapperTest
{
    private SeasonMapper seasonMapper;

    @BeforeEach
    void setUp()
    {
        seasonMapper = Mappers.getMapper(SeasonMapper.class);
    }

    private SeasonEntity seasonEntity(long id, String seasonCode, LocalDate startDate)
    {
        SeasonEntity seasonEntity = new SeasonEntity();
        seasonEntity.setId(id);
        seasonEntity.setSeasonCode(seasonCode);
        seasonEntity.setSeasonName("Season " + seasonCode);
        seasonEntity.setSeasonDescription("Description " + seasonCode);
        seasonEntity.setStartDate(startDate);
        seasonEntity.setEndDate(startDate.plusMonths(3));
        return seasonEntity;
    }

    @Test
    void toRecord_mapsAllFields()
    {
        LocalDate startDate = LocalDate.of(2026, 1, 1);
        SeasonEntity seasonEntity = seasonEntity(1L, "2026_WINTER", startDate);

        SeasonRecord seasonRecord = seasonMapper.toRecord(seasonEntity);

        assertThat(seasonRecord).isNotNull();
        assertThat(seasonRecord.id()).isEqualTo(1L);
        assertThat(seasonRecord.seasonCode()).isEqualTo("2026_WINTER");
        assertThat(seasonRecord.seasonName()).isEqualTo("Season 2026_WINTER");
        assertThat(seasonRecord.seasonDescription()).isEqualTo("Description 2026_WINTER");
        assertThat(seasonRecord.startDate()).isEqualTo(startDate);
        assertThat(seasonRecord.endDate()).isEqualTo(startDate.plusMonths(3));
    }

    @Test
    void toRecord_withNull_returnsNull()
    {
        assertThat(seasonMapper.toRecord((SeasonEntity) null)).isNull();
    }

    @Test
    void toRecordList_mapsAllEntitiesPreservingOrder()
    {
        SeasonEntity first = seasonEntity(1L, "2026_WINTER", LocalDate.of(2026, 1, 1));
        SeasonEntity second = seasonEntity(2L, "2026_SPRING", LocalDate.of(2026, 4, 1));

        List<SeasonRecord> seasonRecords = seasonMapper.toRecordList(List.of(first, second));

        assertThat(seasonRecords).hasSize(2);
        assertThat(seasonRecords).extracting(SeasonRecord::id).containsExactly(1L, 2L);
        assertThat(seasonRecords).extracting(SeasonRecord::seasonCode).containsExactly("2026_WINTER", "2026_SPRING");
    }

    @Test
    void toRecordList_withEmptyList_returnsEmptyList()
    {
        assertThat(seasonMapper.toRecordList(List.of())).isEmpty();
    }

    @Test
    void toRecordList_withNull_returnsNull()
    {
        assertThat(seasonMapper.toRecordList(null)).isNull();
    }

    @Test
    void toEntity_mapsAllFields()
    {
        LocalDate startDate = LocalDate.of(2026, 1, 1);
        SeasonRecord seasonRecord = new SeasonRecord(1L, "2026_WINTER", "2026 Winter", "2026 Winter Season", startDate, startDate.plusMonths(3));

        SeasonEntity seasonEntity = seasonMapper.toEntity(seasonRecord);

        assertThat(seasonEntity).isNotNull();
        assertThat(seasonEntity.getId()).isEqualTo(1L);
        assertThat(seasonEntity.getSeasonCode()).isEqualTo("2026_WINTER");
        assertThat(seasonEntity.getSeasonName()).isEqualTo("2026 Winter");
        assertThat(seasonEntity.getSeasonDescription()).isEqualTo("2026 Winter Season");
        assertThat(seasonEntity.getStartDate()).isEqualTo(startDate);
        assertThat(seasonEntity.getEndDate()).isEqualTo(startDate.plusMonths(3));
    }
}
