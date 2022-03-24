package com.example.asm.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.dao.HDCTDao;
import com.example.asm.model.HDCT;

public class Activity_Stastical extends AppCompatActivity {
    TextView tvtkengay,tvtkethang,tvtkenam;
    HDCTDao hdctDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Thống kê");
        setContentView(R.layout.activity_stastical);
        tvtkengay = findViewById(R.id.tvtkengay);
        tvtkethang = findViewById(R.id.tvtkethang);
        tvtkenam = findViewById(R.id.tvtkenam);
        hdctDao = new HDCTDao(this);
        tvtkengay.setText("Doanh Thu Ngày :"+hdctDao.getDoanhThuTheoNgay());
        tvtkethang.setText("Doanh Thu Tháng :"+hdctDao.getDoanhThuTheoThang());
        tvtkenam.setText("Doanh Thu Năm :"+hdctDao.getDoanhThunam());
    }
}