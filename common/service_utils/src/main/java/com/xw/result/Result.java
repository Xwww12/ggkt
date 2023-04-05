package com.xw.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "全局统一返回结果")
public class Result<T> {
    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public static <T> Result<T> build(T body, Integer code, String message) {
        Result<T> result = new Result<T>();
        if (body != null) {
            result.setData(body);
        }
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 操作成功
     * @param data  baseCategory1List
     * @param <T>
     * @return
     */
    public static<T> Result<T> ok(T data){
        return build(data,20000,"成功");
    }

    /**
     * 操作失败
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> fail(T data){
        return build(data, 20001,"失败");
    }

    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public static<T> Result<T> ok(T data, ResultCodeEnum resultCodeEnum) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(result.getMessage());
        return result;
    }

    public static<T> Result<T> fail(T data, ResultCodeEnum resultCodeEnum) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(result.getMessage());
        return result;
    }

    public static<T> Result<T> ok(T data, Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    public static<T> Result<T> fail(T data, Integer code, String msg) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    public static<T> Result<T> ok(){
        return build(null,20000,"成功");
    }

    public static<T> Result<T> fail(){
        return build(null, 20001,"失败");
    }
}
