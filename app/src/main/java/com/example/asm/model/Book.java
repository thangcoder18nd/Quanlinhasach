package com.example.asm.model;

public class Book {
    private String masach;
    private String matheloai;
    private String tacgia;
    private String nxb;
    private float giabia;
    private int soluong;
    private String tensach;

    public Book(String masach, String matheloai, String tacgia, String nxb, float giabia, int soluong,String tensach) {
        this.masach = masach;
        this.matheloai = matheloai;
        this.tacgia = tacgia;
        this.nxb = nxb;
        this.giabia = giabia;
        this.soluong = soluong;
        this.tensach = tensach;
    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public String getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(String matheloai) {
        this.matheloai = matheloai;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getNxb() {
        return nxb;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }

    public float getGiabia() {
        return giabia;
    }

    public void setGiabia(float giabia) {
        this.giabia = giabia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    @Override
    public String toString() {
        return masach +"|" +tensach;
    }

    public Book() {
    }
}
