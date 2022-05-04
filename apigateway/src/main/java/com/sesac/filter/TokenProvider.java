package com.sesac.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";

    private static final long JWT_ACCESS_TOKEN_VALIDITY = 60 * 30; // 30 minutes
    private static final long JWT_REFRESH_TOKEN_VALIDITY = 60 * 60 * 24 * 7; // 1 week

    private String secret;
    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret
    ) {
        this.secret = secret;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Map<String, Object> getUserParseInfo(String token) {
        Claims claims = getAllClaimsFromToken(token);
        Map<String, Object> result = new HashMap<>();
        result.put("email", claims.getSubject()); // expeted : getSubject("email"),
        result.put(AUTHORITIES_KEY, claims.get(AUTHORITIES_KEY));

        log.info("권한이 담긴 result  = {}", result);

        return result;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public boolean validateExpiration(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (ExpiredJwtException e) {
            log.info(e.getMessage());
            return false;
        }
    }

}
