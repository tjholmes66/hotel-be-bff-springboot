package org.tomholmes.opensource.hotel.bff.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.tomholmes.opensource.hotel.bff.dto.ReservationRecord;
import org.tomholmes.opensource.hotel.bff.model.GuestEntity;
import org.tomholmes.opensource.hotel.bff.model.ReservationEntity;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationMapperTest
{
    private ReservationMapper reservationMapper;

    @BeforeEach
    void setUp()
    {
        reservationMapper = Mappers.getMapper(ReservationMapper.class);
    }

    private ReservationEntity reservationEntity(long id, long guestId, long roomId, LocalDate checkinDate)
    {
        GuestEntity guestEntity = new GuestEntity();
        guestEntity.setId(guestId);

        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(roomId);

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setId(id);
        reservationEntity.setPrimaryGuest(guestEntity);
        reservationEntity.setRoom(roomEntity);
        reservationEntity.setCheckinDate(checkinDate);
        reservationEntity.setCheckoutDate(checkinDate.plusDays(2));
        reservationEntity.setNumberAdults(2);
        reservationEntity.setNumberChildren(1);
        reservationEntity.setCreatedDate(LocalDateTime.of(2026, 1, 2, 3, 4, 5));
        reservationEntity.setUpdatedDate(LocalDateTime.of(2026, 2, 3, 4, 5, 6));
        return reservationEntity;
    }

    @Test
    void toRecord_mapsAllFieldsAndForeignKeys()
    {
        LocalDate checkinDate = LocalDate.of(2026, 3, 4);
        ReservationEntity reservationEntity = reservationEntity(1L, 10L, 20L, checkinDate);

        ReservationRecord reservationRecord = reservationMapper.toRecord(reservationEntity);

        assertThat(reservationRecord).isNotNull();
        assertThat(reservationRecord.id()).isEqualTo(1L);
        assertThat(reservationRecord.primaryGuestId()).isEqualTo(10L);
        assertThat(reservationRecord.roomId()).isEqualTo(20L);
        assertThat(reservationRecord.checkinDate()).isEqualTo(checkinDate);
        assertThat(reservationRecord.checkoutDate()).isEqualTo(checkinDate.plusDays(2));
        assertThat(reservationRecord.numberAdults()).isEqualTo(2);
        assertThat(reservationRecord.numberChildren()).isEqualTo(1);
        assertThat(reservationRecord.createdDate()).isEqualTo(LocalDateTime.of(2026, 1, 2, 3, 4, 5));
        assertThat(reservationRecord.updatedDate()).isEqualTo(LocalDateTime.of(2026, 2, 3, 4, 5, 6));
    }

    @Test
    void toRecord_withNull_returnsNull()
    {
        assertThat(reservationMapper.toRecord((ReservationEntity) null)).isNull();
    }

    @Test
    void toRecord_withNullRelationships_mapsForeignKeyIdsToNull()
    {
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setId(1L);

        ReservationRecord reservationRecord = reservationMapper.toRecord(reservationEntity);

        assertThat(reservationRecord).isNotNull();
        assertThat(reservationRecord.id()).isEqualTo(1L);
        assertThat(reservationRecord.primaryGuestId()).isNull();
        assertThat(reservationRecord.roomId()).isNull();
    }

    @Test
    void toRecordList_mapsAllEntitiesPreservingOrder()
    {
        LocalDate checkinDate = LocalDate.of(2026, 3, 4);
        ReservationEntity first = reservationEntity(1L, 10L, 20L, checkinDate);
        ReservationEntity second = reservationEntity(2L, 11L, 21L, checkinDate.plusDays(1));

        List<ReservationRecord> reservationRecords = reservationMapper.toRecordList(List.of(first, second));

        assertThat(reservationRecords).hasSize(2);
        assertThat(reservationRecords).extracting(ReservationRecord::id).containsExactly(1L, 2L);
        assertThat(reservationRecords).extracting(ReservationRecord::primaryGuestId).containsExactly(10L, 11L);
    }

    @Test
    void toRecordList_withEmptyList_returnsEmptyList()
    {
        assertThat(reservationMapper.toRecordList(List.of())).isEmpty();
    }

    @Test
    void toRecordList_withNull_returnsNull()
    {
        assertThat(reservationMapper.toRecordList(null)).isNull();
    }

    @Test
    void toEntity_mapsFieldsAndForeignKeyIds()
    {
        LocalDate checkinDate = LocalDate.of(2026, 3, 4);
        ReservationRecord reservationRecord = new ReservationRecord(1L, 10L, 20L, checkinDate, checkinDate.plusDays(2), 2, 1, "ABC123", 30L,
                LocalDateTime.of(2026, 1, 2, 3, 4, 5), LocalDateTime.of(2026, 2, 3, 4, 5, 6), null, null, null, null );

        ReservationEntity reservationEntity = reservationMapper.toEntity(reservationRecord);

        assertThat(reservationEntity).isNotNull();
        assertThat(reservationEntity.getId()).isEqualTo(1L);
        assertThat(reservationEntity.getPrimaryGuest().getId()).isEqualTo(10L);
        assertThat(reservationEntity.getRoom().getId()).isEqualTo(20L);
        assertThat(reservationEntity.getReservationStatus().getId()).isEqualTo(30L);
        assertThat(reservationEntity.getCheckinDate()).isEqualTo(checkinDate);
        assertThat(reservationEntity.getCheckoutDate()).isEqualTo(checkinDate.plusDays(2));
        assertThat(reservationEntity.getNumberAdults()).isEqualTo(2);
        assertThat(reservationEntity.getNumberChildren()).isEqualTo(1);
        assertThat(reservationEntity.getReservationNumber()).isEqualTo("ABC123");
        assertThat(reservationEntity.getCreatedDate()).isEqualTo(LocalDateTime.of(2026, 1, 2, 3, 4, 5));
        assertThat(reservationEntity.getUpdatedDate()).isEqualTo(LocalDateTime.of(2026, 2, 3, 4, 5, 6));
    }

    @Test
    void toEntity_withNullForeignKeyIds_leavesRelationshipsNull()
    {
        ReservationRecord reservationRecord = new ReservationRecord(1L, null, null, LocalDate.of(2026, 3, 4), LocalDate.of(2026, 3, 6), 2, 0, null, null, null,
                null, null, null, null, null);

        ReservationEntity reservationEntity = reservationMapper.toEntity(reservationRecord);

        assertThat(reservationEntity).isNotNull();
        assertThat(reservationEntity.getPrimaryGuest()).isNull();
        assertThat(reservationEntity.getRoom()).isNull();
        assertThat(reservationEntity.getReservationStatus()).isNull();
    }
}
