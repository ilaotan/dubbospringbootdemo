package test.com.ilaotan;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Test;
import sun.misc.BASE64Decoder;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/5/16 15:34
 */
public class Jwt2Test extends Base{

    String privateKeyStr="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJHc1bBKiiMsPBBjXNG7j55k2ZEZWpkwBH/MyTgKy/WPes7gi+estW2j9yItR7CyYaEzxF5MW1eYGGOdkyw9lyf6+8LGNohZsgTtP+tDapzegUOsmUvTAUCxMl6xnUbY11vNOlke+RvIt9USEFNbh5TYBXG5hZiUJmH/ee0QuVkxAgMBAAECgYBmyGuX116x3EjS74GHlRhl8ADT2z7ZNVnmSIL+R7Vja2CJiPdtIGmUQfcykR+0Zm6fbmAQZtPyzI1bHL2Jbjkla8y8jJhp832ws/xXZ0wSuucB+9fNoSslOOoNmnJjPg8kat19v/E5Wo8WI+1eeUAkTA3TwcH0c0SUuiqkaRglxQJBAP0Oa1J0XhOrxl/RP+dGXO3YCiy/ptmSEmFWanJsQ/HAlffugISQtXii3GoP2llWqVUto9GK0w9pFxiFlczgbaMCQQCTjzOZ5EmMybb+7utgjpr6xKMrK5hzs23ceMCB6XQHsAXrov8BZh03+3e/9SadDcWQvsKgrSVmzKAsFvp5taMbAkBACwgqQ/0TKWP5F/H+TBZ0szpP/OjzIz63l7E06CWB52WM67Vm12C1+TGjPgPanE4amoRdIa8stUH9GMHCqgHVAkBTmGUbYgFpCT3q31/pq+CRAKWP53rO0XAD30TwVFmvoUE5ZXYiTL9w373PugrOHuc2QVvxuyXdPz/euoXcPdLRAkAbW4vvuMwhqwi2cBqtgYz/FtAoSz68E/Octk1JAhz1vWIVOfIew3Bv93DlS3zKMocKCECFQOZq/dVNdBzDSVZk";
    String publicKeyStr="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCR3NWwSoojLDwQY1zRu4+eZNmRGVqZMAR/zMk4Csv1j3rO4IvnrLVto/ciLUewsmGhM8ReTFtXmBhjnZMsPZcn+vvCxjaIWbIE7T/rQ2qc3oFDrJlL0wFAsTJesZ1G2NdbzTpZHvkbyLfVEhBTW4eU2AVxuYWYlCZh/3ntELlZMQIDAQAB";



    @Test
    public void create1(){

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer("auth0")
                    .sign(algorithm);
        } catch (UnsupportedEncodingException exception){
            //UTF-8 encoding not supported
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        }
    }

    @Test
    public void create2()  {

        try {

            RSAPrivateKey privateKey1 = (RSAPrivateKey) RSATool.getPrivateKey(privateKeyStr);


            Algorithm algorithm = Algorithm.RSA256(null, privateKey1);
            String token = JWT.create()
                    .withIssuer("15628986214")
                    .withJWTId(UUID.randomUUID().toString())
                    .withNotBefore(new Date())
                    .withExpiresAt(new Date())
                    .sign(algorithm);

            System.out.println(token);
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        }
    }

    @Test
    public void verify(){

        String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJuYmYiOjE1MjY0NTkzMDcsImlzcyI6IjE1NjI4OTg2MjE0IiwiZXhwIjoxNTI2NDU5MzA3LCJqdGkiOiI5YTM3ZmM4Ny01NjY0LTRjZGUtOTkyMC1lY2JjYWVmNzVjYWYifQ.bMpFU-sMfXlnWnQrUcUPPW3zrdEHj_OMsy3XaYUKPRKQ7dSHKS5C2l-JzFA6KFS6CryhfWY_dyjS7FMuOlkRs3HLQhN5qNk6jkv2Q6bhu2HsiBeXZ635_iduj2J5TKkXu3HgciVJpJJMfr5RkE_gkEaS_F-dxtO9HXRmvg7TdlE";



        RSAPublicKey publicKey = (RSAPublicKey) RSATool.getPublicKey(publicKeyStr);

        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT jwt = verifier.verify(token);

            System.out.println(jwt.getId());
            System.out.println(jwt.getIssuer());
            System.out.println(jwt.getSubject());
            System.out.println(jwt.getExpiresAt());
            System.out.println(jwt.getNotBefore());

        }catch (TokenExpiredException e2){
            System.out.println("token过期了 需要告诉客户端重启get一次token");

        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            exception.printStackTrace();
        }

    }

}
