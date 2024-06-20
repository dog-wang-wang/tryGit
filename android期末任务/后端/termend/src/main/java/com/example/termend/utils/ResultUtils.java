package com.example.termend.utils;

import com.example.termend.entity.Result;

public class ResultUtils {
    public static final Integer RESULT_SUCCESS = 200;
    public static final Integer RESULT_ERROR = 400;
    public static<T> Result success(Integer code,String message,T data){
        Result result = new Result();
        result.setStatusCode(RESULT_SUCCESS);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    public static<T> Result success(String message,T data){
        return success(RESULT_SUCCESS,message,data);
    }
    public static Result error(Integer code,String message){
        Result result =new Result();
        result.setStatusCode(RESULT_ERROR);
        result.setMessage(message);
        return result;
    }
    public static Result error(String message){
        return error(RESULT_ERROR,message);
    }
}
