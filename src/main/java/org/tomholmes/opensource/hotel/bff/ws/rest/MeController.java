package org.tomholmes.opensource.hotel.bff.ws.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tomholmes.opensource.hotel.bff.dto.UserInfoRecord;

import java.util.List;

@RestController
@RequestMapping("/api/v1/me")
public class MeController
{
    @GetMapping
    public ResponseEntity<UserInfoRecord> getCurrentUser(Authentication authentication)
    {
        if (authentication == null || !(authentication.getPrincipal() instanceof OidcUser oidcUser))
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        UserInfoRecord userInfo = new UserInfoRecord(
                oidcUser.getPreferredUsername(),
                oidcUser.getEmail(),
                oidcUser.getGivenName(),
                oidcUser.getFamilyName(),
                oidcUser.getSubject(),
                roles);

        return ResponseEntity.ok(userInfo);
    }
}
