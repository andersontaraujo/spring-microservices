package com.devaware.userservice.common.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionContollerAdvice {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseError handlePreconditions(MethodArgumentNotValidException ex, HttpServletRequest request){
    	List<String> messages = ex.getBindingResult().getAllErrors().stream()
    			.map(x -> x.getDefaultMessage())
    			.filter(y -> !"".equals(y))
    			.collect(Collectors.toList());
        return ResponseError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.PRECONDITION_FAILED.value())
                .error(HttpStatus.PRECONDITION_FAILED.getReasonPhrase())
                .path(request.getServletPath())
                .messages(messages).build();
    }
    
}
