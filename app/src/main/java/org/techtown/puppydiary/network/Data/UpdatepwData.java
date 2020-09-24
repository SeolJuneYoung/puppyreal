package org.techtown.puppydiary.network.Data;

public class UpdatepwData {

    private String password;
    private String newpassword;
    private String passwordConfirm;

    public UpdatepwData(String password, String newpassword, String passwordConfirm) {
        this.password = password;
        this.newpassword = newpassword;
        this.passwordConfirm = passwordConfirm;
    }

}
