package org.techtown.puppydiary.network.Data;

public class SignupData {

    private String email;
    private String password;
    private String passwordConfirm;

    public SignupData(String email, String password, String passwordConfirm) {
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

}