package org.tomholmes.opensource.hotel.bff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.tomholmes.opensource.hotel.bff.service.CustomOidcUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{

    private final CustomOidcUserService customOidcUserService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    public SecurityConfig(CustomOidcUserService customOidcUserService,
    ClientRegistrationRepository clientRegistrationRepository)
    {
        this.customOidcUserService = customOidcUserService;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Configure URL authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Allow public access to landing pages and static assets
                        .requestMatchers("/", "/index.html", "/static/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()

                        // ONLY protect the Controller endpoint.
                        // Do NOT include "/dashboard.html" here if it lives in /templates/
                        .requestMatchers("/dashboard").authenticated()

                        // Require authentication for everything else (APIs, endpoints, etc.)
                        .anyRequest().authenticated()
                )
                // 2. Configure OAuth2 Login
                .oauth2Login(oauth2 -> oauth2
                        // If OAuth2 fails, seamlessly send them back to your index page with a message
                        .failureUrl("/index.html?error=true")
                        // When login succeeds, hit your backend controller route
                        .defaultSuccessUrl("/dashboard", true)
                        // CRITICAL FIX: Bind the OidcUserService bean here
                        .userInfoEndpoint(userInfo -> userInfo
                            .oidcUserService(customOidcUserService)
                        )
                )
                // 3. Configure CSRF protection
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                )
                // 4. Configure OAuth2 Logout
                .logout(logout -> logout
                    .logoutUrl("/logout") // Spring Security's default logout endpoint
                    .logoutSuccessHandler(oidcLogoutSuccessHandler())
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
        new OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository);

        // Where Keycloak should redirect the user AFTER logging out from Keycloak
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/index.html");
        return oidcLogoutSuccessHandler;
    }

}
