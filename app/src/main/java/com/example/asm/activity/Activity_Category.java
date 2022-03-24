package com.example.asm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.asm.R;
import com.example.asm.adapter.CategoryAdapter;
import com.example.asm.dao.CategoryDao;
import com.example.asm.model.Category;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Activity_Category extends AppCompatActivity {
    CategoryAdapter categoryAdapter;
    ArrayList<Category> listcategory = new ArrayList<>();
    RecyclerView rcv;
    EditText edttimkiem;
    ImageView btnsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        rcv = findViewById(R.id.rcvcate);
        edttimkiem = findViewById(R.id.edttimkiem);
        btnsearch = findViewById(R.id.btnsearch);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        CategoryDao categoryDao = new CategoryDao(this);
        listcategory = categoryDao.dscategory();
        categoryAdapter = new CategoryAdapter(listcategory);
        rcv.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
        categoryAdapter.setDeletelistener(new CategoryAdapter.Onitemclicklistenerr() {
            @Override
            public void onitemclickk(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Category.this, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle("Bạn chắc chắn xóa dữ liệu?");
                builder.setMessage("Hãy lựa chọn bên dưới để xác nhận");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CategoryDao categoryDAO = new CategoryDao(getApplicationContext());
                        categoryDAO.xoaCategory(listcategory.remove(position));
                        categoryAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recreate();
                    }
                });
                builder.show();
            }
        });
        categoryAdapter.setonitemclicklistener(new CategoryAdapter.Onitemclicklistener() {
            @Override
            public void onitemclick(int position) {
                Category category = listcategory.get(position);
                opendialogsua(Gravity.CENTER,category);
            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk = edttimkiem.getText().toString();
                categoryAdapter.getFilter().filter(tk);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Thể loại");
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        opendialogthem(Gravity.CENTER);
        return super.onOptionsItemSelected(item);
    }

    private void opendialogthem(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_addcate);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAtribute = window.getAttributes();
        windowAtribute.gravity = gravity;
        if (Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }
        TextInputEditText edmatheloai,edtentheloai,edmota,edvitri;
        Button btnadd,btncancel;
        btnadd = dialog.findViewById(R.id.btnadd_hdct);
        btncancel = dialog.findViewById(R.id.btncancel_hdct);
        edmatheloai = dialog.findViewById(R.id.edMatheloai);
        edtentheloai = dialog.findViewById(R.id.edTentheloai);
        edmota = dialog.findViewById(R.id.edMota);
        edvitri = dialog.findViewById(R.id.edVitri);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String matheloai = edmatheloai.getText().toString();
                    String tentheloai = edtentheloai.getText().toString();
                    String mota = edmota.getText().toString();
                    int vitri = Integer.parseInt(edvitri.getText().toString());
                    if (!matheloai.isEmpty() && !tentheloai.isEmpty() && !mota.isEmpty()&& !String.valueOf(vitri).isEmpty()){
                        Category category = new Category(matheloai,tentheloai,mota,vitri);
                        CategoryDao categoryDao = new CategoryDao(Activity_Category.this);
                        categoryDao.themCategory(category);
                        listcategory.add(category);
                        categoryAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Lỗi nhập liệu",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edmatheloai.setText("");
                edtentheloai.setText("");
                edmota.setText("");
                edvitri.setText("");
            }
        });
        dialog.show();
    }
    private void opendialogsua(int gravity,Category category) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_addcate);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAtribute = window.getAttributes();
        windowAtribute.gravity = gravity;
        if (Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }
        TextInputEditText edmatheloai,edtentheloai,edmota,edvitri;
        Button btnadd,btncancel;
        btnadd = dialog.findViewById(R.id.btnadd_hdct);
        btncancel = dialog.findViewById(R.id.btncancel_hdct);
        edmatheloai = dialog.findViewById(R.id.edMatheloai);
        edtentheloai = dialog.findViewById(R.id.edTentheloai);
        edmota = dialog.findViewById(R.id.edMota);
        edvitri = dialog.findViewById(R.id.edVitri);
        edmatheloai.setText(category.getMatheloai());
        btnadd.setText("Sửa");
        edmatheloai.setEnabled(false);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String matheloai = edmatheloai.getText().toString();
                    String tentheloai = edtentheloai.getText().toString();
                    String mota = edmota.getText().toString();
                    int vitri = Integer.parseInt(edvitri.getText().toString());
                    if (!matheloai.isEmpty() && !tentheloai.isEmpty() && !mota.isEmpty() && !String.valueOf(vitri).isEmpty()){
                        Category category = new Category(matheloai,tentheloai,mota,vitri);
                        CategoryDao categoryDao = new CategoryDao(Activity_Category.this);
                        categoryDao.suaCategory(category);
                        for (int i = 0; i < listcategory.size(); i++) {
                            if (category.getMatheloai().equals(listcategory.get(i).getMatheloai())){
                                listcategory.set(i,category);
                            }
                        }
                        categoryAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Lỗi nhập liệu",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edmatheloai.setText("");
                edtentheloai.setText("");
                edmota.setText("");
                edvitri.setText("");
            }
        });
        dialog.show();
    }
}