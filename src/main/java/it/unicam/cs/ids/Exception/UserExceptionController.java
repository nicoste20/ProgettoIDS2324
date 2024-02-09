package it.unicam.cs.ids.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionController {
    @ExceptionHandler(value=UserNotCorrectException.class)
    public ResponseEntity<Object> exception(UserNotCorrectException exception){
        return new ResponseEntity<>("Incorrect User Type", HttpStatus.FORBIDDEN);
    }
}
