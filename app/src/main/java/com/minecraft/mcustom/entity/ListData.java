package com.minecraft.mcustom.entity;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListData implements Serializable {

    private static final long serialVersionUID = 2576L;

    private ArrayList<ArrayList> data;

    public ArrayList<ArrayList> getData() {
        return data;
    }

    public ListData(ArrayList<ArrayList> data) {
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
