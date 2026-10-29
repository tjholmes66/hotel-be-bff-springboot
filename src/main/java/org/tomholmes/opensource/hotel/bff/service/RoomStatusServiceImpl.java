package org.tomholmes.opensource.hotel.bff.service;

import org.springframework.stereotype.Service;
import org.tomholmes.opensource.hotel.bff.dto.RoomStatusRecord;
import org.tomholmes.opensource.hotel.bff.mapper.RoomStatusMapper;
import org.tomholmes.opensource.hotel.bff.model.RoomStatusEntity;
import org.tomholmes.opensource.hotel.bff.repository.RoomStatusRepository;

import java.util.List;

@Service
public class RoomStatusServiceImpl implements RoomStatusService
{
    public final RoomStatusRepository repository;
    public final RoomStatusMapper mapper;

    public RoomStatusServiceImpl(RoomStatusRepository repository, RoomStatusMapper mapper)
    {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public RoomStatusEntity create(RoomStatusEntity roomStatusEntity)
    {
        RoomStatusEntity newEntity = repository.saveAndFlush(roomStatusEntity);
        return newEntity;
    }

    @Override
    public RoomStatusRecord createRoomStatus(RoomStatusRecord roomStatusRecord)
    {
        RoomStatusEntity roomStatusEntity = mapper.toEntity(roomStatusRecord);
        RoomStatusEntity newEntity = repository.saveAndFlush(roomStatusEntity);
        RoomStatusRecord newRoomStatusRecord = mapper.toRecord(newEntity);
        return newRoomStatusRecord;
    }

    @Override
    public List<RoomStatusEntity> getAllRoomStatusEntities()
    {
        List<RoomStatusEntity> roomStatusList = repository.findAll();
        return roomStatusList;
    }

    @Override
    public List<RoomStatusRecord> getAllRoomStatusRecords()
    {
        List<RoomStatusEntity> roomStatusList = repository.findAll();
        List<RoomStatusRecord> roomStatusRecordList = mapper.toRecordList(roomStatusList);
        return roomStatusRecordList;
    }

    @Override
    public RoomStatusEntity getRoomStatusEntityById(Long roomStatusId)
    {
        RoomStatusEntity roomStatusEntity = repository.findRoomStatusEntityById(roomStatusId);
        return roomStatusEntity;
    }

    @Override
    public RoomStatusRecord getRoomStatusRecordById(Long roomStatusId)
    {
        RoomStatusEntity roomStatusEntity = repository.findRoomStatusEntityById(roomStatusId);
        RoomStatusRecord roomStatusRecord = mapper.toRecord(roomStatusEntity);
        return roomStatusRecord;
    }

    @Override
    public RoomStatusEntity update(RoomStatusEntity roomStatusEntity)
    {
        RoomStatusEntity updatedEntity = repository.saveAndFlush(roomStatusEntity);
        return updatedEntity;
    }
}
