package dev.archimedes.service.security;

import dev.archimedes.service.contract.EncryptionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

@Service
public class HexEncryptionService implements EncryptionService {

    @Value("${encryption.seed}")
    private String SEED;

    @Value("${encryption.salt}")
    private String SALT;

    private TextEncryptor encryptor;

    @PostConstruct
    public void init() {
        this.encryptor = Encryptors.text(SEED, SALT);
    }


    // Encrypt the users' id
    @Override
    public String encrypt(String value) {
        return encryptor.encrypt(value);
    }

    // Decrypt the users' id
    @Override
    public String decrypt(String value) {
        return encryptor.decrypt(value);
    }
}
