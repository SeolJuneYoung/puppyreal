package org.techtown.puppydiary.network.Response.calendar;

import com.google.gson.JsonArray;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowMonthResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<ShowMonth> data;

    public int getStatus(){
        return status;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public List<ShowMonth> getData(){
        return data;
    }

    public class ShowMonth {
        @SerializedName("date")
        private int date;

        @SerializedName("inject")
        private int inject;

        @SerializedName("water")
        private int water;

        public int getDate(){
            return date;
        }

        public int getInject(){
            return inject;
        }

        public int getWater(){
            return water;
        }
    }
}
