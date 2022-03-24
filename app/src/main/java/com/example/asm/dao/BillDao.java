package com.example.asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.asm.DatabaseHelper;
import com.example.asm.model.Bill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BillDao {
    public static final String table_bill = "create table bill(mahoadon nvarchar primary key ,ngaymua date)";
    private SQLiteDatabase db;
    private DatabaseHelper dbhelper;
    Context mcontext;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public BillDao(Context context) {
        dbhelper = new DatabaseHelper(context);
        db = dbhelper.getWritableDatabase();
        mcontext = context;
    }
    //insert
    public void themBill(Bill bill){
        ContentValues values = new ContentValues();
        values.put("mahoadon",bill.getMahoadon());
        values.put("ngaymua",sdf.format(bill.getNgaymua()));
        Log.e("bill",bill.getNgaymua().toString());
        Log.e("bill",sdf.format(bill.getNgaymua()));
        long kq = db.insert("bill",null,values);
        if (kq > 0){
            Toast.makeText(mcontext,"Thêm thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mcontext,"Thêm thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    //delete
    public void xoaBill(Bill bill){
        long kq = db.delete("bill","mahoadon=?",new String[]{bill.getMahoadon()});
        if (kq > 0){
            Toast.makeText(mcontext,"Xóa thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mcontext,"Xóa thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    //danh sách
    public ArrayList<Bill> dsbill() throws ParseException{
        ArrayList<Bill>bills = new ArrayList<>();
        String getAll = "select * from bill";
        Cursor cursor = db.rawQuery(getAll,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String mahoadon = cursor.getString(0);
                Date ngaymua = sdf.parse(cursor.getString(1));
                Bill bill = new Bill(mahoadon,ngaymua);
                bills.add(bill);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return bills;
    }
}
