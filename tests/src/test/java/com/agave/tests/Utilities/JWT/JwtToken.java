package com.agave.tests.Utilities.JWT;

import org.springframework.beans.factory.annotation.Autowired;

public class JwtToken {
    
    @Autowired
    private final JwtService jwtService = new JwtService();

    @SuppressWarnings("unused")
    public String generateAccessToken(int id, String email) {

        Profile pr = new Profile();
        pr.setId(Long.valueOf(1));
        pr.setEmail(email);
        
        return jwtService.generateAccessToken(pr);
    }
}
