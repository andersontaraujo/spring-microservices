package com.devaware.profileservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionContollerAdvice {

    @ExceptionHandler
    @ResponseBody
    public ResponseError handlePreconditions(MethodArgumentNotValidException ex, HttpServletRequest request){
        List<String> messages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (!error.getDefaultMessage().equals("")) {
                messages.add(error.getDefaultMessage());
            }
        }
        return ResponseError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.PRECONDITION_FAILED.value())
                .error(HttpStatus.PRECONDITION_FAILED.getReasonPhrase())
                .path(request.getServletPath())
                .messages(messages).build();
    }
}
