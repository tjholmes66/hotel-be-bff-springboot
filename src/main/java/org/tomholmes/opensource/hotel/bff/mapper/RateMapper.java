package org.tomholmes.opensource.hotel.bff.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tomholmes.opensource.hotel.bff.dto.RateRecord;
import org.tomholmes.opensource.hotel.bff.model.RateEntity;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;
import org.tomholmes.opensource.hotel.bff.model.SeasonEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RateMapper
{
    // 1. Single entity mapping rule (MapStruct uses this inside the list loop)
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "season.id", target = "seasonId")
    RateRecord toRecord(RateEntity rateEntity);

    // 2. Collection mapping (MapStruct auto-generates the loop under the hood)
    List<RateRecord> toRecordList(List<RateEntity> rateEntityList);

    // 3. Reverse mapping: record -> entity (for create/update)
    @Mapping(source = "roomId", target = "room")
    @Mapping(source = "seasonId", target = "season")
    RateEntity toEntity(RateRecord rateRecord);

    // 4. Relationship references: only create an entity when the id is present,
    //    otherwise Hibernate will see a transient instance with a null id.
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

    default SeasonEntity toSeasonEntity(Long seasonId)
    {
        if (seasonId == null)
        {
            return null;
        }
        SeasonEntity seasonEntity = new SeasonEntity();
        seasonEntity.setId(seasonId);
        return seasonEntity;
    }
}
