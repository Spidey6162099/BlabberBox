package com.example.demochatroom.Exceptions;

public class FileIsEmptyException extends RuntimeException{
    public FileIsEmptyException(String message){
        super(message);
    }
}
