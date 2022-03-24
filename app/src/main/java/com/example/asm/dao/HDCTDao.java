package com.example.asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.asm.DatabaseHelper;
import com.example.asm.model.Bill;
import com.example.asm.model.Book;
import com.example.asm.model.HDCT;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HDCTDao {
    public static final String table_hdct = "create table hdct(mahdct integer primary key ,mahoadon nchar(7)," +
            "masach nchar(5),soluongmua integer,giabia float)";
    private SQLiteDatabase db;
    private DatabaseHelper dbhelper;
    Context mcontext;
    public HDCTDao(Context context) {
        dbhelper = new DatabaseHelper(context);
        db = dbhelper.getWritableDatabase();
        mcontext = context;
    }
    //insert
    public void themHDCT(HDCT hdct){
        ContentValues values = new ContentValues();
        values.put("mahoadon",hdct.getMahoadon());
        values.put("masach",hdct.getMasach());
        values.put("soluongmua",hdct.getSoluongmua());
        values.put("giabia",hdct.getGiabia());
        long kq = db.insert("hdct",null,values);
        if (kq > 0){
            Toast.makeText(mcontext,"Thêm thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mcontext,"Thêm thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    //delete
    public void xoaHDCT(HDCT hdct){
        long kq = db.delete("hdct","mahdct=?",new String[]{String.valueOf(hdct.getMahdct())});
        if (kq > 0){
            Toast.makeText(mcontext,"Xóa thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mcontext,"Xóa thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    //danh sách theo id
    public ArrayList<HDCT> dshdct_mahd(String mahoadon){
        ArrayList<HDCT>hdct_ids = new ArrayList<>();
        String getAll = "SELECT * from hdct where hdct.mahoadon = '"+mahoadon+"'";
        Cursor cursor = db.rawQuery(getAll,null);
        Log.e("hdct",cursor.getCount()+"");
        cursor.moveToFirst();
        try {
            while (!cursor.isAfterLast()){
                int mahdct = cursor.getInt(0);
                String masach = cursor.getString(2);
                int soluongmua = cursor.getInt(3);
                float giabia = cursor.getFloat(4);
                HDCT hdct = new HDCT(mahdct,mahoadon,masach,soluongmua,giabia);
                hdct_ids.add(hdct);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception ex){
            Log.d("HDCT",ex.toString());
        }
        return hdct_ids;
    }
    public double getDoanhThunam(){
        double doanhThu = 0;
        String sSQL ="SELECT SUM(tongtien) from (SELECT SUM(book.giabia * hdct.soluongmua) as 'tongtien'  \n" +
                "                FROM bill INNER JOIN hdct on bill.mahoadon = hdct.mahoadon \n" +
                "                INNER JOIN book on hdct.masach = book.masach where strftime('%Y',bill.ngaymua) \n" +
                "                 = strftime('%Y','now') GROUP BY hdct.masach)tmp";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }
    public double getDoanhThuTheoNgay(){
        double doanhThu = 0;
        String sSQL ="SELECT SUM(tongtien) from (SELECT SUM(book.giabia * hdct.soluongmua) as 'tongtien' FROM bill INNER JOIN hdct on bill.mahoadon = hdct.mahoadon INNER JOIN book on hdct.masach = book.masach where strftime('m%','now') GROUP BY hdct.masach)tmp";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;

    }
    public double getDoanhThuTheoThang(){
        double doanhThu = 0;
        String sSQL ="SELECT SUM(tongtien) from (SELECT SUM(book.giabia * hdct.soluongmua) as 'tongtien' FROM bill INNER JOIN hdct on bill.mahoadon = hdct.mahoadon INNER JOIN book on hdct.masach = book.masach where strftime('m%',bill.ngaymua)= strftime('m%','now') GROUP BY hdct.masach)tmp";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;

    }
}
