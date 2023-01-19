package com.minecraft.mcustom.entity;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;

public class Data implements Serializable {

	private static final long serialVersionUID = 2546L;

	private String data;

	public String getData() {
		return data;
	}
	
	public Data(String data) {
		this.data = data;
	}

	@NonNull
	@Override
	public String toString() {
		return "Data{" +
				"data=" + data +
				'}';
	}
}
