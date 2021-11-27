package br.org.mcord.alpr4j.model;

import java.io.Serializable;

public class AuthData implements Serializable {
	private static final long serialVersionUID = 2906353178034578717L;
	
	private String username;
	private String password;
	
	public AuthData(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
