package com.example.asm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.adapter.UserAdapter;
import com.example.asm.dao.UserDao;
import com.example.asm.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Activity_User extends AppCompatActivity {
    UserAdapter userAdapter;
    ArrayList<User> listuser = new ArrayList<>();
    RecyclerView rcv;
    TextView tiltle;
    EditText tvtkus;
    ImageView imgtkus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        rcv = findViewById(R.id.rcvuser);
        tiltle = findViewById(R.id.tiltle);
        tvtkus = findViewById(R.id.edtsearchus);
        imgtkus = findViewById(R.id.imgtkus);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        UserDao userDao = new UserDao(this);
        listuser = userDao.dsuser();
        userAdapter = new UserAdapter(listuser);
        rcv.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            tiltle.setText("Xin chào :"+b.getString("acc1"));
        }
        userAdapter.setonitemclicklistener(new UserAdapter.Onitemclicklistener() {
            @Override
            public void onitemclick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_User.this, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle("Bạn chắc chắn xóa dữ liệu?");
                builder.setMessage("Hãy lựa chọn bên dưới để xác nhận");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserDao userDAO = new UserDao(getApplicationContext());
                        userDAO.xoaUser(listuser.remove(position));
                        userAdapter.notifyDataSetChanged();
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
        imgtkus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk = tvtkus.getText().toString();
                userAdapter.getFilter().filter(tk);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Người dùng");
        getMenuInflater().inflate(R.menu.menu1,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.id_themuser:
                opendialogthem(Gravity.CENTER);
                break;
            case R.id.id_signout:
                Intent intent = new Intent(Activity_User.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.id_changepass:
                opendialogsua(Gravity.CENTER);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void opendialogsua(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_changepass);
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
        TextInputEditText ednewpass,edrep,edusn,edghichu;
        Button btnadd2,btncancel2;
        edghichu = dialog.findViewById(R.id.edghichu);
        edusn = dialog.findViewById(R.id.edusn);
        ednewpass = dialog.findViewById(R.id.ednewpass);
        edrep = dialog.findViewById(R.id.edrep);
        btnadd2 = dialog.findViewById(R.id.btnadd_hdct);
        btncancel2 = dialog.findViewById(R.id.btncancel_hdct);
        btnadd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = ednewpass.getText().toString();
                String repass = edrep.getText().toString();
                String username = edusn.getText().toString();
                String ghichu = edghichu.getText().toString();
                if (!password.isEmpty() && !repass.isEmpty()){
                    if (repass.equals(password)==false){
                        Toast.makeText(getApplicationContext(),"Repass không khớp với newpass",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        UserDao userDao = new UserDao(getApplicationContext());
                        User user = userDao.checkUserExist(username);
                        if (user!= null){
                            user.setPassword(password);
                            userDao.suaUser(user);
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"username ko tồn tại",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btncancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edusn.setText("");
                ednewpass.setText("");
                edrep.setText("");
            }
        });
        dialog.show();
    }

    private void opendialogthem(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_adduser);
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
        TextInputEditText edusername,edname,edphone,edpass,edrepass,edghichu;
        Button btnadd,btncancel;
        edname = dialog.findViewById(R.id.edName);
        edghichu = dialog.findViewById(R.id.edghichu);
        edusername = dialog.findViewById(R.id.edUsername);
        edphone = dialog.findViewById(R.id.edPhone);
        edpass = dialog.findViewById(R.id.edPass);
        edrepass = dialog.findViewById(R.id.edRepass);
        btnadd = dialog.findViewById(R.id.btnadd_hdct);
        btncancel = dialog.findViewById(R.id.btncancel_hdct);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edusername.getText().toString();
                String password = edpass.getText().toString();
                String phone = edphone.getText().toString();
                String hoten = edname.getText().toString();
                String repass = edrepass.getText().toString();
                String ghichu = edghichu.getText().toString();
                if (!username.isEmpty() && !password.isEmpty() && !phone.isEmpty() && !repass.isEmpty() && !hoten.isEmpty()){
                    if (repass.equals(password)==false){
                        Toast.makeText(getApplicationContext(),"Repass không khớp với pass",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        User user = new User(username,password,phone,hoten,ghichu);
                        UserDao userDao = new UserDao(Activity_User.this);
                        userDao.themUser(user);
                        listuser.add(user);
                        userAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edname.setText("");
                edusername.setText("");
                edphone.setText("");
                edpass.setText("");
                edrepass.setText("");
            }
        });
        dialog.show();
    }

}