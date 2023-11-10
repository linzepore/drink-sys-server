package com.drink_sys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Msg<T> {
    private T data;
    private String code;
    private String msg;
    public Msg<T> beSucceed(T data) {
        this.data = data;
        this.msg = "SUCCESS";
        this.code = "1";
        return this;
    }
    public Msg<T> beSucceed(T data, String msg) {
        this.data = data;
        this.msg = msg;
        this.code = "1";
        return this;
    }
    public Msg<T> beFailed(T data) {
        this.data = data;
        this.msg = "FAIL";
        this.code = "0";
        return this;
    }
    public Msg<T> beFailed(T data, String msg) {
        this.data = data;
        this.msg = msg;
        this.code = "0";
        return this;
    }
}
