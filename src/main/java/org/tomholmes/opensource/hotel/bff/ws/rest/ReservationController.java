package org.tomholmes.opensource.hotel.bff.ws.rest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tomholmes.opensource.hotel.bff.dto.ReservationRecord;
import org.tomholmes.opensource.hotel.bff.service.ReservationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController
{
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('FRONT-DESK', 'ADMIN')")
    public ResponseEntity<List<ReservationRecord>> getAllReservations()
    {
        List<ReservationRecord> reservationRecordList = reservationService.getAllReservationRecords();
        return ResponseEntity.ok(reservationRecordList);
    }

    @GetMapping("/{reservationId}")
    @PreAuthorize("hasAnyRole('FRONT-DESK','CONCIERGE' ,'ADMIN')")
    public ResponseEntity<ReservationRecord> getReservationById(@PathVariable Long reservationId)
    {
        ReservationRecord reservationRecord = reservationService.getReservationRecordById(reservationId);
        return ResponseEntity.ok(reservationRecord);
    }

    @GetMapping("/reservationNumber/{reservationNumber}")
    @PreAuthorize("hasAnyRole('FRONT-DESK','CONCIERGE' ,'ADMIN')")
    public ResponseEntity<ReservationRecord> getReservationByReservationNumber(@PathVariable String reservationNumber)
    {
        ReservationRecord reservationRecord = reservationService.getReservationRecordByReservationNumber(reservationNumber);
        return ResponseEntity.ok(reservationRecord);
    }

    @GetMapping("/primaryGuest/{primaryGuestId}/checkinDate/{checkinDate}")
    @PreAuthorize("hasAnyRole('FRONT-DESK','CONCIERGE' ,'ADMIN')")
    public ResponseEntity<List<ReservationRecord>> getReservationsByPrimaryGuestIdAndCheckinDate(
            @PathVariable Long primaryGuestId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkinDate)
    {
        List<ReservationRecord> reservationRecordList =
                reservationService.getReservationRecordsByPrimaryGuestIdAndCheckinDate(primaryGuestId, checkinDate);
        return ResponseEntity.ok(reservationRecordList);
    }

    @GetMapping("/primaryGuest/lastName/{lastName}/checkinDate/{checkinDate}")
    @PreAuthorize("hasAnyRole('FRONT-DESK','CONCIERGE' ,'ADMIN')")
    public ResponseEntity<List<ReservationRecord>> getReservationsByPrimaryGuestLastNameAndCheckinDate(
            @PathVariable String lastName,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkinDate)
    {
        List<ReservationRecord> reservationRecordList =
                reservationService.getReservationRecordsByPrimaryGuestLastNameAndCheckinDate(lastName, checkinDate);
        return ResponseEntity.ok(reservationRecordList);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('FRONT-DESK','CONCIERGE' ,'ADMIN', 'GUEST')")
    public ResponseEntity<ReservationRecord> createNewReservation(@RequestBody ReservationRecord reservationRecord)
    {
        ReservationRecord savedRecord = reservationService.createReservation(reservationRecord);
        return ResponseEntity.ok(savedRecord);
    }

    @PutMapping("/checkin")
    @PreAuthorize("hasAnyRole('FRONT-DESK','CONCIERGE' ,'ADMIN', 'GUEST')")
    public ResponseEntity<ReservationRecord> reservationCheckIn(@RequestBody Long reservationId)
    {
        ReservationRecord savedRecord = reservationService.reservationCheckIn(reservationId);
        return ResponseEntity.ok(savedRecord);
    }

    @PutMapping("/checkout")
    @PreAuthorize("hasAnyRole('FRONT-DESK','CONCIERGE' ,'ADMIN', 'GUEST')")
    public ResponseEntity<ReservationRecord> reservationCheckOut(@RequestBody Long reservationId)
    {
        ReservationRecord savedRecord = reservationService.reservationCheckOut(reservationId);
        return ResponseEntity.ok(savedRecord);
    }

}
