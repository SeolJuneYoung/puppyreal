package org.techtown.puppydiary.network.Response;

import java.util.List;

public class EmailResponse {

    private int status;
    private boolean success;
    private String message;
    private String data;

//    private List<Eml> data;

    public int getStatus(){
        return status;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    //public List<Eml> getData(){
//        return data;
//    }

    public String getData() {
        return data;
    }


//    public class Eml {
//        private String email;
//
//        public String getEmail(){
//            return email;
//        }
//    }

}