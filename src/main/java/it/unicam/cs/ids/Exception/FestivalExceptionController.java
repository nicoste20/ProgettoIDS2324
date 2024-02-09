package it.unicam.cs.ids.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FestivalExceptionController {
    @ExceptionHandler(value=FestivalAlreadyInException.class)
    public ResponseEntity<Object> exception(FestivalAlreadyInException exception){
        return new ResponseEntity<>("Festival already in", HttpStatus.FOUND);
    }

    @ExceptionHandler(value=FestivalNotFoundException.class)
    public ResponseEntity<Object> exception(FestivalNotFoundException exception){
        return new ResponseEntity<>("Festival not found", HttpStatus.NOT_FOUND);
    }
}
