package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;

import org.springframework.stereotype.Service;

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

        var encodedKey = this.encryptionService.createEncodedKey();
        var encryptedPassword = this.encryptionService.encryptValue(password, encodedKey);

        this.credentialMapper.insertCredential(new Credential(null, url, userNameForCredential, encodedKey, encryptedPassword, userId));
    }

    public void updateCredential(CredentialForm credentialForm, String userName) {
        var userId = this.userMapper.getUser(userName).getUserId();

        var credentialId = Integer.parseInt(credentialForm.getCredentialId());
        var url = credentialForm.getUrl();
        var userNameForCredential = credentialForm.getUserName();
        var password = credentialForm.getPassword();

        var encodedKey = this.encryptionService.createEncodedKey();
        var encryptedPassword = this.encryptionService.encryptValue(password, encodedKey);

        this.credentialMapper.updateCredential(new Credential(credentialId, url, userNameForCredential, encodedKey, encryptedPassword, userId));
    }

    public void deleteCredentialById(int credentialId) {
        this.credentialMapper.deleteById(credentialId);
    }

}
