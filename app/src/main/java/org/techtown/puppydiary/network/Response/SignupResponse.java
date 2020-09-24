package org.techtown.puppydiary.network.Response;

import com.google.gson.annotations.SerializedName;

public class SignupResponse {
    @SerializedName("code")
    private int code;
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage() {
        return message;
    }
}