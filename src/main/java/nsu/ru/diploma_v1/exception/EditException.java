package nsu.ru.diploma_v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EditException extends RuntimeException {
    public EditException() {
    }

    public EditException(String message) {
        super(message);
    }

    public EditException(String message, Throwable cause) {
        super(message, cause);
    }

    public EditException(Throwable cause) {
        super(cause);
    }

    public EditException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
