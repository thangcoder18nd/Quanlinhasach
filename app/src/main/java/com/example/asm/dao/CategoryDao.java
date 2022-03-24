package com.example.asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import com.example.asm.DatabaseHelper;
import com.example.asm.model.Category;
import java.util.ArrayList;

public class CategoryDao {
    private SQLiteDatabase db;
    private DatabaseHelper dbhelper;
    Context mcontext;
    public static final String table_category = "create table category(matheloai char(5) primary key," +
            "tentheloai nvarchar(50), mota nvarchar(255) , vitri integer)";
    public CategoryDao(Context context) {
        dbhelper = new DatabaseHelper(context);
        db = dbhelper.getWritableDatabase();
        mcontext = context;
    }

    public CategoryDao() {
    }

    //insert
    public void themCategory(Category category){
        ContentValues values = new ContentValues();
        values.put("matheloai",category.getMatheloai());
        values.put("tentheloai",category.getTentheloai());
        values.put("mota",category.getMota());
        values.put("vitri",category.getVitri());
        long kq = db.insert("category",null,values);
        if (kq > 0){
            Toast.makeText(mcontext,"Thêm thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mcontext,"Thêm thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    //update
    public void suaCategory(Category category){
        ContentValues values = new ContentValues();
        values.put("tentheloai",category.getTentheloai());
        values.put("mota",category.getMota());
        values.put("vitri",category.getVitri());
        long kq = db.update("category",values,"matheloai=?",new String[]{category.getMatheloai()});
        if (kq > 0){
            Toast.makeText(mcontext,"Sửa thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mcontext,"Sửa thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    //delete
    public void xoaCategory(Category category){
        ContentValues values = new ContentValues();
        long kq = db.delete("category","matheloai=?",new String[]{category.getMatheloai()});
        if (kq > 0){
            Toast.makeText(mcontext,"Xóa thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(mcontext,"Xóa thất bại",Toast.LENGTH_SHORT).show();
        }
    }
    //danh sách
    public ArrayList<Category> dscategory(){
        ArrayList<Category>categories = new ArrayList<>();
        String getAlll = "select * from category";
        Cursor cursor = db.rawQuery(getAlll,null);
        if (cursor.getCount()>0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String matheloai = cursor.getString(0);
                String tentheloai = cursor.getString(1);
                String mota = cursor.getString(2);
                int vitri = Integer.parseInt(cursor.getString(3));
                Category category = new Category(matheloai,tentheloai,mota,vitri);
                categories.add(category);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return categories;
    }
}
