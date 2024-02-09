package it.unicam.cs.ids.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MultimediaExceptionController {
    @ExceptionHandler(value=MultimediaNotFoundException.class)
    public ResponseEntity<Object> exception(MultimediaNotFoundException exception){
        return new ResponseEntity<>("Multimedia not found", HttpStatus.NOT_FOUND);
    }
}
