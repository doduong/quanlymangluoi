package com.example.mac.appproject_moneymanager.model.request;

public class UpdateUserPwd {

    private String UserId;
    private String OldPwd;
    private String NewPwd;

    public UpdateUserPwd(String userId, String oldPwd, String newPwd) {
        UserId = userId;
        OldPwd = oldPwd;
        NewPwd = newPwd;
    }

    public UpdateUserPwd() {
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getOldPwd() {
        return OldPwd;
    }

    public void setOldPwd(String oldPwd) {
        OldPwd = oldPwd;
    }

    public String getNewPwd() {
        return NewPwd;
    }

    public void setNewPwd(String newPwd) {
        NewPwd = newPwd;
    }
}

