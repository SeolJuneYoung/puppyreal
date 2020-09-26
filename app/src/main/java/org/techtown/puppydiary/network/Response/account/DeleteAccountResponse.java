package org.techtown.puppydiary.network.Response.account;

import com.google.gson.JsonObject;

public class DeleteAccountResponse {

    private int status;
    private boolean success;
    private String message;

    public int getStatus(){
        return status;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

}
