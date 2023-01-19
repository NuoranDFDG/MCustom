package com.minecraft.mcustom.entity;

public class Result {
    private int code;
    private String msg;
    private Object result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public void setSuccess(String msg, Object result){
        this.code=200;
        this.msg="success-"+msg;
        this.result=result;
    }
    public void setWarn(String msg, Object result){
        this.code=400;
        this.msg="warning-"+msg;
        this.result=result;
    }
    public void setErr(){
        this.code=500;
        this.msg="error-服务器在忙，请稍后再试一试吧！";
        this.result=null;
    }
}
