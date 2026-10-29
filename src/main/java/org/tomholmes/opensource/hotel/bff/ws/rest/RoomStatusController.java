package org.tomholmes.opensource.hotel.bff.ws.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tomholmes.opensource.hotel.bff.dto.RoomStatusRecord;
import org.tomholmes.opensource.hotel.bff.service.RoomStatusService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roomStatus")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "oauth2")
public class RoomStatusController
{
    private final RoomStatusService roomStatusService;

    public RoomStatusController(RoomStatusService roomStatusService) {
        this.roomStatusService = roomStatusService;
    }

    @GetMapping
    public ResponseEntity<List<RoomStatusRecord>> getAllRoomStatuses()
    {
        List<RoomStatusRecord> roomStatusRecordList = roomStatusService.getAllRoomStatusRecords();
        return ResponseEntity.ok(roomStatusRecordList);
    }

    @GetMapping("/{roomStatusId}")
    public ResponseEntity<RoomStatusRecord> getRoomStatusById(@PathVariable Long roomStatusId)
    {
        RoomStatusRecord roomStatusRecord = roomStatusService.getRoomStatusRecordById(roomStatusId);
        return ResponseEntity.ok(roomStatusRecord);
    }

    @PostMapping
    public ResponseEntity<RoomStatusRecord> createNewRoomStatus(@RequestBody RoomStatusRecord roomStatusRecord)
    {
        RoomStatusRecord savedRecord = roomStatusService.createRoomStatus(roomStatusRecord);
        return ResponseEntity.ok(savedRecord);
    }

}
