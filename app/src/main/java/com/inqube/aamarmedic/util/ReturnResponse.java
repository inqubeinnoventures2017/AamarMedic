package com.inqube.aamarmedic.util;

public class ReturnResponse {

    public ReturnResponse(boolean ret_status, String msg, int error_code){
        this.msg=msg;
        this.error_code=error_code;
        this.ret_status=ret_status;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isRet_status() {
        return ret_status;
    }

    public void setRet_status(boolean ret_status) {
        this.ret_status = ret_status;
    }

    private boolean ret_status;
    private String msg;
    private int error_code;
}
