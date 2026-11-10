package org.tomholmes.opensource.hotel.bff.dto;

import java.time.LocalDateTime;

public record GuestRecord(
Long id,
String firstName,
String lastName,
String guestEmail,
String guestPhone,
Boolean guestAdut,
String stripeId,
Long paymentId,
Long createdBy,
LocalDateTime createdDate,
Long updatedBy,
LocalDateTime updatedDate)
{
}
