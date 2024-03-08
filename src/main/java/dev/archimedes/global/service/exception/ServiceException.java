package dev.archimedes.global.service.exception;

public class ServiceException extends RuntimeException{

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

}
