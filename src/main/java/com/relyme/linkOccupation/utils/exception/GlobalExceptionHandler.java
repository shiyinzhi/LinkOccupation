package com.relyme.linkOccupation.utils.exception;

import com.relyme.linkOccupation.utils.bean.ResultCodeNew;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = SyzException.class)
    @ResponseBody
    public ResultCodeNew bizExceptionHandler(HttpServletRequest req, SyzException e){
        e.printStackTrace();
        return new ResultCodeNew("00",e.getMessage());
    }


    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public ResultCodeNew exceptionHandler(HttpServletRequest req, Exception e){
        e.printStackTrace();
        return new ResultCodeNew("00",e.getMessage());
    }
}
