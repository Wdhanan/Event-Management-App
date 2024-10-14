package com.example.eventmanagementsystem.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    //secret for  the JWTtoken generated from any password website like Generate Random
    public static final  String SECRET ="0ec57ae179cccf3bab54feef39c7b60863421b8ebd662d0b602531c1ab4aa544c95469c72b4ac3c428b40ff4647f22a5eb319121a4f7a5389c43457ae1efac7e124448fd06cc511c2ce42abbdaef850658b66ddf6d6df4e4ae0b5c50956e318252ac123d1eae3fb45085e5bb356344494688d92bcbc4db9b7ed1ff7fe147e5df357606037c5dd1bd6684957369aba10fc229de03acb52d80adb49eac3915947dbef672977bdf0aeb8d003722a65a1638c846251a3a592455b968cf1d213b2c785f64e5d0ea3935d11b293e448c31c4c1672eddf0c38b33b5bf7221fb177a8a0af503d3886e8f9eef0a28f496e45b713e796af0bf3fa75fac604e681e107df108";

    //create token Method
    private String createToken(Map<String, Object> claims, String username){

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
                .signWith(SignatureAlgorithm.HS256, getSignKey()).compact();
    }

    // decrypt the Key
    private Key getSignKey() {
        byte [] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // method to generate Token
    public String generateToken(String userName){
        Map<String, Object> claims = new HashMap<>();

        return  createToken(claims, userName); // call the method to create our Token
    }

    // extract all the Claims from JWt

    private Claims extractAllClaims(String token){
        return  Jwts
                .parser()
                .setSigningKey(getSignKey())
                .parseClaimsJws(token) // verify the jwt with the signature
                .getBody();
    }

    //Extract a particular Claim
    public <T> T extractClaim (String token, Function<Claims, T> claimsResolver){
        final  Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Extract the Expiration
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    //Extract the username
    public String extractUsername(String token){
        return  extractClaim(token, Claims::getSubject);
    }

    //Check the Expiration of Token
    private Boolean isTokenExpired(String token){
        return  extractExpiration(token).before(new Date());
    }

    //validate the Token userDetails from Spring security
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
