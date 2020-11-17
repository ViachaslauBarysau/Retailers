package by.itechart.retailers.exceptions;

public class NotUniqueEmailException extends  RuntimeException {
    public NotUniqueEmailException() {
    }

    public NotUniqueEmailException(String message) {
        super(message);
    }
}
