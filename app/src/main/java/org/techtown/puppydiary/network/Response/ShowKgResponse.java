package org.techtown.puppydiary.network.Response;

import java.util.List;

public class ShowKgResponse {

    private int status;
    private boolean success;
    private String message;
    private List<ShowKg> data;

    public int getStatus(){
        return status;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public List<ShowKg> getData(){ return data; }

    public class ShowKg {

        private int kgIdx;
        private int userIdx;
        private int year;

        private int month;
        private float kg;

        public int getKgIdx(){
            return kgIdx;
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

        public double getKg(){
            return kg;
        }

    }

}
