package dev.prtjohanson.jdbc20230402;

public class DatabaseActionException extends RuntimeException {
    public DatabaseActionException(String message) {
        super(message);
    }
}
