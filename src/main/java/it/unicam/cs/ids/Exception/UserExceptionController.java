package it.unicam.cs.ids.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionController {
    @ExceptionHandler(value= UserBadTypeException.class)
    public ResponseEntity<Object> exception(UserBadTypeException exception){
        return new ResponseEntity<>("Incorrect User Type", HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(value=UserAlreadyInException.class)
    public ResponseEntity<Object> exception(UserAlreadyInException exception){
        return new ResponseEntity<>("User already in", HttpStatus.FOUND);
    }
    @ExceptionHandler(value= UserNotExistException.class)
    public ResponseEntity<Object> exception(UserNotExistException exception){
        return new ResponseEntity<>("User not in", HttpStatus.NOT_FOUND);
    }
}
