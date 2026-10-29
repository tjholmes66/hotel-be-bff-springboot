package org.tomholmes.opensource.hotel.bff.mapper;

import org.mapstruct.Mapper;
import org.tomholmes.opensource.hotel.bff.dto.UserRecord;
import org.tomholmes.opensource.hotel.bff.model.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper
{
    // 1. Single entity mapping rule (MapStruct uses this inside the list loop)
    UserRecord toRecord(UserEntity userEntity);

    // 2. Collection mapping (MapStruct auto-generates the loop under the hood)
    List<UserRecord> toRecordList(List<UserEntity> userEntityList);
}
