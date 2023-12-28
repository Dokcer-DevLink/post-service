package com.goorm.devlink.postservice.exception;


import com.goorm.devlink.postservice.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;


@Slf4j
@RestControllerAdvice(basePackages = "com.goorm.devlink.postservice.service")
@RequiredArgsConstructor
public class ServiceExceptionAdvice {

    private final MessageUtil messageUtil;

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResult> methodNoSuchElementExceptionHandler(NoSuchElementException exception,
                                                                           HttpServletRequest request){

        return ResponseEntity.badRequest()
                .body(ErrorResult.getInstance(exception.getMessage(),request.getRequestURL().toString()));
    }

}
