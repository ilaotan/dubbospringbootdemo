package test.com.ilaotan;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import sun.misc.BASE64Decoder;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/5/16 15:59
 */
public class RSATool {

    public static PrivateKey getPrivateKey(String key) {
        return getPrivateKey(key, "RSA");
    }

    public static PrivateKey getPrivateKey(String key, String algorithm) {
        PrivateKey privateKey = null;
        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(key));
            privateKey = kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not reconstruct the private key, the given algorithm could not be found.");
        } catch (InvalidKeySpecException e) {
            System.out.println("Could not reconstruct the private key");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return privateKey;
    }

    public static PublicKey getPublicKey(String key) {
        return getPublicKey(key, "RSA");
    }

    public static PublicKey getPublicKey(String key, String algorithm) {

        PublicKey publicKey = null;
        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec keySpec = new X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(key));
            publicKey = kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not reconstruct the public key, the given algorithm could not be found.");
        } catch (InvalidKeySpecException e) {
            System.out.println("Could not reconstruct the public key");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return publicKey;
    }

}
