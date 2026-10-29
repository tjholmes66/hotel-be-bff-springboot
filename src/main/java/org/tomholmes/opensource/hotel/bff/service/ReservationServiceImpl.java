package org.tomholmes.opensource.hotel.bff.service;

import org.springframework.stereotype.Service;
import org.tomholmes.opensource.hotel.bff.dto.ReservationRecord;
import org.tomholmes.opensource.hotel.bff.mapper.ReservationMapper;
import org.tomholmes.opensource.hotel.bff.model.ReservationEntity;
import org.tomholmes.opensource.hotel.bff.model.UserEntity;
import org.tomholmes.opensource.hotel.bff.repository.ReservationRepository;
import org.tomholmes.opensource.hotel.bff.repository.UserRepository;
import org.tomholmes.opensource.hotel.bff.utils.HotelUtils;
import org.tomholmes.opensource.hotel.bff.utils.SecurityUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService
{
    public final ReservationRepository reservationRepository;
    public final UserRepository userRepository;
    public final ReservationMapper mapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
    UserRepository userRepository,
    ReservationMapper mapper)
    {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    private Long getCurrentUserId()
    {
        return SecurityUtils.getCurrentUserKeycloakId()
        .flatMap(userRepository::findByKeycloakId)
        .map(UserEntity::getId)
        .orElse(null);
    }

    @Override
    public ReservationEntity create(ReservationEntity reservationEntity)
    {
        LocalDateTime today = LocalDateTime.now();
        reservationEntity.setCreatedDate(today);
        reservationEntity.setUpdatedDate(today);
        reservationEntity.setReservationNumber(HotelUtils.generateConfirmationCode());
        ReservationEntity newEntity = reservationRepository.saveAndFlush(reservationEntity);
        return newEntity;
    }

    @Override
    public ReservationRecord createReservation(ReservationRecord reservationRecord)
    {
        LocalDateTime today = LocalDateTime.now();
        ReservationEntity reservationEntity = mapper.toEntity(reservationRecord);
        reservationEntity.setCreatedDate(today);
        reservationEntity.setUpdatedDate(today);
        reservationEntity.setReservationNumber(HotelUtils.generateConfirmationCode());
        ReservationEntity newEntity = reservationRepository.saveAndFlush(reservationEntity);
        ReservationRecord newReservationRecord = mapper.toRecord(newEntity);
        return newReservationRecord;
    }

    @Override
    public List<ReservationEntity> getAllReservationEntities()
    {
        List<ReservationEntity> reservationList = reservationRepository.findAll();
        return reservationList;
    }

    @Override
    public List<ReservationRecord> getAllReservationRecords()
    {
        List<ReservationEntity> reservationList = reservationRepository.findAll();
        List<ReservationRecord> reservationRecordList = mapper.toRecordList(reservationList);
        return reservationRecordList;
    }

    @Override
    public ReservationEntity getReservationEntityById(Long reservationId)
    {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId).orElse(null);
        return reservationEntity;
    }

    @Override
    public ReservationRecord getReservationRecordById(Long reservationId)
    {
        ReservationEntity reservationEntity = reservationRepository.findById(reservationId).orElse(null);
        ReservationRecord reservationRecord = mapper.toRecord(reservationEntity);
        return reservationRecord;
    }

    @Override
    public ReservationEntity getReservationEntityByReservationNumber(String reservationNumber)
    {
        ReservationEntity reservationEntity = reservationRepository.findReservationEntityByReservationNumber(reservationNumber);
        return reservationEntity;
    }

    @Override
    public ReservationRecord getReservationRecordByReservationNumber(String reservationNumber)
    {
        ReservationEntity reservationEntity = reservationRepository.findReservationEntityByReservationNumber(reservationNumber);
        ReservationRecord reservationRecord = mapper.toRecord(reservationEntity);
        return reservationRecord;
    }

    @Override
    public List<ReservationEntity> getReservationEntitiesByPrimaryGuestIdAndCheckinDate(Long guestId, LocalDate checkinDate)
    {
        List<ReservationEntity> reservationList = reservationRepository.findByPrimaryGuestIdAndCheckinDate(guestId, checkinDate);
        return reservationList;
    }

    @Override
    public List<ReservationRecord> getReservationRecordsByPrimaryGuestIdAndCheckinDate(Long guestId, LocalDate checkinDate)
    {
        List<ReservationEntity> reservationList = reservationRepository.findByPrimaryGuestIdAndCheckinDate(guestId, checkinDate);
        List<ReservationRecord> reservationRecordList = mapper.toRecordList(reservationList);
        return reservationRecordList;
    }

    @Override
    public List<ReservationEntity> getReservationEntitiesByPrimaryGuestLastNameAndCheckinDate(String lastName, LocalDate checkinDate)
    {
        List<ReservationEntity> reservationList = reservationRepository.findByPrimaryGuest_LastNameAndCheckinDate(lastName, checkinDate);
        return reservationList;
    }

    @Override
    public List<ReservationRecord> getReservationRecordsByPrimaryGuestLastNameAndCheckinDate(String lastName, LocalDate checkinDate)
    {
        List<ReservationEntity> reservationList = reservationRepository.findByPrimaryGuest_LastNameAndCheckinDate(lastName, checkinDate);
        List<ReservationRecord> reservationRecordList = mapper.toRecordList(reservationList);
        return reservationRecordList;
    }

    /*
    This method does NOT create a new record.   It is presumed an existing reservation is already
    populating this ReservationRecord which means it already has a createdBy, createdDate, and reservationNumber
    There is NO NEED to check to see if those values are created.
     */
    @Override
    public ReservationRecord reservationCheckIn(Long reservationId)
    {
        Long userId = getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();
        ReservationEntity reservationEntity = getReservationEntityById(reservationId);
        //========================================================================
        // The record exists, so these are all the fields we want to update.
        reservationEntity.setUpdatedDate(now);
        reservationEntity.setActualCheckin(now);
        reservationEntity.setActualCheckinBy(userId);
        // save the reservation record
        ReservationEntity newEntity = reservationRepository.saveAndFlush(reservationEntity);
        //========================================================================
        ReservationRecord newReservationRecord = mapper.toRecord(newEntity);
        return newReservationRecord;
    }

    @Override
    public ReservationRecord reservationCheckOut(Long reservationId)
    {
        Long userId = getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();
        ReservationEntity reservationEntity = getReservationEntityById(reservationId);
        //========================================================================
        // The record exists, so these are all the fields we want to update.
        reservationEntity.setUpdatedDate(now);
        reservationEntity.setActualCheckout(now);
        reservationEntity.setActualCheckoutBy(userId);
        // save the reservation record
        ReservationEntity newEntity = reservationRepository.saveAndFlush(reservationEntity);
        //========================================================================
        ReservationRecord newReservationRecord = mapper.toRecord(newEntity);
        return newReservationRecord;
    }

    @Override
    public ReservationEntity update(ReservationEntity reservationEntity)
    {
        ReservationEntity updatedEntity = reservationRepository.saveAndFlush(reservationEntity);
        return updatedEntity;
    }
}
