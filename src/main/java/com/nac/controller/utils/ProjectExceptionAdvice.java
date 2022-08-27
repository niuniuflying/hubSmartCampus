package com.nac.controller.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//作为SpringMVC的异常处理器
//@ControllerAdvice
@RestControllerAdvice
public class ProjectExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public R doException(Exception ex){
        ex.printStackTrace();
        return new  R("Lose ! Please try again !");
    }
}
