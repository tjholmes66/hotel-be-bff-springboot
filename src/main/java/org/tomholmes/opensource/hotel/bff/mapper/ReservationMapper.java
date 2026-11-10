package org.tomholmes.opensource.hotel.bff.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tomholmes.opensource.hotel.bff.dto.ReservationRecord;
import org.tomholmes.opensource.hotel.bff.model.GuestEntity;
import org.tomholmes.opensource.hotel.bff.model.RateEntity;
import org.tomholmes.opensource.hotel.bff.model.ReservationEntity;
import org.tomholmes.opensource.hotel.bff.model.ReservationStatusEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper
{
    // 1. Single entity mapping rule (MapStruct uses this inside the list loop)
    @Mapping(source = "primaryGuest.id", target = "primaryGuestId")
    @Mapping(source = "rate.id", target = "rateId")
    @Mapping(source = "reservationStatus.id", target = "statusId")
    ReservationRecord toRecord(ReservationEntity reservationEntity);

    // 2. Collection mapping (MapStruct auto-generates the loop under the hood)
    List<ReservationRecord> toRecordList(List<ReservationEntity> reservationEntityList);

    // 3. Reverse mapping: record -> entity (for create/update)
    @Mapping(source = "primaryGuestId", target = "primaryGuest")
    @Mapping(source = "rateId", target = "rate")
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

    default RateEntity toRateEntity(Long rateId)
    {
        if (rateId == null)
        {
            return null;
        }
        RateEntity rateEntity = new RateEntity();
        rateEntity.setId(rateId);
        return rateEntity;
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
