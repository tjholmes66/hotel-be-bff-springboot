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
import org.tomholmes.opensource.hotel.bff.dto.RoomRecord;
import org.tomholmes.opensource.hotel.bff.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
@PreAuthorize("hasAnyRole('HOUSEKEEPING', 'MAINTENANCE', 'ADMIN')")
@SecurityRequirement(name = "oauth2")
public class RoomController
{
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomRecord>> getAllRooms()
    {
        List<RoomRecord> roomRecordList = roomService.getAllRoomRecords();
        return ResponseEntity.ok(roomRecordList);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomRecord> getRoomById(@PathVariable Long roomId)
    {
        RoomRecord roomRecord = roomService.getRoomRecordById(roomId);
        return ResponseEntity.ok(roomRecord);
    }

    @PostMapping
    public ResponseEntity<RoomRecord> createNewRoom(@RequestBody RoomRecord roomRecord)
    {
        RoomRecord savedRecord = roomService.createRoom(roomRecord);
        return ResponseEntity.ok(savedRecord);
    }

}
