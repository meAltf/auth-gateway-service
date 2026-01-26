package com.alataf.session.Exception;

public class ConcurrentLoginException extends RuntimeException{

    public ConcurrentLoginException(String message){
        super(message);
    }
}
