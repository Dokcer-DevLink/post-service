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
@RestControllerAdvice(basePackages = "com.goorm.devlink.postservice.service")
@RequiredArgsConstructor
public class ServiceExceptionAdvice {

    private final MessageUtil messageUtil;

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResult> methodNoSuchElementExceptionHandler(NoSuchElementException exception,
                                                                           HttpServletRequest request){

        return new ResponseEntity<>(ErrorResult.getInstance(exception.getMessage(),request.getRequestURL().toString()),
                HttpStatus.BAD_REQUEST);
    }

}
