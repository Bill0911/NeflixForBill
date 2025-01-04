package com.example.netflix.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class KeyGenerator
{
    public static void main(String[] args) {
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        String base64Key = Encoders.BASE64.encode(keyBytes);
        System.out.println(base64Key);
    }
}
