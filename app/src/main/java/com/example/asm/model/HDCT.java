package com.example.asm.model;

public class HDCT {
    private int mahdct;
    private String mahoadon;
    private String masach;
    private int soluongmua;
    private float giabia;

    public HDCT() {
    }

    public HDCT(int mahdct, String mahoadon, String masach, int soluongmua, float giabia) {
        this.mahdct = mahdct;
        this.mahoadon = mahoadon;
        this.masach = masach;
        this.soluongmua = soluongmua;
        this.giabia = giabia;
    }

    public int getMahdct() {
        return mahdct;
    }

    public void setMahdct(int mahdct) {
        this.mahdct = mahdct;
    }

    public String getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(String mahoadon) {
        this.mahoadon = mahoadon;
    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public int getSoluongmua() {
        return soluongmua;
    }

    public void setSoluongmua(int soluongmua) {
        this.soluongmua = soluongmua;
    }

    public float getGiabia() {
        return giabia;
    }

    public void setGiabia(float giabia) {
        this.giabia = giabia;
    }
}
