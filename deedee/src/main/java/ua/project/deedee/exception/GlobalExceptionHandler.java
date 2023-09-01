package ua.project.deedee.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.project.deedee.exception.data.ExceptionData;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // TODO: Exception when Long param at request is not correct

    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(
            final EntityNotFoundException exception){
        log.error("ERROR: {}", exception.getMessage());
        var response = new ExceptionData(exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = { NotEnoughBalanceException.class,
            IllegalTypeException.class, EmptyMessageException.class,
            GiveawayException.class})
    public ResponseEntity<Object> handleBadBalanceException(
            final RuntimeException exception, WebRequest request){
        log.error("ERROR: {}", exception.getMessage());
        var response = new ExceptionData(exception.getMessage(), LocalDateTime.now());
        return handleExceptionInternal(exception, response,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ResponseStatus(value= HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(ChatSpamMessageException.class)
    public ResponseEntity<?> handleSpamRequests(
            final ChatSpamMessageException exception){
        log.error("ERROR: {}", exception.getMessage());
        var response = new ExceptionData(exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
    }

}
