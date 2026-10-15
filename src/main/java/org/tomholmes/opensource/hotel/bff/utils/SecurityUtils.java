package org.tomholmes.opensource.hotel.bff.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import java.util.Optional;

public class SecurityUtils {

    /**
     * Retrieves the current authenticated Keycloak User wrapper.
     */
    public static Optional<OidcUser> getCurrentOidcUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof OidcUser) {
            return Optional.of((OidcUser) authentication.getPrincipal());
        }
        return Optional.empty();
    }

    /**
     * Gets the current unique Keycloak Identifier (sub claim).
     */
    public static Optional<String> getCurrentUserKeycloakId() {
        return getCurrentOidcUser().map(OidcUser::getSubject);
    }

}

