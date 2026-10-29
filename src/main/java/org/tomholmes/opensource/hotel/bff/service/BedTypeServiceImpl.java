package org.tomholmes.opensource.hotel.bff.service;

import org.springframework.stereotype.Service;
import org.tomholmes.opensource.hotel.bff.dto.BedTypeRecord;
import org.tomholmes.opensource.hotel.bff.mapper.BedTypeMapper;
import org.tomholmes.opensource.hotel.bff.model.BedTypeEntity;
import org.tomholmes.opensource.hotel.bff.repository.BedTypeRepository;

import java.util.List;

@Service
public class BedTypeServiceImpl implements BedTypeService
{
    public final BedTypeRepository repository;
    public final BedTypeMapper mapper;

    public BedTypeServiceImpl(BedTypeRepository repository, BedTypeMapper mapper)
    {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public BedTypeEntity create(BedTypeEntity bedTypeEntity)
    {
        BedTypeEntity newEntity = repository.saveAndFlush(bedTypeEntity);
        return newEntity;
    }

    @Override
    public BedTypeRecord createBedType(BedTypeRecord bedTypeRecord)
    {
        BedTypeEntity bedTypeEntity = mapper.toEntity(bedTypeRecord);
        BedTypeEntity newEntity = repository.saveAndFlush(bedTypeEntity);
        BedTypeRecord newBedTypeRecord = mapper.toRecord(newEntity);
        return newBedTypeRecord;
    }

    @Override
    public List<BedTypeEntity> getAllBedTypeEntities()
    {
        List<BedTypeEntity> bedTypeList = repository.findAll();
        return bedTypeList;
    }

    @Override
    public List<BedTypeRecord> getAllBedTypeRecords()
    {
        List<BedTypeEntity> bedTypeList = repository.findAll();
        List<BedTypeRecord> bedTypeRecordList = mapper.toRecordList(bedTypeList);
        return bedTypeRecordList;
    }

    @Override
    public BedTypeEntity getBedTypeEntityById(Long bedTypeId)
    {
        BedTypeEntity bedTypeEntity = repository.findBedTypeEntityById(bedTypeId);
        return bedTypeEntity;
    }

    @Override
    public BedTypeRecord getBedTypeRecordById(Long bedTypeId)
    {
        BedTypeEntity bedTypeEntity = repository.findBedTypeEntityById(bedTypeId);
        BedTypeRecord bedTypeRecord = mapper.toRecord(bedTypeEntity);
        return bedTypeRecord;
    }

    @Override
    public BedTypeEntity update(BedTypeEntity bedTypeEntity)
    {
        BedTypeEntity updatedEntity = repository.saveAndFlush(bedTypeEntity);
        return updatedEntity;
    }
}
