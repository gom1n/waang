package com.waang.waang;

/**
 * 사용자 계정 정보 모델 클래스 (객체를 어떤 식으로 담아 줄 것인지)
*/
public class UserAccount {

    private String idToken; //Firebase Uid (고유 토큰 정보)
    private String idinfo;
    private String pwinfo;

    public UserAccount() {}

    public String getIdToken() { return idToken;}
    public void setIdToken(String idToken) { this.idToken = idToken;}

    public String getIdinfo() { return idinfo;}
    public void setIdinfo(String idinfo) { this.idinfo = idinfo;}
    public String getPwinfo() { return pwinfo;}
    public void setPwinfo(String pwinfo) { this.pwinfo = pwinfo;}


}
