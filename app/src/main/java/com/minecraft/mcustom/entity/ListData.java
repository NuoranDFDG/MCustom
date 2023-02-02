package com.minecraft.mcustom.entity;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListData implements Serializable {

    private static final long serialVersionUID = 2576L;

    private ArrayList<List> data;

    public ArrayList<List> getData() {
        return data;
    }

    public void setData(ArrayList<List> data) {
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
