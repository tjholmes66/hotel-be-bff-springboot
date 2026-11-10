package org.tomholmes.opensource.hotel.bff.dto;

import java.math.BigDecimal;

public record RateRecord(
Long id,
Long roomId,
Long seasonId,
BigDecimal amount)
{
}
