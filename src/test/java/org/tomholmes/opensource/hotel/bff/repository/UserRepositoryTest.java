package org.tomholmes.opensource.hotel.bff.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.tomholmes.opensource.hotel.bff.model.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserRepositoryTest
{
    @Autowired
    private UserRepository userRepository;
    private String _username = "test";
    private String _emailAddress = "tom@tomholmes.xyz";
    private boolean _userActive = true;
    private String _userFirstName = "test_FN";
    private String _userLastName = "test_LN";
    private Long _editedBy = 1L;
    private Long _enteredBy = 1L;
    private LocalDateTime _editedDate = LocalDateTime.now();
    private LocalDateTime _enteredDate = LocalDateTime.now();
    private String _cellPhone = "123-456-7890";
    private String keycloakId = "30249857234058723405805874";

    private UserEntity save()
    {
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(_username);

        userEntity.setEnabled(_userActive);

        userEntity.setFirstName(_userFirstName);
        userEntity.setLastName(_userLastName);
        
        userEntity.setCellPhone(_cellPhone);
        userEntity.setEmail(_emailAddress);

        userEntity.setKeycloakId(keycloakId);

        // =====================================================
        // =====================================================

        userEntity.setUpdatedBy(_editedBy);
        userEntity.setUpdatedDate(_editedDate);
        userEntity.setCreatedBy(_enteredBy);
        userEntity.setCreatedDate(_enteredDate);

        return userEntity;
    }
    
    @Test
    public void testFindById() {
        Long id = 1L;
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        assertNotNull(userEntity);
        assertEquals(id, userEntity.getId());
    }

    // UserEntity save(UserEntity userEntity);
    @Test
    public void testUsersave()
    {
        System.out.println("testUsersave: START");

        UserEntity userEntity = save();

        userEntity = userRepository.save(userEntity);

        assertNotNull(userEntity);
        assertNotSame(0, userEntity.getId());
        assertEquals(_username, userEntity.getUsername());

        System.out.println("testUsersave: FINISH");
    }

    // UserEntity updateUserEntity(UserEntity userEntity);
    @Test
    public void testUserUpdate()
    {
        System.out.println("testUserUpdate: START");

        UserEntity userEntity = save();

        userEntity = userRepository.save(userEntity);

        assertNotNull(userEntity);
        assertNotSame(0, userEntity.getId());
        assertEquals(_username, userEntity.getUsername());

        String username = "updated";
        userEntity.setUsername(username);

        userEntity = userRepository.save(userEntity);

        assertNotNull(userEntity);
        assertNotSame(0, userEntity.getId());
        assertEquals(username, userEntity.getUsername());

        System.out.println("testUserUpdate: FINISH");
    }

    // void deleteUserEntity(Long userId);
    // void deleteUserEntity(UserEntity userEntity);

    // UserEntity getUserEntity(Long userId);
    @Test
    public void testGetUserEntityById()
    {
        System.out.println("testGetUserEntity: START");

        UserEntity userEntity = save();

        userEntity = userRepository.save(userEntity);

        assertNotNull(userEntity);
        assertNotSame(0, userEntity.getId());
        assertEquals(_username, userEntity.getUsername());

        Long userId = userEntity.getId();
        userEntity = userRepository.findById(userId).orElse(null);

        assertNotNull(userEntity);
        assertEquals(userId, userEntity.getId());
        assertEquals(_username, userEntity.getUsername());

        System.out.println("testGetUserEntity: FINISH");
    }

    // UserEntity getUserEntityByUsername(String username);
    @Test
    public void testGetUserEntityByUsername()
    {
        System.out.println("testGetUserEntityByUsername: START");

        UserEntity userEntity = save();

        userEntity = userRepository.save(userEntity);

        assertNotNull(userEntity);
        assertNotSame(0, userEntity.getId());
        assertEquals(_username, userEntity.getUsername());

        String username = userEntity.getUsername();
        userEntity = userRepository.findUserEntityByUsername(username);

        assertNotNull(userEntity);
        assertNotSame(0, userEntity.getId());
        assertEquals(_username, userEntity.getUsername());

        System.out.println("testGetUserEntityByUsername: FINISH");
    }

    // List<UserEntity> getAllUserEntityList();
    @Test
    public void testGetAllUserEntityList()
    {
        System.out.println("testGetAllUserEntityList: START");

        List<UserEntity> userList = userRepository.findAll();

        assertNotNull(userList);
        assertEquals(true, userList.size() > 0);

        System.out.println("testGetAllUserEntityList: FINISH");
    }

    @Test
    public void testGetUserEntityByEmailAddress()
    {
        System.out.println("testGetUserEntityByEmailAddress: START");

        UserEntity userEntity = save();

        userEntity = userRepository.save(userEntity);

        assertNotNull(userEntity);
        assertNotSame(0, userEntity.getId());
        assertEquals(_username, userEntity.getUsername());
        assertEquals(_emailAddress, userEntity.getEmail());

        String emailAddress = userEntity.getEmail();
        userEntity = userRepository.findUserByEmail(emailAddress);

        assertNotNull(userEntity);
        assertNotSame(0, userEntity.getId());
        assertEquals(_username, userEntity.getUsername());
        assertEquals(emailAddress, userEntity.getEmail());

        System.out.println("testGetUserEntityByEmailAddress: FINISH");
    }

    @Test
    public void testGetUserEntityByKeycloakId()
    {
        String keycloakId = "3c42807f-f1ac-4baa-9e02-444523b2cfeb";
        Optional<UserEntity> userEntity = userRepository.findByKeycloakId(keycloakId);
        assertNotNull(userEntity);
        assertTrue(userEntity.isPresent());
        assertEquals(keycloakId, userEntity.get().getKeycloakId());
    }

}
