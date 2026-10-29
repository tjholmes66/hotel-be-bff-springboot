package org.tomholmes.opensource.hotel.bff.service;

import org.springframework.stereotype.Service;
import org.tomholmes.opensource.hotel.bff.dto.UserRecord;
import org.tomholmes.opensource.hotel.bff.mapper.UserMapper;
import org.tomholmes.opensource.hotel.bff.model.UserEntity;
import org.tomholmes.opensource.hotel.bff.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    public final UserRepository repository;
    public final UserMapper mapper;

    public UserServiceImpl(UserRepository repository, UserMapper mapper)
    {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserEntity create(UserEntity userEntity)
    {
        UserEntity newEntity = repository.saveAndFlush(userEntity);
        return newEntity;
    }

    @Override
    public List<UserEntity> getAllUserEntities()
    {
        List<UserEntity> userList = repository.findAll();
        return userList;
    }

    @Override
    public List<UserRecord> getAllUserRecords()
    {
        List<UserEntity> userList = repository.findAll();
        List<UserRecord> userRecordList = mapper.toRecordList(userList);
        return userRecordList;
    }

    @Override
    public UserEntity update(UserEntity userEntity)
    {
        UserEntity updatedEntity = repository.saveAndFlush(userEntity);
        return updatedEntity;
    }
}
