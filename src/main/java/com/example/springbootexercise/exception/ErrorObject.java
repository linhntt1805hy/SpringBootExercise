package com.example.springbootexercise.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private long timestamp;
}
