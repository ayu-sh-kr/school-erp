package dev.archimedes.service.security;

import dev.archimedes.service.contract.EncryptionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;

@Service
public class Base64EncryptionService implements EncryptionService {

    @Value("${encryption.seed}")
    private String SEED;

    private static final String ALGORITHM = "AES";

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Arrays.copyOf(SEED.getBytes(), 16);
        this.key = new SecretKeySpec(keyBytes, ALGORITHM);
    }

    @Override
    public String encrypt(String text){
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedByteValue = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedByteValue);
        }catch (Exception e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public String decrypt(String text){
        try {
            byte[] decryptedValue64 = Base64.getDecoder().decode(text);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(decryptedValue64), StandardCharsets.UTF_8);
        }catch (Exception e){
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}
