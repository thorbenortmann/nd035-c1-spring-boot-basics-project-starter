package com.udacity.jwdnd.course1.cloudstorage.services;

public class DuplicateFileNameException extends Exception {

    public DuplicateFileNameException(String errorMessage) {
        super(errorMessage);
    }

}
