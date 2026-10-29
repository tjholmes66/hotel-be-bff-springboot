package org.tomholmes.opensource.hotel.bff.dto;

import java.time.LocalDateTime;

public record ReservationStatusRecord(
Long id,
String statusCode,
String statusName,
String statusDescription,
Long createdBy,
LocalDateTime createdDate,
Long updatedBy,
LocalDateTime updatedDate)
{
}
