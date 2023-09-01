package ua.project.deedee.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class IllegalTypeException extends RuntimeException {

    public IllegalTypeException(String message) {
        super(message);
    }

}