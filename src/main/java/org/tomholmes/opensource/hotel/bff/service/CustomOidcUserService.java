package org.tomholmes.opensource.hotel.bff.service;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.tomholmes.opensource.hotel.bff.model.UserEntity;
import org.tomholmes.opensource.hotel.bff.repository.UserRepository;
import org.tomholmes.opensource.hotel.bff.utils.KeycloakRoleConverter;

import java.time.LocalDateTime;

@Service
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository; // Your Spring Data JPA Repository
    private final KeycloakRoleConverter roleConverter;

    public CustomOidcUserService(UserRepository userRepository,
    KeycloakRoleConverter roleConverter)
    {
        this.userRepository = userRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest)
    {
        // Fetch the standard OidcUser from Keycloak
        OidcUser oidcUser = super.loadUser(userRequest);

        // Map claims to your local DB database
        String keycloakId = oidcUser.getSubject(); // 'sub' claim from JWT
        String email = oidcUser.getEmail();
        String firstName = oidcUser.getGivenName();
        String lastName = oidcUser.getFamilyName();
        String phoneNumber = oidcUser.getPhoneNumber();
        String username = oidcUser.getPreferredUsername();

        // Idempotent sync: create if absent, or update if changed
        // Check if the user already exists in the Spring database; if not, create them
        userRepository.findByKeycloakId(keycloakId)
        .ifPresentOrElse(
            existingUser -> {
                // Optional: Update profile information if it changed in Keycloak
                existingUser.setEmail(email);
                existingUser.setFirstName(firstName);
                existingUser.setLastName(lastName);
                existingUser.setCellPhone(phoneNumber);
                existingUser.setUpdatedBy(existingUser.getId());
                existingUser.setUpdatedDate(LocalDateTime.now());
                userRepository.saveAndFlush(existingUser);
            },
            () -> {
                // Create new local user entry synced to Keycloak ID
                UserEntity newUser = new UserEntity();
                newUser.setEnabled(true);
                newUser.setUsername(username);
                newUser.setKeycloakId(keycloakId);
                newUser.setEmail(email);
                newUser.setFirstName(firstName);
                newUser.setLastName(lastName);
                newUser.setCellPhone(phoneNumber);
                newUser.setCreatedBy(1L);
                newUser.setCreatedDate(LocalDateTime.now());
                newUser.setUpdatedBy(1L);
                newUser.setUpdatedDate(LocalDateTime.now());
                userRepository.saveAndFlush(newUser);
            }
        );

        // Map Keycloak realm roles and groups to ROLE_* authorities so that
        // @PreAuthorize / hasRole(...) checks keep working.
        var mappedAuthorities = roleConverter.convert(userRequest.getIdToken());

        return new DefaultOidcUser(
                mappedAuthorities,
                oidcUser.getIdToken(),
                oidcUser.getUserInfo(),
                "preferred_username");
    }
}
