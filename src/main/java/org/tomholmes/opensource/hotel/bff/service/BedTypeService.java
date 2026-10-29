package org.tomholmes.opensource.hotel.bff.service;

import org.tomholmes.opensource.hotel.bff.dto.BedTypeRecord;
import org.tomholmes.opensource.hotel.bff.model.BedTypeEntity;

import java.util.List;

public interface BedTypeService
{
    // CREATE
    BedTypeEntity create(BedTypeEntity bedTypeEntity);

    BedTypeRecord createBedType(BedTypeRecord bedTypeRecord);

    // RETRIEVE
    List<BedTypeEntity> getAllBedTypeEntities();

    List<BedTypeRecord> getAllBedTypeRecords();

    BedTypeEntity getBedTypeEntityById(Long bedTypeId);

    BedTypeRecord getBedTypeRecordById(Long bedTypeRecordId);

    // UPDATE
    BedTypeEntity update(BedTypeEntity bedTypeEntity);

    // DELETE - no delete needed
    // void deleteBedTypeById(long id);
    // void deleteBedTypeByEntity(BedTypeEntity bedTypeEntity);
}
