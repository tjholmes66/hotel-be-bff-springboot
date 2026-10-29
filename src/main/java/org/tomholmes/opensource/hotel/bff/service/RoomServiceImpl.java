package org.tomholmes.opensource.hotel.bff.service;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.tomholmes.opensource.hotel.bff.dto.RoomRecord;
import org.tomholmes.opensource.hotel.bff.mapper.RoomMapper;
import org.tomholmes.opensource.hotel.bff.model.RoomEntity;
import org.tomholmes.opensource.hotel.bff.model.UserEntity;
import org.tomholmes.opensource.hotel.bff.repository.RoomRepository;
import org.tomholmes.opensource.hotel.bff.repository.UserRepository;
import org.tomholmes.opensource.hotel.bff.utils.SecurityUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService
{
    public final RoomRepository roomRepository;
    public final UserRepository userRepository;
    public final RoomMapper mapper;

    public RoomServiceImpl(RoomRepository roomRepository, UserRepository userRepository, RoomMapper mapper)
    {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    private Long getCurrentUserId()
    {
        return SecurityUtils.getCurrentUserKeycloakId()
                .flatMap(userRepository::findByKeycloakId)
                .map(UserEntity::getId)
                .orElse(null);
    }


    @Override
    public RoomEntity create(RoomEntity roomEntity)
    {
        LocalDateTime today = LocalDateTime.now();
        roomEntity.setCreatedBy(getCurrentUserId());
        roomEntity.setCreatedDate(today);
        roomEntity.setUpdatedBy(getCurrentUserId());
        roomEntity.setUdpatedDate(today);
        RoomEntity newEntity = roomRepository.saveAndFlush(roomEntity);
        return newEntity;
    }

    @Override
    public RoomRecord createRoom(RoomRecord roomRecord)
    {
        LocalDateTime today = LocalDateTime.now();
        RoomEntity roomEntity = mapper.toEntity(roomRecord);
        roomEntity.setCreatedBy(getCurrentUserId());
        roomEntity.setCreatedDate(today);
        roomEntity.setUpdatedBy(getCurrentUserId());
        roomEntity.setUdpatedDate(today);
        RoomEntity newEntity = roomRepository.saveAndFlush(roomEntity);
        RoomRecord newRoomRecord = mapper.toRecord(newEntity);
        return newRoomRecord;
    }

    @Override
    public List<RoomEntity> getAllRoomEntities()
    {
        List<RoomEntity> roomList = roomRepository.findAll();
        return roomList;
    }

    @Override
    public List<RoomRecord> getAllRoomRecords()
    {
        List<RoomEntity> roomList = roomRepository.findAll();
        List<RoomRecord> roomRecordList = mapper.toRecordList(roomList);
        return roomRecordList;
    }

    @Override
    public RoomEntity getRoomEntityById(Long roomId)
    {
        RoomEntity roomEntity = roomRepository.findRoomEntityById(roomId);
        return roomEntity;
    }

    @Override
    public RoomRecord getRoomRecordById(Long roomId)
    {
        RoomEntity roomEntity = roomRepository.findRoomEntityById(roomId);
        RoomRecord roomRecord = mapper.toRecord(roomEntity);
        return roomRecord;
    }

    @Override
    public RoomEntity update(RoomEntity roomEntity)
    {
        RoomEntity updatedEntity = roomRepository.saveAndFlush(roomEntity);
        return updatedEntity;
    }
}
