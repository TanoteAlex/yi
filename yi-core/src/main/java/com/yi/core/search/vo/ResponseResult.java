package com.yi.core.search.vo;


import com.yi.core.search.vo.meta.Result;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="接口对象",description="接口对象 ResponseResult")
public class ResponseResult<T> {

    @ApiModelProperty(value="结果",name="result",example="SUCCESS")
    private Result result;
    @ApiModelProperty(value="错误信息",name="message",example="参数错误")
    private String message;
    @ApiModelProperty(value="数据",name="data",example="返回对象")
    private T data;

    public ResponseResult(Result result) {
        this.result = result;
    }

    public ResponseResult(Result result, String message) {
        this.result = result;
        this.message = message;
    }

    public ResponseResult(Result result, T data) {
        this.result = result;
        this.data = data;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
