package com.inc.admin.exception;

import com.inc.admin.utils.R;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    //通用异常
    @ExceptionHandler(Exception.class)
    R exception(Exception e) {
        log.error(e.getMessage(), e);
        return R.error(SysCode.E500.getCode(), e.getMessage());
    }

    //JWT-token过期异常
    @ExceptionHandler(ExpiredJwtException.class)
    R exception(ExpiredJwtException e) {
        log.error(e.getMessage(), e);
        return R.error(SysCode.E402);
    }

    //业务异常
    @ExceptionHandler(BizException.class)
    R exception(BizException e) {
        log.error(e.getErrorMsg(), e);
        return R.error(e.getCode(), e.getErrorMsg());
    }

    //参数校验异常
    @ExceptionHandler(ConstraintViolationException.class)
    R exception(ConstraintViolationException e) {
        //controller中单个参数校验//单个参数构建请求实体耗费时间/需在注解中message参数中写明
        ConstraintViolationException logicException = (ConstraintViolationException)e;
        Set<ConstraintViolation<?>> violations = logicException.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return R.error(SysCode.E0001.getCode(), message);
    }
    //参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    R exception(MethodArgumentNotValidException e) {
        MethodArgumentNotValidException logicException = (MethodArgumentNotValidException)e;
        FieldError fieldError = logicException.getBindingResult().getFieldError();
//        String message = MessageFormat.format(SysCode.E0001.getMessage(), fieldError.getField()) + "，"
//                + fieldError.getDefaultMessage();
        String message = fieldError.getDefaultMessage();
        return R.error(SysCode.E0001.getCode(), message);
    }
    //参数校验异常
    @ExceptionHandler(BindException.class)
    R exception(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = "";
        if (fieldError == null) {
            List<ObjectError> list = e.getAllErrors();
            log.warn("进入了待调试代码");
        }else{
//            message = MessageFormat.format(SysCode.E0001.getMessage(), fieldError.getField()) + "，"
//                    + fieldError.getDefaultMessage();
            message = fieldError.getDefaultMessage();
            log.error("请求参数：{}，{}", fieldError.getField(), message);
        }
        return R.error(SysCode.E0001.getCode(), message);
    }
}
