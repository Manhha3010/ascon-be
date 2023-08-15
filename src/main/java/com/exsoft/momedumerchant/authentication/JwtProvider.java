package com.exsoft.momedumerchant.authentication;

import com.exsoft.momedumerchant.config.props.JwtProperties;
import com.exsoft.momedumerchant.domain.Staff;
import com.exsoft.momedumerchant.problem.UnauthorizedProblem;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    private static final String ROLE = "role";
    private static final String UID = "uid";
    private static final String DEVICE_ID = "device_id";
    private static final String USER_AGENT = "user_agent";

    private final JwtProperties jwtProperties;

    // Create key by JWT secret

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecretKey()));
    }

    public String generateJwtToken(Staff staff, String userAgent) {
        Date expiryDate = new Date(Instant.now().plus(jwtProperties.getTimeToLive(), ChronoUnit.DAYS).toEpochMilli());

        return Jwts.builder()
                .setSubject((staff.getUsername()))
                .setIssuer(jwtProperties.getIss())
                .setIssuedAt(new Date())
                .claim(ROLE, staff.getStaffRoles().get(0).getRole().getCode())
                .claim(UID, staff.getId())
                .claim(USER_AGENT, userAgent)
                .setExpiration(expiryDate)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public JwtParseFormat getJwtInfo(String token) {
        try {
            Claims body = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
            return JwtParseFormat.builder()
                    .iss(body.getIssuer())
                    .userId(body.get(UID, Long.class))
                    .username(body.getSubject())
                    .build();
        } catch (ExpiredJwtException ex) {
            throw new UnauthorizedProblem("invalid_jwt", "JWT is invalid");
        }
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
