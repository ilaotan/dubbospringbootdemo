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

    // rsa公私 生成 注意 java要用PKCS8  其他平台(IOS等)要用PKCS1

    /*
    # PKCS1私钥生成
    openssl genrsa -out private.pem 1024

    # 生成公钥
    openssl rsa -in private.pem -pubout -out public.pem

    # PKCS1私钥转换为PKCS8(该格式一般Java调用)
    openssl pkcs8 -topk8 -inform PEM -in private.pem -outform pem -nocrypt -out pkcs8.pem


    * */


    private String privateKeyStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALNuM1L2Gk3ff02N0GTR9vfuJfZ3p4" +
            "/dNgeqxqrGGKqdm6QvqVm8cwsGayImwuabpniBsmIOJNYcibThO0QofE08hYlDvHjGmAb6dajoOHLln" +
            "+nHQfbaqqTo7wCReQUtBrfqFyXVIvexAWKxMMDiySuAGOZv13M3cBc9NsPJ0bJ" +
            "/AgMBAAECgYBCLIBNNmpZEfY3OfgDVtRId9IUZeisTB8jEL6YONrcAahnExTX/YJILKPSQo1a00i/5MVPGHxJYtj+lppu9qdwjm" +
            "+NpmJcFh0A6pWp7MDUnunQBG92Lp28jLZ1oUeOOuh2QwRL2nzLOnaYRnLIyS12ZlESlKJGhZA3l09dPf8ecQJBAOKAqeQRBu3OIjw93Rl9mp2R/gTllI7LWYwn6EtjhsvgUqv0HvTk3Pr3TXbsm3fq/8JnDtIVS3P+aV/ebKEXTCsCQQDKzDTT5vxm1QvUn6xuQNCH9bLWTMPt53AGosMI9SbopvHHNTw6dJHPtyR9XRZFlSgUIMpxf1opApTv2WIYsET9AkEAzigRedXNmrDeFDqyfTsHeZehvs8/MeEANkM0eTmzPOmGFaBydK/COmLRDQc6UiGAfG7U3H4pWQAc5PdLpCvSVQJBAJhX7xeAXfuh79E6Yf4xEnYQTNhPsp/1TGKvwnPoNeJwBxDvHN+M0jyICBFk7GY5NnJob6vzT3efSOMlGlwRjsECQEzVhVqPRvShj51JztgeRRwLQbwYZabY3B7+XqnWjTOVIK4/ipJKzzmsAUIBwsjLC9b3GRMsG1nQLYqvjlQvazM=";

    private String publicKeyStr =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCzbjNS9hpN339NjdBk0fb37iX2d6eP3TYHqsaqxhiqnZukL6lZvHMLBmsiJsLmm6Z4gbJiDiTWHIm04TtEKHxNPIWJQ7x4xpgG+nWo6Dhy5Z/px0H22qqk6O8AkXkFLQa36hcl1SL3sQFisTDA4skrgBjmb9dzN3AXPTbDydGyfwIDAQAB";

    private String secret;

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private SignatureAlgorithm rsaSA              = SignatureAlgorithm.RS512;


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


    public String createJWTRSA(String id, String issuer, String subject, long ttlSecond) {

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
