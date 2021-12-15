package Exceptions;

public class DatabaseException extends Exception{
    public DatabaseException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        return "DatabaseException: " + message;

    }
}
