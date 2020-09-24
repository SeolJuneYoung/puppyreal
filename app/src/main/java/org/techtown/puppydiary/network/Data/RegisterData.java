package org.techtown.puppydiary.network.Data;

public class RegisterData {

    private String puppyname;
    private int age;
    private String birth;
    private int gender;

    public RegisterData(String puppyname, int age, String birth, int gender) {
        this.puppyname = puppyname;
        this.age = age;
        this.birth = birth;
        this.gender = gender;
    }

}
