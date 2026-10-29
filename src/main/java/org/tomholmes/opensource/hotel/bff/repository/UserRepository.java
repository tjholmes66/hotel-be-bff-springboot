package org.tomholmes.opensource.hotel.bff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.tomholmes.opensource.hotel.bff.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    @Query(value = "from UserEntity u where (u.email = :email)")
    UserEntity findUserByEmail(String email);

    @Query(value = "from UserEntity u where (u.username = :username)")
    UserEntity findUserEntityByUsername(String username);

    Optional<UserEntity> findByKeycloakId(String keycloakId);
}