package com.minecraft.mcustom.entity;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class AllPlayer implements Serializable {

    private static final long serialVersionUID = 2546L;

    private List player;

    private int code;

    public void setCode() {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setPlayer() {
        this.player = player;
    }

    public List getPlayer() {
        return player;
    }

    public AllPlayer(List player) {
        this.player = player;
    }

    @NonNull
    @Override
    public String toString() {
        return "AllPlayer{" +
                "code=" + code +
                ",player=" + player +
                '}';
    }
}
