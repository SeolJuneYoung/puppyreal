package org.techtown.puppydiary.network.Response;

public class ProfileResponse {

    private int status;
    private boolean success;
    private String message;
    private Profile data;

    public int getStatus(){
        return status;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public Profile getData(){
        return data;
    }

    public class Profile {
        private int useridx;
        private String image;

        public int getUseridx(){
            return useridx;
        }

        public String getImage(){
            return image;
        }
    }

}
