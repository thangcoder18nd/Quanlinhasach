package com.example.asm.model;

import java.util.Date;

public class Bill {
    private String mahoadon;
    private Date ngaymua;

    public String getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(String mahoadon) {
        this.mahoadon = mahoadon;
    }

    public Date getNgaymua() {
        return ngaymua;
    }

    public void setNgaymua(Date ngaymua) {
        this.ngaymua = ngaymua;
    }

    public Bill(String mahoadon, Date ngaymua) {
        this.mahoadon = mahoadon;
        this.ngaymua = ngaymua;
    }

    public Bill() {
    }
}
