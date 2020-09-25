package org.techtown.puppydiary.network.Response;

import java.util.List;

public class RegisterResponse {

    private int status;
    private boolean success;
    private String message;
    private List<Register> data;

    public int getStatus() {
        return status;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Register> getData(){
        return data;
    }

    public class Register {
        private String image;
        private String puppyname;
        private int age;
        private String birth;
        private int gender;

        public String getImage() {
            return image;
        }

        public String getPuppyname(){
            return puppyname;
        }

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
