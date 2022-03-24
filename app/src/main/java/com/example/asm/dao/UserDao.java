package com.example.asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import com.example.asm.DatabaseHelper;
import com.example.asm.model.User;

import java.util.ArrayList;

public class UserDao {
    private SQLiteDatabase db;
    private DatabaseHelper dbhelper;
    Context mcontext;
    public static final String table_user = "create table user(username nvarchar(50) primary key,password nvarchar(50)," +
            "phone nchar(10),hoten nvarchar(50),ghichu text)";

    public UserDao(Context context) {
        dbhelper = new DatabaseHelper(context);
        db = dbhelper.getWritableDatabase();
        mcontext = context;
    }
    //insert
    public void themUser(User user){
        ContentValues values = new ContentValues();
        values.put("username",user.getUsername());
        values.put("password",user.getPassword());
        values.put("phone",user.getPhone());
        values.put("hoten",user.getHoten());
        values.put("ghichu",user.getGhichu());
        long kq = db.insert("user",null,values);
        if (kq > 0){
            Toast.makeText(mcontext,"Thêm thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mcontext,"Thêm thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    //update
    public void suaUser(User user){
        ContentValues values = new ContentValues();
        values.put("password",user.getPassword());
        values.put("phone",user.getPhone());
        values.put("hoten",user.getHoten());
        values.put("ghichu",user.getGhichu());
        long kq = db.update("user",values,"username=?",new String[]{user.getUsername()});
        if (kq > 0){
            Toast.makeText(mcontext,"Sửa thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mcontext,"Sửa thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    public void changepass(User user){
        ContentValues values = new ContentValues();
        values.put("password",user.getPassword());
        long kq = db.update("user",values,"username=?",new String[]{user.getUsername()});
        if (kq > 0){
            Toast.makeText(mcontext,"Sửa thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mcontext,"Sửa thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    public User checkUserExist(String username){
        String query = "select * from user where username = '" + username + "'";
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.getCount()>0){
            cursor.moveToFirst();
            String user_name = cursor.getString(0);
            String password = cursor.getString(1);
            String phone = cursor.getString(2);
            String hoten = cursor.getString(3);
            String ghichu = cursor.getString(4);
            User user = new User(user_name,password,phone,hoten,ghichu);
            cursor.close();
            return user;
        }
        else {
            return null;
        }
    }
    //delete
    public void xoaUser(User user){
        ContentValues values = new ContentValues();
        long kq = db.delete("user","username=?",new String[]{user.getUsername()});
        if (kq > 0){
            Toast.makeText(mcontext,"Xóa thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mcontext,"Xóa thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    //danh sách
    public ArrayList<User> dsuser(){
        ArrayList<User>users = new ArrayList<>();
        String getAll = "select * from user";
        Cursor cursor = db.rawQuery(getAll,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String username = cursor.getString(0);
                String password = cursor.getString(1);
                String phone = cursor.getString(2);
                String hoten = cursor.getString(3);
                String ghichu = cursor.getString(4);
                User user = new User(username,password,phone,hoten,ghichu);
                users.add(user);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return users;
    }

}
