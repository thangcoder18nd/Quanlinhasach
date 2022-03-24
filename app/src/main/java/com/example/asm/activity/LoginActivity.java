package com.example.asm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.example.asm.MainActivity;
import com.example.asm.R;
import com.example.asm.dao.UserDao;
import com.example.asm.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText ed_user_login,ed_pass_login;
    Button btnlogin,btncancel;
    CheckBox chk;
    UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_pass_login = findViewById(R.id.ed_pass_login);
        ed_user_login = findViewById(R.id.ed_user_login);
        btncancel = findViewById(R.id.btncancel_hdct);
        btnlogin = findViewById(R.id.btn_login);
        userDao = new UserDao(this);
        chk = findViewById(R.id.chk_save);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_user_login.setText("");
                ed_pass_login.setText("");
            }
        });
    }
    public int isLogin(String u ,String p){
        ArrayList<User>listuser = userDao.dsuser();
        for (int i = 0; i <listuser.size() ; i++) {
            if (strU.equals(listuser.get(i).getUsername())&& strP.equals(listuser.get(i).getPassword())){
                return 1;
            }
        }
        if (u.equals("admin")&& p.equals("admin")){
            return 1;
        }
        else {
            return -1;
        }
    }
    String strU,strP;
    public void checkLogin(View view){
        strU = ed_user_login.getText().toString();
        strP = ed_pass_login.getText().toString();
        if (strU.isEmpty() || strP.isEmpty()){
            Toast.makeText(getApplicationContext(),"username hoặc password không được để trống",Toast.LENGTH_SHORT).show();
        }
        else {
            if (isLogin(strU,strP)>0){
                rememberUser(strU,strP,chk.isChecked());
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                Bundle b = new Bundle();
                b.putString("acc", strU);
                intent.putExtras(b);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(),"username hoặc password không chính xác",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void rememberUser(String u, String p, boolean status){
        SharedPreferences pref = getSharedPreferences("UserFile",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status){
            //xoa tinh trang luu tru truoc do
            edit.clear();
        }else {
            //luu du lieu
            edit.putString("Username",u);
            edit.putString("Password",p);
            edit.putBoolean("Remember",status);
        }
        //luu lai toan bo
        edit.commit();
    }
}