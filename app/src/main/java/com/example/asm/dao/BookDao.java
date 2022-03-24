package com.example.asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.asm.DatabaseHelper;
import com.example.asm.model.Book;

import java.util.ArrayList;

public class BookDao {
    public static final String table_book = "create table book(masach nchar(5) primary key , matheloai nchar(50)" +
            " ,tacgia nvarchar(50),nxb nvarchar(50),giabia float , soluong integer,tensach nvarchar(50))";
    private SQLiteDatabase db;
    private DatabaseHelper dbhelper;
    Context mcontext;    

    public BookDao(Context context) {
        dbhelper = new DatabaseHelper(context);
        db = dbhelper.getWritableDatabase();
        mcontext = context;
    }
    //insert
    public void themBook(Book book){
        ContentValues values = new ContentValues();
        values.put("masach",book.getMasach());
        values.put("matheloai",book.getMatheloai());
        values.put("tacgia",book.getTacgia());
        values.put("nxb",book.getNxb());
        values.put("giabia",book.getGiabia());
        values.put("soluong",book.getSoluong());
        values.put("tensach",book.getTensach());
        long kq = db.insert("book",null,values);
        if (kq > 0){
            Toast.makeText(mcontext,"Thêm thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mcontext,"Thêm thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    //update
    public void suaBook(Book book){
        ContentValues values = new ContentValues();
        values.put("matheloai",book.getMatheloai());
        values.put("tacgia",book.getTacgia());
        values.put("nxb",book.getNxb());
        values.put("giabia",book.getGiabia());
        values.put("soluong",book.getSoluong());
        values.put("tensach",book.getTensach());
        long kq = db.update("book",values,"matheloai=?",new String[]{book.getMatheloai()});
        if (kq > 0){
            Toast.makeText(mcontext,"Sửa thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mcontext,"Sửa thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    public void suaBookhdct(String masach,int soluong){
        ContentValues values = new ContentValues();
        values.put("masach",masach);
        values.put("soluong",soluong);
        Log.e("book",masach);
        long kq = db.update("book",values,"masach=?",new String[]{masach});
        if (kq > 0){
            Toast.makeText(mcontext,"Thanh toán thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mcontext,"Thanh toán thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    //delete
    public void xoaBook(Book book){
        ContentValues values = new ContentValues();
        long kq = db.delete("book","matheloai=?",new String[]{book.getMatheloai()});
        if (kq > 0){
            Toast.makeText(mcontext,"Xóa thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mcontext,"Xóa thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    //danh sách
    public ArrayList<Book> dsbook(){
        ArrayList<Book>books = new ArrayList<>();
        String getAll = "select * from book";
        Cursor cursor = db.rawQuery(getAll,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String masach = cursor.getString(0);
                String matheloai = cursor.getString(1);
                String tacgia = cursor.getString(2);
                String nxb = cursor.getString(3);
                float giabia = cursor.getFloat(4);
                int soluong = cursor.getInt(5);
                String tensach = cursor.getString(6);
                Book book = new Book(masach,matheloai,tacgia,nxb,giabia,soluong,tensach);
                books.add(book);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return books;
    }
    public ArrayList<Book> dstop10(int month){
        ArrayList<Book>books = new ArrayList<>();
        String getAll = "select book.masach,book.matheloai,book.tacgia,book.nxb,book.giabia,book.soluong,book.tensach,hdct.soluongmua,book.matheloai,bill.ngaymua FROM book INNER JOIN hdct on hdct.masach = book.masach INNER JOIN bill on hdct.mahoadon = bill.mahoadon WHERE strftime('"+month+"',bill.ngaymua) GROUP by book.masach ORDER BY soluongmua DESC LIMIT 10";
        Log.e("book",getAll);
        Cursor cursor = db.rawQuery(getAll,null);
        if (cursor.getCount()>0){
            Log.e("book",cursor.getCount()+"");
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String masach = cursor.getString(0);
                String matheloai = cursor.getString(1);
                String tacgia = cursor.getString(2);
                String nxb = cursor.getString(3);
                float giabia = cursor.getFloat(4);
                int soluong = cursor.getInt(5);
                String tensach = cursor.getString(6);
                Book book = new Book(masach,matheloai,tacgia,nxb,giabia,soluong,tensach);
                books.add(book);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return books;
    }
}
