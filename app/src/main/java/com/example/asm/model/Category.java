package com.example.asm.model;

import android.content.Context;

public class Category {
    private String matheloai;
    private String tentheloai;
    private String mota;
    private int vitri;

    public Category() {
    }

    public String getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(String matheloai) {
        this.matheloai = matheloai;
    }

    public String getTentheloai() {
        return tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getVitri() {
        return vitri;
    }

    public void setVitri(int vitri) {
        this.vitri = vitri;
    }

    public Category(String matheloai, String tentheloai, String mota, int vitri) {
        this.matheloai = matheloai;
        this.tentheloai = tentheloai;
        this.mota = mota;
        this.vitri = vitri;
    }

    @Override
    public String toString() {
        return  matheloai +"-"+ tentheloai ;
    }
}
