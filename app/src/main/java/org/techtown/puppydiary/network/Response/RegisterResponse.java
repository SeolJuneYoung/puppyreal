package org.techtown.puppydiary.network.Response;

public class RegisterResponse {

    private Checkpuppyname puppyname;
    private Checkage age;
    private Checkbirth birth;
    private Checkgender gender;

    private int status;
    private boolean success;
    private String message;

    public int getStatus(){
        return status;
    }

    public boolean getSuccess(){
        return success;
    }

    public String getMessage(){
        return message;
    }

    public Checkpuppyname getPuppyname(){
        return puppyname;
    }

    public Checkage getAge(){
        return age;
    }

    public Checkbirth getBirth(){
        return birth;
    }

    public Checkgender getGender(){
        return gender;
    }

    public class Checkpuppyname {
        private String puppyname;

        public String getPuppyname(){
            return puppyname;
        }
    }

    public class Checkage {
        private String age;

        public String getAge(){
            return age;
        }
    }

    public class Checkbirth {
        private String birth;

        public String getBirth(){
            return birth;
        }
    }

    public class Checkgender {
        private String gender;

        public String getGender(){
            return gender;
        }
    }

}
