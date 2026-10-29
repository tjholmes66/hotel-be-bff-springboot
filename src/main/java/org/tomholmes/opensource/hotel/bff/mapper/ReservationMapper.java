package org.tomholmes.opensource.hotel.bff.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tomholmes.opensource.hotel.bff.dto.ReservationRecord;
import org.tomholmes.opensource.hotel.bff.model.GuestEntity;
import org.tomholmes.opensource.hotel.bff.model.ReservationEntity;
import org.tomholmes.opensource.hotel.bff.model.ReservationStatusEntity;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper
{
    // 1. Single entity mapping rule (MapStruct uses this inside the list loop)
    @Mapping(source = "primaryGuest.id", target = "primaryGuestId")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "reservationStatus.id", target = "statusId")
    ReservationRecord toRecord(ReservationEntity reservationEntity);

    // 2. Collection mapping (MapStruct auto-generates the loop under the hood)
    List<ReservationRecord> toRecordList(List<ReservationEntity> reservationEntityList);

    // 3. Reverse mapping: record -> entity (for create/update)
    @Mapping(source = "primaryGuestId", target = "primaryGuest")
    @Mapping(source = "roomId", target = "room")
    @Mapping(source = "statusId", target = "reservationStatus")
    ReservationEntity toEntity(ReservationRecord reservationRecord);

    // 4. Relationship references: only create an entity when the id is present,
    //    otherwise Hibernate will see a transient instance with a null id.
    default GuestEntity toGuestEntity(Long guestId)
    {
        if (guestId == null)
        {
            return null;
        }
        GuestEntity guestEntity = new GuestEntity();
        guestEntity.setId(guestId);
        return guestEntity;
    }

    default RoomEntity toRoomEntity(Long roomId)
    {
        if (roomId == null)
        {
            return null;
        }
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(roomId);
        return roomEntity;
    }

    default ReservationStatusEntity toReservationStatusEntity(Long statusId)
    {
        if (statusId == null)
        {
            return null;
        }
        ReservationStatusEntity reservationStatusEntity = new ReservationStatusEntity();
        reservationStatusEntity.setId(statusId);
        return reservationStatusEntity;
    }
}
