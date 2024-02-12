package it.unicam.cs.ids.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PointExceptionController {
    @ExceptionHandler(value = PointAlreadyInException.class)
    public ResponseEntity<Object> exception(PointAlreadyInException exception) {
        return new ResponseEntity<>("Point already in!", HttpStatus.FOUND);
    }

    @ExceptionHandler(value = PointNotExistException.class)
    public ResponseEntity<Object> exception (PointNotExistException exception){
        return new ResponseEntity<>("Point not existing!", HttpStatus.NOT_FOUND);
    }
}
