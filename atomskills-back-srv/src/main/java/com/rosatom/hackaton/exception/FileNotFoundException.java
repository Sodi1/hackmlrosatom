package com.rosatom.hackaton.exception;

public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException(String message) {
        super(message);
    }
}
