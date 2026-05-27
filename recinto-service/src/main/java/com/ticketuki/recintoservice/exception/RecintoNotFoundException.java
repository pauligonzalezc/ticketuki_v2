package com.ticketuki.recintoservice.exception;

public class RecintoNotFoundException extends RuntimeException {
    public RecintoNotFoundException(String message) {
        super(message);
    }
}
