package com.photo.myblog.security;


import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.text.ParseException;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public String generateToken(String username) throws JOSEException {
        // Create JWT Claims (payload)
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .expirationTime(new Date(System.currentTimeMillis() + expirationTime))
                .build();

        // Create a signed JWT using a secret key (HMAC)
        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                claimsSet
        );

        // Create a signer with the secret key
        MACSigner signer = new MACSigner(secretKey);

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    public boolean validateToken(String token){

        try{
            SignedJWT signedJWT = SignedJWT.parse(token);
            MACVerifier verifier = new MACVerifier(secretKey);

            if(signedJWT.verify(verifier)){
                Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();
                if(expirationDate != null && expirationDate.before(new Date())){
                    return false;
                }
            }

        } catch(JOSEException | ParseException e){
            e.printStackTrace();
        }
        return false;

    }

    public String getUsernameFromToken(String token) throws ParseException{
        SignedJWT signedJWT = SignedJWT.parse(token);
        return signedJWT.getJWTClaimsSet().getSubject();
    }


}
