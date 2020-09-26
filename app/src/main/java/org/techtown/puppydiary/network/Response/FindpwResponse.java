package org.techtown.puppydiary.network.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class FindpwResponse {

    private int status;
    private boolean success;
    private String message;
    private JsonObject data;
    private String toEmail;
    private String subject;

    public int getStatus(){
        return status;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public JsonObject getData(){
        return data;
    }

    public String getToEmail(){
        toEmail = data.getAsJsonObject().get("toEmail").getAsString();
        return toEmail;
    }

    public String getSubject(){
        subject = data.getAsJsonObject().get("subject").getAsString();
        return subject;
    }


}
