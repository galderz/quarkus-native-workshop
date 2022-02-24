package org.acme;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Path("/encrypt-decrypt")
public class EncryptDecryptResource {

    static final KeyPairGenerator KEY_PAIR_GEN;
    static final Cipher CIPHER;

    static {
        try {
            KEY_PAIR_GEN = KeyPairGenerator.getInstance("RSA");
            KEY_PAIR_GEN.initialize(1024);

            CIPHER = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/{message}")
    public String encryptDecrypt(@PathParam String message) throws Exception {
        KeyPair keyPair = KEY_PAIR_GEN.generateKeyPair();

        byte[] text = message.getBytes(StandardCharsets.UTF_8);

        // Encrypt with private key
        CIPHER.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
        byte[] encrypted = CIPHER.doFinal(text);

        // Decrypt with public key
        CIPHER.init(Cipher.DECRYPT_MODE, keyPair.getPublic());
        byte[] unencrypted = CIPHER.doFinal(encrypted);

        return new String(unencrypted, StandardCharsets.UTF_8);
    }
}
