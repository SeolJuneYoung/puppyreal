package org.techtown.puppydiary.network.Response.calendar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowDayResponse {

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<ShowDay> data;

    public int getStatus(){
        return status;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public List<ShowDay> getData(){
        return data;
    }

    public class ShowDay {
        @SerializedName("idcalendar")
        @Expose
        private int idcalendar;

        @SerializedName("userIdx")
        @Expose
        private int userIdx;

        @SerializedName("year")
        @Expose
        private int year;

        @SerializedName("month")
        @Expose
        private int month;

        @SerializedName("date")
        @Expose
        private int date;

        @SerializedName("memo")
        @Expose
        private String memo;

        @SerializedName("inject")
        @Expose
        private int inject;

        @SerializedName("water")
        @Expose
        private int water;

        @SerializedName("photo")
        @Expose
        private String photo;

        public int getIdcalendar(){
            return idcalendar;
        }

        public int getUserIdx(){
            return userIdx;
        }

        public int getYear(){
            return year;
        }

        public int getMonth(){
            return month;
        }

        public int getDate(){
            return date;
        }

        public String getMemo(){
            return memo;
        }

        public int getInject(){
            return inject;
        }

        public int getWater(){
            return water;
        }

        public String getPhoto(){
            return photo;
        }
    }
}