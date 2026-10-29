package org.tomholmes.opensource.hotel.bff.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class KeycloakRoleConverter implements Converter<OidcIdToken, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(OidcIdToken idToken) {
        List<String> combinedRolesAndGroups = new ArrayList<>();

        // 1. Extract standard Realm Roles
        Map<String, Object> realmAccess = idToken.getClaim("realm_access");
        if (realmAccess != null && !realmAccess.isEmpty()) {
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) realmAccess.get("roles");
            if (roles != null) {
                combinedRolesAndGroups.addAll(roles);
            }
        }

        // 2. Extract Keycloak Groups
        @SuppressWarnings("unchecked")
        List<String> groups = idToken.getClaim("groups");
        if (groups != null) {
            for (String group : groups) {
                // Keycloak often prefixes groups with a slash (e.g., "/USER")
                String cleanGroup = group.startsWith("/") ? group.substring(1) : group;
                combinedRolesAndGroups.add(cleanGroup);
            }
        }

        // 3. Map everything to ROLE_ authorities
        return combinedRolesAndGroups.stream()
                .map(name -> "ROLE_" + name.toUpperCase())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
