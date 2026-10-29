package org.tomholmes.opensource.hotel.bff.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tomholmes.opensource.hotel.bff.dto.GuestRecord;
import org.tomholmes.opensource.hotel.bff.model.GuestEntity;
import org.tomholmes.opensource.hotel.bff.repository.GuestRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GuestServiceTest extends BaseServiceTests
{
    @Autowired
    private GuestService guestService;

    @Autowired
    private GuestRepository guestRepository;

    @Test
    //List<GuestEntity> getAllGuestEntities();
    public void testGetAllGuestEntities()
    {
        List<GuestEntity> allGuestEntities = guestService.getAllGuestEntities();
        assertNotNull(allGuestEntities);
    }

    @Test
    // List<GuestRecord> getAllGuestRecords();
    public void testGetAllGuestRecords()
    {
        List<GuestRecord> allGuestRecords = guestService.getAllGuestRecords();
        assertNotNull(allGuestRecords);
        assertEquals(guestService.getAllGuestEntities().size(), allGuestRecords.size());
    }

    @Test
    public void testFindByLastNameContainingIgnoreCase()
    {
        GuestEntity guest = new GuestEntity();
        guest.setFirstName("Test");
        guest.setLastName("Smithson");
        guest.setGuestEmail("smithson.test@example.com");
        guest.setGuestPhone("555-0999");
        guestRepository.saveAndFlush(guest);

        List<GuestEntity> results = guestService.findByLastNameContainingIgnoreCase("smith");
        assertNotNull(results);
    }

    @Test
    public void testFindByLastNameContainingIgnoreCaseRecords()
    {
        GuestEntity guest = new GuestEntity();
        guest.setFirstName("Test2");
        guest.setLastName("Smithson");
        guest.setGuestEmail("smithson.test2@example.com");
        guest.setGuestPhone("555-0998");
        guestRepository.saveAndFlush(guest);

        List<GuestRecord> results = guestService.findByLastNameContainingIgnoreCaseRecords("smith");
        assertNotNull(results);
    }

    @Test
    public void testFindByLastNameAndFirstNameContainingIgnoreCase()
    {
        GuestEntity guest = new GuestEntity();
        guest.setFirstName("John");
        guest.setLastName("Smithson");
        guest.setGuestEmail("smithson.john@example.com");
        guest.setGuestPhone("555-0997");
        guestRepository.saveAndFlush(guest);

        List<GuestEntity> results = guestService.findByLastNameAndFirstNameContainingIgnoreCase("smith", "john");
        assertNotNull(results);
    }

    @Test
    public void testFindByLastNameAndFirstNameContainingIgnoreCaseRecords()
    {
        GuestEntity guest = new GuestEntity();
        guest.setFirstName("Jane");
        guest.setLastName("Smithson");
        guest.setGuestEmail("smithson.jane@example.com");
        guest.setGuestPhone("555-0996");
        guestRepository.saveAndFlush(guest);

        List<GuestRecord> results = guestService.findByLastNameAndFirstNameContainingIgnoreCaseRecords("smith", "jane");
        assertNotNull(results);
    }

}
