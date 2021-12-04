package com.kbfina.management.responses;

public class LoginResponse {
	
	private String token;

	private String role;

	public String getToken() {
		return token;
	}
	public String getRole() {
		return role;
	}

	public void setToken(String token) {
		this.token = token;
	}
	public void setRole(String role) {
		this.role = role;
	}

}
