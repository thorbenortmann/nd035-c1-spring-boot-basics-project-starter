package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;
    private UserMapper userMapper;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, UserMapper userMapper) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.userMapper = userMapper;
    }

    public List<Credential> getCredentialsForUser(String userName) {
        var userId = this.userMapper.getUser(userName).getUserId();
        return this.credentialMapper.getNotesForUser(userId);
    }

    public void addCredential(CredentialForm credentialForm, String userName) {
        var userId = this.userMapper.getUser(userName).getUserId();

        var url = credentialForm.getUrl();
        var userNameForCredential = credentialForm.getUserName();
        var password = credentialForm.getPassword();

        String encodedKey = this.createEncodedKey();
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        this.credentialMapper.insert(new Credential(null, url, userNameForCredential, encodedKey, encryptedPassword, userId));
    }

    public void deleteCredentialById(int credentialId) {
        this.credentialMapper.deleteById(credentialId);
    }

    private String createEncodedKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
