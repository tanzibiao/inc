package com.inc.admin.exception;

public enum SysCode implements IErrorCode
{
    
    /** 交易成功 **/
    S0("0", "操作成功"),
    E500("500", "操作失败"),
    E401("401", "你还没有登录"),
    E402("402", "登录信息过期，请重新登录"),
    E403("403", "你没有访问权限"),

    /** 请求参数{0}有误 **/
    E0001("0001", "请求参数{0}有误"),;
    
    private String code;
    
    private String message;
    
    private SysCode(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
    
    public String getCode()
    {
        return this.code;
    }
    
    public String getMessage()
    {
        return this.message;
    }
}
