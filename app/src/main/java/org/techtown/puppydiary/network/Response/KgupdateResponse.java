package org.techtown.puppydiary.network.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class KgupdateResponse {

    private int status;
    private boolean success;
    private String message;
    private JsonArray data;

    private int kgIdx;
    private int userIdx;
    private int year;

    private String jan;
    private String feb;
    private String mar;
    private String apr;
    private String may;
    private String jun;
    private String jul;
    private String aug;
    private String sep;
    private String oct;
    private String nov;
    private String dec;

    private String month;
    private Double kg;


    public int getStatus(){
        return status;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public JsonArray getData(){ return data; }

    /*public int getKgIdx(){
        kgIdx = data.getAsJsonObject().get("kgIdx").getAsInt();
        return kgIdx;
    }

    public int getUserIdx(){
        userIdx = data.getAsJsonObject().get("userIdx").getAsInt();
        return userIdx;
    }

    public int getYear(){
        year = data.getAsJsonObject().get("year").getAsInt();
        return year;
    }

    public String getJan(){
        jan = data.getAsJsonObject().get("jan").getAsString();
        return jan;
    }

    public String getFeb(){
        feb = data.getAsJsonObject().get("feb").getAsString();
        return feb;
    }

    public String getMar(){
        mar = data.getAsJsonObject().get("mar").getAsString();
        return mar;
    }

    public String getApr(){
        apr = data.getAsJsonObject().get("apr").getAsString();
        return apr;
    }

    public String getMay(){
        may = data.getAsJsonObject().get("may").getAsString();
        return may;
    }

    public String getJun(){
        jun = data.getAsJsonObject().get("jun").getAsString();
        return jun;
    }

    public String getJul(){
        jul = data.getAsJsonObject().get("jul").getAsString();
        return jul;
    }

    public String getAug(){
        aug = data.getAsJsonObject().get("aug").getAsString();
        return aug;
    }

    public String getSep(){
        sep = data.getAsJsonObject().get("sep").getAsString();
        return sep;
    }

    public String getOct(){
        oct = data.getAsJsonObject().get("oct").getAsString();
        return oct;
    }

    public String getNov(){
        nov = data.getAsJsonObject().get("nov").getAsString();
        return nov;
    }

    public String getDec(){
        dec = data.getAsJsonObject().get("dec").getAsString();
        return dec;
    }*/

    public String getMonth(){
        month = data.getAsJsonObject().get("month").getAsString();
        return month;
    }

    public Double getKg(){
        kg = data.getAsJsonObject().get("kg").getAsDouble();
        return kg;
    }


}
