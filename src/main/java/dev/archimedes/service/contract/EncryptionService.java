package dev.archimedes.service.contract;

public interface EncryptionService {

    String encrypt(String text);

    String decrypt(String text);
}
