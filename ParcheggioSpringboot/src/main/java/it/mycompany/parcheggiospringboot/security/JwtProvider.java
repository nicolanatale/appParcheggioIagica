/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Nicola
 */
public class JwtProvider {
    public static String createJwt(String subject, Map<String,Object> payloadClaims){
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+1);
        Date nowMoreHour = cal.getTime();
        
        JWTCreator.Builder builder = JWT.create().withSubject(subject).withIssuer("Demo JWT").withIssuedAt(now).withExpiresAt(nowMoreHour);
        if(payloadClaims != null && !payloadClaims.isEmpty()){
            for(Map.Entry<String,Object> entry : payloadClaims.entrySet()){
                builder.withClaim(entry.getKey(), entry.getValue().toString());
            }
        } else{
            System.out.println("You are building a JWT without header.");
        }
        return builder.sign(Algorithm.HMAC256(SecurityConfig.secret));
    }
    
    public static DecodedJWT verifyJwt(String jwt){
        return JWT.require(Algorithm.HMAC256(SecurityConfig.secret)).build().verify(jwt);
    }
    
    private JwtProvider() {}
}
