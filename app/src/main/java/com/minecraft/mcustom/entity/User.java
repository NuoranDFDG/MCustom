package com.minecraft.mcustom.entity;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 7912L;

	private String id;

	private String password;

	private String hash;
	
	public User(String id, String password, String hash) {
		this.id = id;
		this.password = password;
		this.hash = hash;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "User{" +
				"userId=" + id +
				", password='" + password + '\'' +
				'}';
	}
}
