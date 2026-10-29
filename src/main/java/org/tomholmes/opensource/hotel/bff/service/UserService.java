package org.tomholmes.opensource.hotel.bff.service;

import org.tomholmes.opensource.hotel.bff.dto.UserRecord;
import org.tomholmes.opensource.hotel.bff.model.UserEntity;

import java.util.List;

public interface UserService
{
    // CREATE
    UserEntity create(UserEntity userEntity);

    // RETRIEVE
    List<UserEntity> getAllUserEntities();

    List<UserRecord> getAllUserRecords();

    // UPDATE
    UserEntity update(UserEntity userEntity);

    // DELETE - no delete needed
    // void deleteUserById(long id);
    // void deleteUserByEntity(UserEntity userEntity);
}
