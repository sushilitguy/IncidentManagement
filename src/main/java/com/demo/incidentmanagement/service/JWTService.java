package com.demo.incidentmanagement.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class to handle JWT operations
 */
@Service
public class JWTService {
    String secretKey;

    /**
     * Constructor
     */
    public JWTService() {
        KeyGenerator keygen = null;
        try {
            keygen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secret = keygen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(secret.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Extracts User name from JWT token
     * @param token token value
     * @return User name, in our case email
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts Claim
     * @param token token value
     * @param claimResolver claimResolver Function
     * @return claim data
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     * Extracts all calims
     * @param token token value
     * @return Claims object
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Returns Secret key
     * @return secret key
     */
    private SecretKey getKey() {
        byte [] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Validates token against user details
     * @param token token value
     * @param userDetails UserDetails object
     * @return whether token is valid or not
     */
    public boolean validateToken(String token, UserDetails userDetails) {
            final String userName = extractUserName(token);
            return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Returns whether token is expired
     * @param token token value
     * @return whether token is expired
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts Expiration date and time
     * @param token token vale
     * @return Date object
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generates new token for a user
     * @param userName user name, in our case email
     * @return token value
     */
    public String generateToken(String userName) {
        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims().add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)))
                .and()
                .signWith(getKey())
                .compact();
    }
}
