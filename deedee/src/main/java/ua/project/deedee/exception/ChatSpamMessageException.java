package ua.project.deedee.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
@Getter
public class ChatSpamMessageException extends RuntimeException {

    public ChatSpamMessageException(String message) {
        super(message);
    }
}
