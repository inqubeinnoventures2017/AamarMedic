package com.inqube.aamarmedic.util;

/**
 * Created by Tubu on 11/16/2017.
 */

public class AllInterfaces {
    public interface ForgotPassword {
        void onForgotPasswqordProperDataSubmitted(String otp, String newPassword, String user_id);
    }

    public interface ForgotPasswordCallback {
        void sendResponse(String phoneNumber);
    }

    public interface RetrofitResponseToActivityOrFrament<T> {
        void onSuccess(T response, String which_method);
        void onFailure(ReturnResponse response);
        void onResponseFailure();
        void onResponseFailure(String msg);
    }

    public interface IfGPSNotAvailable {
        void ifGPSNotAvailable();
    }

    public interface KeyboardDialogCallback {
        void onOkClick();
        void onCancleClick();
    }

    public interface AdapterCallback{
        void onReturn(String position);
    }

    public interface DialogCallback{
        void onDialogReturn(String position);
    }

    public interface StateCallback{
        void onStateReturn(String position);
    }

    public interface ResultReceiverCallBack<T>{
        public void onSuccess(T data);
        public void onError(Exception exception);
    }


}
