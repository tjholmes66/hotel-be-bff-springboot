package org.tomholmes.opensource.hotel.bff.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tomholmes.opensource.hotel.bff.dto.BedTypeRecord;
import org.tomholmes.opensource.hotel.bff.model.BedTypeEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BedTypeMapper
{
    // 1. Single entity mapping rule (MapStruct uses this inside the list loop)
    BedTypeRecord toRecord(BedTypeEntity bedTypeEntity);

    // 2. Collection mapping (MapStruct auto-generates the loop under the hood)
    List<BedTypeRecord> toRecordList(List<BedTypeEntity> bedTypeEntityList);

    // 3. Reverse mapping: record -> entity (for create/update)
    BedTypeEntity toEntity(BedTypeRecord bedTypeRecord);
}
