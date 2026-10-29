package org.tomholmes.opensource.hotel.bff.dto;

import java.time.LocalDateTime;

public record GuestRecord(
Long id,
String firstName,
String lastName,
String guestEmail,
String guestPhone,
Boolean guestAdut,
Long createdBy,
LocalDateTime createdDate,
Long updatedBy,
LocalDateTime updatedDate)
{
}
