package org.tomholmes.opensource.hotel.bff.ws.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tomholmes.opensource.hotel.bff.dto.GuestRecord;
import org.tomholmes.opensource.hotel.bff.service.GuestService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/guest")
public class GuestController
{
    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public ResponseEntity<List<GuestRecord>> getAllGuests()
    {
        List<GuestRecord> guestRecordList = guestService.getAllGuestRecords();
        return ResponseEntity.ok(guestRecordList);
    }

    @GetMapping("/{guestId}")
    public ResponseEntity<GuestRecord> getGuestById(@PathVariable Long guestId)
    {
        GuestRecord guestRecord = guestService.getGuestRecordById(guestId);
        return ResponseEntity.ok(guestRecord);
    }

    @GetMapping("/search/lastname")
    public ResponseEntity<List<GuestRecord>> getGuestsByLastName(@RequestParam String lastName)
    {
        List<GuestRecord> guestRecordList = guestService.findByLastNameContainingIgnoreCaseRecords(lastName);
        return ResponseEntity.ok(guestRecordList);
    }

    @GetMapping("/search/fullname")
    public ResponseEntity<List<GuestRecord>> getGuestsByLastNameAndFirstName(@RequestParam String lastName, @RequestParam String firstName)
    {
        List<GuestRecord> guestRecordList = guestService.findByLastNameAndFirstNameContainingIgnoreCaseRecords(lastName, firstName);
        return ResponseEntity.ok(guestRecordList);
    }

    @PostMapping
    public ResponseEntity<GuestRecord> createNewGuest(@RequestBody GuestRecord guestRecord)
    {
        GuestRecord savedRecord = guestService.createGuest(guestRecord);
        return ResponseEntity.ok(savedRecord);
    }

}
