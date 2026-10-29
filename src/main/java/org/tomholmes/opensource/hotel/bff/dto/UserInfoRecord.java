package org.tomholmes.opensource.hotel.bff.dto;

import java.util.List;

public record UserInfoRecord(
String username,
String email,
String firstName,
String lastName,
String keycloakId,
List<String> roles)
{
}
