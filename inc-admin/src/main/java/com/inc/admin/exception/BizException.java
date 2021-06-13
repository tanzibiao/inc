package com.inc.admin.exception;

import java.text.MessageFormat;

public class BizException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    /**
     * 异常信息
     */
    private String errorMsg;
    
    /**
     * 错误码
     */
    private String code;
    
    public String getErrorMsg()
    {
        return errorMsg;
    }
    
    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public BizException(String errorMsg)
    {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }
    
    public BizException(String code, String errorMsg)
    {
        super(errorMsg);
        this.code = code;
        this.errorMsg = errorMsg;
    }
    
    public BizException(IErrorCode errorCode)
    {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.errorMsg = errorCode.getMessage();
    }
    
    public BizException(IErrorCode errorCode, Object... param)
    {
        super(MessageFormat.format(errorCode.getMessage(), param));
        this.code = errorCode.getCode();
        this.errorMsg = super.getMessage();
    }
}
