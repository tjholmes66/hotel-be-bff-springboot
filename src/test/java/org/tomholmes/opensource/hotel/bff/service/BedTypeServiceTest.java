package org.tomholmes.opensource.hotel.bff.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tomholmes.opensource.hotel.bff.dto.BedTypeRecord;
import org.tomholmes.opensource.hotel.bff.model.BedTypeEntity;
import org.tomholmes.opensource.hotel.bff.repository.BedTypeRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BedTypeServiceTest extends BaseServiceTests
{
    @Autowired
    private BedTypeService bedTypeService;

    @Test
    //List<BedTypeEntity> getAllBedTypeEntities();
    public void testGetAllBedTypeEntities()
    {
        List<BedTypeEntity> allBedTypeEntities = bedTypeService.getAllBedTypeEntities();
        assertNotNull(allBedTypeEntities);
        assertEquals(9, allBedTypeEntities.size());
    }

    @Test
    // List<BedTypeRecord> getAllBedTypeRecords();
    public void testGetAllBedTypeRecords()
    {
        List<BedTypeRecord> allBedTypeRecords = bedTypeService.getAllBedTypeRecords();
        assertNotNull(allBedTypeRecords);
        assertEquals(9, allBedTypeRecords.size());
    }

}
