package com.fqh.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 海盗狗
 * @version 1.0
 */
//统一结果返回类
@Data
public class ReturnMessage {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    // 私有构造：不能new
    private ReturnMessage() {
    }

//    链式编程

//    成功静态方法
    public static ReturnMessage ok() {
        ReturnMessage r = new ReturnMessage();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }
//    失败静态方法
    public static ReturnMessage error() {
        ReturnMessage r = new ReturnMessage();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public ReturnMessage success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public ReturnMessage message(String message) {
        this.setMessage(message);
        return this;
    }

    public ReturnMessage code(Integer code) {
        this.setCode(code);
        return this;
    }

    public ReturnMessage data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public ReturnMessage data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
