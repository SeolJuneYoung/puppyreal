package org.techtown.puppydiary.network.Response.account;

import com.google.gson.JsonObject;

public class CheckAccountResponse {
    private int status;
    private boolean success;
    private String message;
    private int data;
    private int idaccount;

    public int getStatus(){
        return status;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public int getData(){ return data; }

    /*public int getIdaccount(){
        idaccount = data.getAsJsonObject().get("idaccount").getAsInt();
        return idaccount; }*/


}
