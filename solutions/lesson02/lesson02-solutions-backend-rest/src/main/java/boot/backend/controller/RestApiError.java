package boot.backend.controller;

public class RestApiError {
    private final int code;
    private final String message;
    
    public RestApiError(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
