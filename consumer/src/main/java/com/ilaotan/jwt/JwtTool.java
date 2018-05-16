package com.ilaotan.jwt;

import java.io.IOException;
import java.security.Key;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sun.misc.BASE64Decoder;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/5/16 13:42
 */
public class JwtTool {


    String privateKeyStr = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAInu8Jd6U7UCoethzYwMwC4X7tRgKaYMH2PliKPdgtoc+1lWiQvsu9uHpOAzqWBWBeu3k342Mm1PGYr91yVdntosJ4Y3Jyn7DGh3Tvznnefu4ZXjTb6rHYBpeKUZ+iXujeMSJYwy/QVefzMHc6leXWGQByymUYWraI4amGNoj0BlAgMBAAECgYApoERXUoFkhRDcej2P4GE3B935lbO7+riWazTbTwQoUsq/4U+mm6Dt1Xe6eZzKB+vtgQ8v7ac2OcLZoVWGX0SK3WmweNNvGkubwclkZWiHEfh6/OnptW3WWxZqXAmy1oAftZPMlQhmyPdhIcWUqINGji3KilolJELxlLleIsMVwQJBAMX0p2q96F89cyYyQvMV2WHkumidPwKon1NTHfrF760XyIf5/w9n+3fGFhSJ3gbaxGVfJ45GVVwAd040L81wab0CQQCyYMUjL9Ase86zZEAyVqhXTZ0L+8VhlTcfxEa1Uu3LYG7pFFqsfNYb3iF22hM60/ArJMmg+pn6Ie+2enq+IVfJAkA/YrSM6FxUyr9pVqS7Y56kyvGpd9hqSIYjzzSFTQYO3dO3PqSeUURjOMlvMCoo9bn3X72xv/GrMPcC0pEP7lPdAkBJ+9UIKRagvKEYyqXNux+LRkey5rQRK0B3zoK9Ri4WwmJ+DdEl5YkwectLblu3dJwSaOmv+QDQPC8ecmJiZZXxAkBqjUUTSeaJ5L84UH/4wySkmwpKy5AXuYvekX9K5BwQx+KhxJ6ajC3YIJanA4UYl/xRsI3O6ngw6DE6PnQX16wj";

    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJ7vCXelO1AqHrYc2MDMAuF+7UYCmmDB9j5Yij3YLaHPtZVokL7Lvbh6TgM6lgVgXrt5N+NjJtTxmK/dclXZ7aLCeGNycp+wxod078553n7uGV402+qx2AaXilGfol7o3jEiWMMv0FXn8zB3OpXl1hkAcsplGFq2iOGphjaI9AZQIDAQAB";

    private String secret;

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private SignatureAlgorithm rsaSA = SignatureAlgorithm.RS256;


    public JwtTool(String secret) {
        this.secret = secret;
    }

    public String createJWT(String id, String issuer, String subject, long ttlSecond) {


        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        if (ttlSecond >= 0) {
            long expMillis = nowMillis + ttlSecond * 1000;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }



    public Claims parseJWT(String jwt) {


        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(jwt).getBody();

        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());

        return claims;
    }


    public String createJWTRSA(String id, String issuer, String subject, long ttlSecond)  {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        RSAPrivateKey privateKey1 = (RSAPrivateKey) RSATool.getPrivateKey(privateKeyStr);

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(rsaSA, privateKey1);

        if (ttlSecond >= 0) {
            long expMillis = nowMillis + ttlSecond * 1000;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    public Claims parseJWTRSA(String jwt) {

        RSAPublicKey publicKey = (RSAPublicKey) RSATool.getPublicKey(publicKeyStr);

        Claims claims = Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(jwt).getBody();

        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());

        return claims;
    }
}
