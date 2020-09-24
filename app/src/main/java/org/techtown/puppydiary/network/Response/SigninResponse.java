package org.techtown.puppydiary.network.Response;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class SigninResponse {

    private int status;
    private boolean success;
    private String message;
    private JsonObject data;
    private int userIdx;
    private String jwtToken;


    public int getStatus(){
        return status;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public JsonObject getData(){ return data; }

    public int getUserIdx(){
        userIdx = data.getAsJsonObject().get("userIdx").getAsInt();
        return userIdx;
    }

    public String getJwtToken() {
        jwtToken = data.getAsJsonObject().get("jwtToken").getAsString();
        return jwtToken;
    }

    /*
    public void save(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("USERIDX", userIdx).apply();
    }

    public int load(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt("USERIDX", 0);
    }
     */


}