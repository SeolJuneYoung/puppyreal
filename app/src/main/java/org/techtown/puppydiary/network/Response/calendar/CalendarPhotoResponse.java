package org.techtown.puppydiary.network.Response.calendar;

import org.techtown.puppydiary.network.Response.ProfileResponse;

import java.util.Calendar;
import java.util.List;

public class CalendarPhotoResponse {

    private int status;
    private boolean success;
    private String message;
    private List<CalendarPhoto> data;

    public int getStatus(){
        return status;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public List<CalendarPhoto> getData(){
        return data;
    }

    public class CalendarPhoto {
        private int useridx;
        private String profile;
        private String photo;

        public int getUseridx(){
            return useridx;
        }

        public String getphoto(){
            return photo;
        }

        public String getprofile(){
            return profile;
        }
    }
}