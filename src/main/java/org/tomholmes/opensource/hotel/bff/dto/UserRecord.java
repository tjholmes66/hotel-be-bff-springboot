package org.tomholmes.opensource.hotel.bff.dto;

import java.time.LocalDateTime;

public record UserRecord(
Long id,
boolean enabled,
String username,
String firstName,
String lastName,
Long createdBy,
LocalDateTime createdDate,
Long updatedBy,
LocalDateTime updatedDate,
String email,
String cellPhone,
String keycloakId)
{
}
