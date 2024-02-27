package it.unicam.cs.ids.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FileExceptionController {

    @ExceptionHandler(value=FileException.class)
    public ResponseEntity<Object> exception(FileException exception){
        return new ResponseEntity<>("Error on file load", HttpStatus.BAD_REQUEST);
    }

}
