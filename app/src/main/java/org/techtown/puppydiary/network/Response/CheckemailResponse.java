package org.techtown.puppydiary.network.Response;

public class CheckemailResponse {

    private int status;
    private boolean success;
    private String message;
    private Checkemail data;

    public int getStatus(){
        return status;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public Checkemail getData(){
        return data;
    }

    public class Checkemail {
        private String email;

        public String getEmail(){
            return email;
        }
    }

}
