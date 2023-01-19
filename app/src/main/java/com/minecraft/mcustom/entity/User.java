package com.minecraft.mcustom.entity;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 7912L;

	private static String Aid;

	private String id;

	private String password;

	public static String getAid() {
		return Aid;
	}

	public static void setAid(String aid) {
		User.Aid = aid;
	}

	public void Data(){};
	
	public void Data(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
