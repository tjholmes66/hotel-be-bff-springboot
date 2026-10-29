package org.tomholmes.opensource.hotel.bff.dto;

import java.time.LocalDateTime;

public record RoomRecord(
Long id,
Integer floor,
Integer roomNumber,
String roomName,
String roomDescription,
Long roomStatusId,
Integer occupants,
Long bedTypeId,
Long createdBy,
LocalDateTime createdDate,
Long updatedBy,
LocalDateTime udpatedDate)
{
}
