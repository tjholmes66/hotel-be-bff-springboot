package org.tomholmes.opensource.hotel.bff.dto;

import java.time.LocalDate;

public record SeasonRecord(
Long id,
String seasonCode,
String seasonName,
String seasonDescription,
LocalDate startDate,
LocalDate endDate)
{
}
