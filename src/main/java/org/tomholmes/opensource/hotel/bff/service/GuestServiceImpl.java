package org.tomholmes.opensource.hotel.bff.service;

import org.springframework.stereotype.Service;
import org.tomholmes.opensource.hotel.bff.dto.GuestRecord;
import org.tomholmes.opensource.hotel.bff.mapper.GuestMapper;
import org.tomholmes.opensource.hotel.bff.model.GuestEntity;
import org.tomholmes.opensource.hotel.bff.repository.GuestRepository;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService
{
    public final GuestRepository repository;
    public final GuestMapper mapper;

    public GuestServiceImpl(GuestRepository repository, GuestMapper mapper)
    {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public GuestEntity create(GuestEntity guestEntity)
    {
        GuestEntity newEntity = repository.saveAndFlush(guestEntity);
        return newEntity;
    }

    @Override
    public GuestRecord createGuest(GuestRecord guestRecord)
    {
        GuestEntity guestEntity = mapper.toEntity(guestRecord);
        GuestEntity newEntity = repository.saveAndFlush(guestEntity);
        GuestRecord newGuestRecord = mapper.toRecord(newEntity);
        return newGuestRecord;
    }

    @Override
    public List<GuestEntity> getAllGuestEntities()
    {
        List<GuestEntity> guestList = repository.findAll();
        return guestList;
    }

    @Override
    public List<GuestRecord> getAllGuestRecords()
    {
        List<GuestEntity> guestList = repository.findAll();
        List<GuestRecord> guestRecordList = mapper.toRecordList(guestList);
        return guestRecordList;
    }

    @Override
    public GuestEntity getGuestEntityById(Long guestId)
    {
        GuestEntity guestEntity = repository.findGuestEntityById(guestId);
        return guestEntity;
    }

    @Override
    public GuestRecord getGuestRecordById(Long guestId)
    {
        GuestEntity guestEntity = repository.findGuestEntityById(guestId);
        GuestRecord guestRecord = mapper.toRecord(guestEntity);
        return guestRecord;
    }

    @Override
    public List<GuestEntity> findByLastNameContainingIgnoreCase(String lastName)
    {
        return repository.findByLastNameContainingIgnoreCase(lastName);
    }

    @Override
    public List<GuestRecord> findByLastNameContainingIgnoreCaseRecords(String lastName)
    {
        List<GuestEntity> guestList = repository.findByLastNameContainingIgnoreCase(lastName);
        return mapper.toRecordList(guestList);
    }

    @Override
    public List<GuestEntity> findByLastNameAndFirstNameContainingIgnoreCase(String lastName, String firstName)
    {
        return repository.findByLastNameAndFirstNameContainingIgnoreCase(lastName, firstName);
    }

    @Override
    public List<GuestRecord> findByLastNameAndFirstNameContainingIgnoreCaseRecords(String lastName, String firstName)
    {
        List<GuestEntity> guestList = repository.findByLastNameAndFirstNameContainingIgnoreCase(lastName, firstName);
        return mapper.toRecordList(guestList);
    }

    @Override
    public GuestEntity update(GuestEntity guestEntity)
    {
        GuestEntity updatedEntity = repository.saveAndFlush(guestEntity);
        return updatedEntity;
    }
}
