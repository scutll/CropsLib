package com.mxl.CropsLib.Response;


public class Response<T> {
    private T data;

    boolean success;

    private String errorMsg;

    public static <K> Response<K> newSuccess(K data){
        Response<K> response = new Response();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static Response<Void> newFail(String errorMsg){
        Response<Void> response = new Response();
        response.setSuccess(false);
        response.setErrorMsg(errorMsg);
        return response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}