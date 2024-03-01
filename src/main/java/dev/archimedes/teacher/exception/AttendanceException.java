package dev.archimedes.teacher.exception;

public class AttendanceException extends RuntimeException{
    public AttendanceException() {
        super();
    }

    public AttendanceException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }
}
