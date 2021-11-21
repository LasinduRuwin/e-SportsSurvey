package com.esports.Client.cookie;

/**
 * This class contains the methods required for setting up the cookie
 * @author Lasindu Ruwin
 */
public class AdmnCookie {
    
    public void setCookie(String name){
        Login_cookie.setCookie(name+" is Logged in");   
    }

    public String getCookie(){
        return Login_cookie.getCookie();
    }
    
}
