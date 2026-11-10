package org.tomholmes.opensource.hotel.bff.mapper;

import org.mapstruct.Mapper;
import org.tomholmes.opensource.hotel.bff.dto.SeasonRecord;
import org.tomholmes.opensource.hotel.bff.model.SeasonEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeasonMapper
{
    // 1. Single entity mapping rule (MapStruct uses this inside the list loop)
    SeasonRecord toRecord(SeasonEntity seasonEntity);

    // 2. Collection mapping (MapStruct auto-generates the loop under the hood)
    List<SeasonRecord> toRecordList(List<SeasonEntity> seasonEntityList);

    // 3. Reverse mapping: record -> entity (for create/update)
    SeasonEntity toEntity(SeasonRecord seasonRecord);
}
