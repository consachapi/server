package pe.regioncusco.llankasun.server.exceptions;

public class BadRequestException extends RuntimeException {
    private static final String DESCRIPTION = "Bad Request Exception";

    public BadRequestException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
