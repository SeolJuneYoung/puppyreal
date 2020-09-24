package org.techtown.puppydiary.network.Data;

public class UpdatepwData {

    private String email;
    private String password;
    private String newpassword;
    private String passwordConfirm;

    public UpdatepwData(String email, String password, String newpassword, String passwordConfirm) {
        this.email = email;
        this.password = password;
        this.newpassword = newpassword;
        this.passwordConfirm = passwordConfirm;
    }

}
