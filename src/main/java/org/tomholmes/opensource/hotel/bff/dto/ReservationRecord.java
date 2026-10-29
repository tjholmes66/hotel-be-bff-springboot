package org.tomholmes.opensource.hotel.bff.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationRecord(
Long id,
Long primaryGuestId,
Long roomId,
LocalDate checkinDate,
LocalDate checkoutDate,
Integer numberAdults,
Integer numberChildren,
String reservationNumber,
Long statusId,
LocalDateTime createdDate,
LocalDateTime updatedDate,
LocalDateTime actualCheckin,
LocalDateTime actualCheckout,
Long actualCheckinBy,
Long actualCheckoutBy)
{
}
