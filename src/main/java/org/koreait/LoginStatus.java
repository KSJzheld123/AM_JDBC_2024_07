package org.koreait;

public class LoginStatus {

    private int loginNum;
    private String loginId;
    private String loginPw;
    private String loginName;

    LoginStatus(int loginNum, String loginId, String loginPw, String loginName) {
        this.loginNum = loginNum;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.loginName = loginName;
    }

    public int getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(int loginNum) {
        this.loginNum = loginNum;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPw() {
        return loginPw;
    }

    public void setLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

}
