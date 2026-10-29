package org.tomholmes.opensource.hotel.bff.mapper;

import org.mapstruct.Mapper;
import org.tomholmes.opensource.hotel.bff.dto.RoomStatusRecord;
import org.tomholmes.opensource.hotel.bff.model.RoomStatusEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomStatusMapper
{
    // 1. Single entity mapping rule (MapStruct uses this inside the list loop)
    RoomStatusRecord toRecord(RoomStatusEntity roomStatusEntity);

    // 2. Collection mapping (MapStruct auto-generates the loop under the hood)
    List<RoomStatusRecord> toRecordList(List<RoomStatusEntity> roomStatusEntityList);

    // 3. Reverse mapping: record -> entity (for create/update)
    RoomStatusEntity toEntity(RoomStatusRecord roomStatusRecord);
}
