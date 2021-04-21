package nsu.ru.diploma_v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TemplateException extends RuntimeException {

    public TemplateException(String message) { super(message); }
}
