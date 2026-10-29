package org.tomholmes.opensource.hotel.bff.mapper;

import org.mapstruct.Mapper;
import org.tomholmes.opensource.hotel.bff.dto.GuestRecord;
import org.tomholmes.opensource.hotel.bff.model.GuestEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestMapper
{
    // 1. Single entity mapping rule (MapStruct uses this inside the list loop)
    GuestRecord toRecord(GuestEntity guestEntity);

    // 2. Collection mapping (MapStruct auto-generates the loop under the hood)
    List<GuestRecord> toRecordList(List<GuestEntity> guestEntityList);

    // 3. Reverse mapping: record -> entity (for create/update)
    GuestEntity toEntity(GuestRecord guestRecord);
}
