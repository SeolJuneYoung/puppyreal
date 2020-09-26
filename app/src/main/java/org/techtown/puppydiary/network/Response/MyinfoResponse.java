package org.techtown.puppydiary.network.Response;

import java.util.ArrayList;
import java.util.List;

public class MyinfoResponse {

    private int status;
    private boolean success;
    private String message;
    private List<Myinfo> data;

    public int getStatus(){
        return status;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public List<Myinfo> getData(){
        return data;
    }

    public class Myinfo {
        private String image;
        private String puppyname;
        private int age;
        private String birth;
        private int gender;

        public String getImage(){
            return image;
        }

        public String getPuppyname(){ return puppyname; }

        public int getAge(){
            return age;
        }

        public String getBirth(){
            return birth;
        }

        public int getGender(){
            return gender;
        }
    }
}
