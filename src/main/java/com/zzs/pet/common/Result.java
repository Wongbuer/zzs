package com.zzs.pet.common;

import java.util.HashMap;

/**
 * @author michael
 */
public class Result extends HashMap<String, Object> {

    private static final String CODE = "code";
    private static final String ATTR_MESSAGE = "message";
    private static final String ATTR_DATA = "data";


    private Result(int errorCode) {
        put(CODE, errorCode);
    }


    public static Result success() {
        return new Result(0);
    }

    public static Result create(boolean success) {
        if (success) {
            return success();
        } else {
            return fail(1);
        }
    }

    public static Result success(Object data) {
        return success(ATTR_DATA, data);
    }

    public static Result success(String key, Object value) {
        Result ret = new Result(0);
        ret.put(key, value);
        return ret;
    }

    public static Result fail() {
        return fail(1);
    }

    public static Result fail(int errorCode) {
        return new Result(errorCode);
    }

    public static Result fail(int errorCode, String errorMessage) {
        Result result = fail(errorCode);
        result.put(ATTR_MESSAGE, errorMessage);
        return result;
    }


    public int failCode() {
        return get(CODE);
    }

    public void failCode(int errorCode) {
        this.put(CODE, errorCode);
    }

    public String failMessage() {
        return this.get(ATTR_MESSAGE);
    }

    public void failMessage(String errorMessage) {
        put(ATTR_MESSAGE, errorMessage);
    }

    public Object data() {
        return get(ATTR_DATA);
    }

    public void data(Object data) {
        put(ATTR_DATA, data);
    }

    public <T> T get(String key) {
        return (T) super.get(key);
    }

    public Result set(String key, Object value) {
        put(key, value);
        return this;
    }

    public Result setIfNotNull(String key, Object value) {
        if (value != null && key != null) {
            put(key, value);
        }
        return this;
    }

    public boolean isSuccess() {
        return failCode() == 0;
    }

}
