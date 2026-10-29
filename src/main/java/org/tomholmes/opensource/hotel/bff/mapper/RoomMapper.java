package org.tomholmes.opensource.hotel.bff.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tomholmes.opensource.hotel.bff.dto.RoomRecord;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper
{
    // 1. Single entity mapping rule (MapStruct uses this inside the list loop)
    @Mapping(source = "roomStatus.id", target = "roomStatusId")
    @Mapping(source = "bedType.id", target = "bedTypeId")
    RoomRecord toRecord(RoomEntity roomEntity);

    // 2. Collection mapping (MapStruct auto-generates the loop under the hood)
    List<RoomRecord> toRecordList(List<RoomEntity> roomEntityList);

    // 3. Reverse mapping: record -> entity (for create/update)
    @Mapping(source = "roomStatusId", target = "roomStatus.id")
    @Mapping(source = "bedTypeId", target = "bedType.id")
    RoomEntity toEntity(RoomRecord roomRecord);
}
