package org.tomholmes.opensource.hotel.bff.dto;

import java.time.LocalDateTime;

public record RoomStatusRecord(
Long id,
String roomStatusCode,
String roomStatusName,
String roomStatusDescription,
Long createdBy,
LocalDateTime createdDate,
Long updatedBy,
LocalDateTime updatedDate)
{
}
