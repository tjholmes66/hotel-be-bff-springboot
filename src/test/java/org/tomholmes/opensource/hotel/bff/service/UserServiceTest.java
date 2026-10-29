package org.tomholmes.opensource.hotel.bff.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tomholmes.opensource.hotel.bff.dto.UserRecord;
import org.tomholmes.opensource.hotel.bff.model.UserEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserServiceTest extends BaseServiceTests
{
    @Autowired
    private UserService userService;

    @Test
    //List<UserEntity> getAllUserEntities();
    public void testGetAllUserEntities()
    {
        List<UserEntity> allUserEntities = userService.getAllUserEntities();
        assertNotNull(allUserEntities);
    }

    @Test
    // List<UserRecord> getAllUserRecords();
    public void testGetAllUserRecords()
    {
        List<UserRecord> allUserRecords = userService.getAllUserRecords();
        assertNotNull(allUserRecords);
        assertEquals(userService.getAllUserEntities().size(), allUserRecords.size());
    }

}
