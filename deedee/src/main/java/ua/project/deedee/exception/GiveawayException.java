package ua.project.deedee.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
public class GiveawayException extends RuntimeException {

    public GiveawayException(String message) {
        super(message);
    }

}