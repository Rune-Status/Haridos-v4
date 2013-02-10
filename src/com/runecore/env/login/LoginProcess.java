package com.runecore.env.login;

/**
 * LoginProcess.java
 * @author Harry Andreas<harry@runecore.org>
 * Feb 10, 2013
 */
public class LoginProcess implements Runnable {

    private LoginRequest request;
    
    public LoginProcess(LoginRequest request) {
	this.request = request;
    }
    
    @Override
    public void run() {
	System.out.println("Request: "+request.getUser());
    }

}