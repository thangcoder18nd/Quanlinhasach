package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asm.activity.Activity_Bill;
import com.example.asm.activity.Activity_Book;
import com.example.asm.activity.Activity_Category;
import com.example.asm.activity.Activity_Selling;
import com.example.asm.activity.Activity_Stastical;
import com.example.asm.activity.Activity_User;
import com.example.asm.activity.LoginActivity;

public class MainActivity extends AppCompatActivity {
    ImageView img_user,img_category,img_book,img_bill,img_selling,img_stastical;
    String strUserName, strPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this,"Chào mừng đến với menu",Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_main);
        img_user = findViewById(R.id.img_user);
        img_book = findViewById(R.id.img_book);
        img_category = findViewById(R.id.img_category);
        img_bill = findViewById(R.id.img_bill);
        img_selling = findViewById(R.id.img_selling);
        img_stastical = findViewById(R.id.img_statistical);
        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,Activity_User.class);
//                startActivity(intent);
                Intent in = getIntent();
                Bundle b = in.getExtras();
                if (b != null) {
                    Intent intent = new Intent(MainActivity.this,Activity_User.class);
                    Bundle b1 = new Bundle();
                    b1.putString("acc1", b.getString("acc"));
                    Log.e("abc",b.getString("acc"));
                    intent.putExtras(b1);
                    startActivity(intent);
                }
            }
        });
        img_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_Book.class);
                startActivity(intent);
            }
        });
        img_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_Category.class);
                startActivity(intent);
            }
        });
        img_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_Bill.class);
                startActivity(intent);
            }
        });
        img_selling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_Selling.class);
                startActivity(intent);
            }
        });
        img_stastical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_Stastical.class);
                startActivity(intent);
            }
        });
    }
    public int checkLoginShap(){
        SharedPreferences pref = getSharedPreferences("UserFile",MODE_PRIVATE);
        boolean chk = pref.getBoolean("Remember",false);
        if (chk){
            strUserName = pref.getString("Username","");
            strPassword = pref.getString("Password","");
            return 1;
        }
        return -1;
    }
}