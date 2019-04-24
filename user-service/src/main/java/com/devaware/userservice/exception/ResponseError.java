package com.devaware.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseError {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;
    private List<String> messages;
}
