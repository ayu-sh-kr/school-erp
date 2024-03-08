package dev.archimedes.global.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {
    // JwtEncoder is a bean provided by spring security for encoding JWT tokens
    private final JwtEncoder jwtEncoder;


    // This method generates a JWT token for the user by using the JwtEncoder bean and the authentication object
    public String generateToken(Authentication authentication) {
        Instant instant = Instant.now();
        String scope = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(instant)
                .expiresAt(instant.plus(1, ChronoUnit.HOURS))
                .claim("scope", scope)
                .subject(authentication.getName())
                .build();

        var encodeValue = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(), jwtClaimsSet);
        return jwtEncoder.encode(encodeValue).getTokenValue();

    }
}
