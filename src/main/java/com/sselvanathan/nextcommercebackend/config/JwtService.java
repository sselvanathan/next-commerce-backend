package com.sselvanathan.nextcommercebackend.config;

import com.sselvanathan.nextcommercebackend.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(
            Map<String, Object> extraClaims,
            User userDetails
    ){
        Date currentDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        String userId = String.valueOf(userDetails.getId());

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userId)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userIdFromToken = extractUserId(token);

        if (userIdFromToken != null && userDetails instanceof User) {
            String userIdFromUserDetails = String.valueOf(((User) userDetails).getId());

            return userIdFromToken.equals(userIdFromUserDetails) && !isTokenExpired(token); // Token is valid
        }

        return false; // Token is invalid
    }




    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        // ToDo in config verschieben
        byte[] keyBytes = Decoders.BASE64.decode("2646294A404E635166546A576E5A7234753778214125442A472D4B6150645367");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
