package org.tomholmes.opensource.hotel.bff.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.tomholmes.opensource.hotel.bff.dto.GuestRecord;
import org.tomholmes.opensource.hotel.bff.model.GuestEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GuestMapperTest
{
    private GuestMapper guestMapper;

    @BeforeEach
    void setUp()
    {
        guestMapper = Mappers.getMapper(GuestMapper.class);
    }

    @Test
    void toRecord_mapsAllFields()
    {
        LocalDateTime createdDate = LocalDateTime.of(2026, 1, 2, 3, 4, 5);
        LocalDateTime updatedDate = LocalDateTime.of(2026, 2, 3, 4, 5, 6);

        GuestEntity guestEntity = new GuestEntity();
        guestEntity.setId(1L);
        guestEntity.setFirstName("Grace");
        guestEntity.setLastName("Hopper");
        guestEntity.setGuestEmail("grace@example.com");
        guestEntity.setGuestPhone("555-0101");
        guestEntity.setGuestAdut(true);
        guestEntity.setCreatedBy(10L);
        guestEntity.setCreatedDate(createdDate);
        guestEntity.setUpdatedBy(20L);
        guestEntity.setUpdatedDate(updatedDate);

        GuestRecord guestRecord = guestMapper.toRecord(guestEntity);

        assertThat(guestRecord).isNotNull();
        assertThat(guestRecord.id()).isEqualTo(1L);
        assertThat(guestRecord.firstName()).isEqualTo("Grace");
        assertThat(guestRecord.lastName()).isEqualTo("Hopper");
        assertThat(guestRecord.guestEmail()).isEqualTo("grace@example.com");
        assertThat(guestRecord.guestPhone()).isEqualTo("555-0101");
        assertThat(guestRecord.guestAdut()).isTrue();
        assertThat(guestRecord.createdBy()).isEqualTo(10L);
        assertThat(guestRecord.createdDate()).isEqualTo(createdDate);
        assertThat(guestRecord.updatedBy()).isEqualTo(20L);
        assertThat(guestRecord.updatedDate()).isEqualTo(updatedDate);
    }

    @Test
    void toRecord_withNull_returnsNull()
    {
        assertThat(guestMapper.toRecord((GuestEntity) null)).isNull();
    }

    @Test
    void toRecordList_mapsAllEntitiesPreservingOrder()
    {
        GuestEntity first = new GuestEntity();
        first.setId(1L);
        first.setFirstName("Grace");
        first.setLastName("Hopper");

        GuestEntity second = new GuestEntity();
        second.setId(2L);
        second.setFirstName("Ada");
        second.setLastName("Lovelace");

        List<GuestRecord> guestRecords = guestMapper.toRecordList(List.of(first, second));

        assertThat(guestRecords).hasSize(2);
        assertThat(guestRecords).extracting(GuestRecord::id).containsExactly(1L, 2L);
        assertThat(guestRecords).extracting(GuestRecord::firstName)
                .containsExactly("Grace", "Ada");
    }

    @Test
    void toRecordList_withEmptyList_returnsEmptyList()
    {
        assertThat(guestMapper.toRecordList(List.of())).isEmpty();
    }

    @Test
    void toRecordList_withNull_returnsNull()
    {
        assertThat(guestMapper.toRecordList(null)).isNull();
    }
}
