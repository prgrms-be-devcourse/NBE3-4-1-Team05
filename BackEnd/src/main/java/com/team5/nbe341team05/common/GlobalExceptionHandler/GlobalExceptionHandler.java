package com.team5.nbe341team05.common.GlobalExceptionHandler;

import com.team5.nbe341team05.common.exception.ServiceException;
import com.team5.nbe341team05.common.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private ResponseEntity<ResponseMessage<Void>> createNotFoundResponse(String message) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseMessage<>(message, "404", null));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ResponseMessage<Void>> handle(NoHandlerFoundException ex) {
        return createNotFoundResponse("해당 데이터가 존재하지 않습니다.");
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseMessage<Void>> handle(NoSuchElementException ex) {
        return createNotFoundResponse("해당 데이터가 존재하지 않습니다.");

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessage<Void>> handle(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .map(error -> error.getField() + "-" + error.getCode() + "-" + error.getDefaultMessage())
                .sorted(Comparator.comparing(String::toString))
                .collect(Collectors.joining("\n"));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseMessage<>(
                        message
                        , "400",
                        null
                ));
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ResponseMessage<Void>> handle(ServiceException ex) {
        ResponseMessage<Void> rsData = ex.getRsData();
        return ResponseEntity
                .status(rsData.getStatusCode())
                .body(rsData);
    }
}
