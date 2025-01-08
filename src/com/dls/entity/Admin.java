package com.dls.entity;

import java.io.Serializable;

/**
 * Entity class to hold the data for admin object.
 * 
 * @author Aryan Arora
 *
 */
public class Admin implements Serializable {

	private String username;
	private String password;
	private int id;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Admin() {
	}

	public Admin(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.id = 1;
	}

}
