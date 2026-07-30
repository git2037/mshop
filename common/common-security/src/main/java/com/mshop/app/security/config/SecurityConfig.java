package com.mshop.app.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${keycloak.issuer-uri}")
    private String issuerUri;

    private final OAuth2ErrorConfig errorConfig;

    public SecurityConfig(OAuth2ErrorConfig errorConfig) {
        this.errorConfig = errorConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    @SuppressWarnings("java:S4502")
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           Converter<Jwt, AbstractAuthenticationToken> keycloakJwtAuthenticationConverter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req ->
                        req.anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.authenticationEntryPoint(errorConfig.customAuthenticationEntryPoint())
                                .accessDeniedHandler(errorConfig.customAccessDeniedHandler())
                                .jwt(
                                        jwt ->
                                                jwt.jwtAuthenticationConverter(keycloakJwtAuthenticationConverter)
                                )
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }
}
