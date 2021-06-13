package com.inc.admin.utils;

import com.inc.admin.exception.IErrorCode;
import com.inc.admin.exception.SysCode;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", SysCode.S0.getCode());
        put("msg", SysCode.S0.getMessage());
    }

    public static R error() {
        return error(SysCode.E500);
    }

    public static R operate(boolean b){
        if(b){
            return R.ok();
        }
        return R.error();
    }

    public static R error(String msg) {
        return error(SysCode.E500);
    }

    public static R error(IErrorCode errorCode) {
        R r = new R();
        r.put("code", errorCode.getCode());
        r.put("msg", errorCode.getMessage());
        return r;
    }

    public static R error(String code ,String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }
    public static R ok(Object obj) {
        R r = new R();
        r.put("data", obj);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public static R error401() {
        return error(SysCode.E401);
    }

    public static R error403() {
        return error(SysCode.E403);
    }

    public static R data(Object data){
        return R.ok().put("data",data);
    }

    public static R page(Object page){
        return R.ok().put("page",page);
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
