package com.inc.admin.exception;

import java.util.function.Supplier;

public interface IErrorCode {

	public String getCode();

	public String getMessage();
	
    default Supplier<BizException> supplier(Object... param)
    {
        return () -> new BizException(this, param);
    }

    default Supplier<BizException> supplier()
    {
        return () -> new BizException(this);
    }
}
