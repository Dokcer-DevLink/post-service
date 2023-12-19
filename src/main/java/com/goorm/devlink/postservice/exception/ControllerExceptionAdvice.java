package com.goorm.devlink.postservice.exception;

import com.goorm.devlink.postservice.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@RestControllerAdvice(basePackages = "com.goorm.devlink.postservice.controller")
@RequiredArgsConstructor
public class ControllerExceptionAdvice {

    private final MessageUtil messageUtil;

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResult> noSuchElementExceptionHandler(NoSuchElementException exception,
                                                                           HttpServletRequest request){

        return new ResponseEntity<>(ErrorResult.getInstance(exception.getMessage(),request.getRequestURL().toString()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResult> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException exception,
                                                                                        HttpServletRequest request){
        String errorMessage =
                messageUtil.getEnumTypeMisMatchMessage(exception.getName(),exception.getValue().toString());
        return new ResponseEntity<>(ErrorResult.getInstance(errorMessage,request.getRequestURL().toString()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResult> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception,
                                                                                    HttpServletRequest request){
        return new ResponseEntity<>(ErrorResult.getInstance(exception.getMessage(),
                request.getRequestURL().toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> methodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                            HttpServletRequest request){
        return new ResponseEntity<>(ErrorResult.getInstance(getMethodArgumentNotValidMessage(exception),
                request.getRequestURL().toString()), HttpStatus.BAD_REQUEST);
    }

    private List<String> getMethodArgumentNotValidMessage(MethodArgumentNotValidException ex){
        ArrayList<String> errorMessages = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMessages.add("[" + fieldError.getField() +"]는(은) " + fieldError.getDefaultMessage() +
                    " [ 입력된 값 : " + fieldError.getRejectedValue() + " ]");
        }
        return errorMessages;
    }
}
