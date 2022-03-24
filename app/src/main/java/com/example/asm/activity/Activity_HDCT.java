package com.example.asm.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.adapter.HDCTAdapter;
import com.example.asm.dao.BookDao;
import com.example.asm.dao.HDCTDao;
import com.example.asm.model.Bill;
import com.example.asm.model.Book;
import com.example.asm.model.HDCT;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.Date;

public class Activity_HDCT extends AppCompatActivity {
    HDCTAdapter hdctAdapter;
    ArrayList<HDCT> listhdct = new ArrayList<>();
    ArrayList<Book> listbook = new ArrayList<>();
    RecyclerView rcv;
    Spinner spnmasach;
    Button btnthem_hdct,btntt_hdct;
    TextInputEditText edsoluongmua,edtmahd;
    TextView tvgiabia,tvslc,tvthanhtien;
    String masach = "";
    double thanhTienn = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdct);
        btnthem_hdct = findViewById(R.id.btnthem_hdct);
        btntt_hdct = findViewById(R.id.btntt_hdct);
        edsoluongmua = findViewById(R.id.edsoluongmua);
        spnmasach = findViewById(R.id.spnmasach);
        edtmahd = findViewById(R.id.edtmahd);
        tvgiabia = findViewById(R.id.tvgiabia);
        tvgiabia = findViewById(R.id.tvgiabia);
        tvthanhtien = findViewById(R.id.tvthanhtien);
        tvslc = findViewById(R.id.tvslc);
        rcv = findViewById(R.id.rcvhdct);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        HDCTDao hdctDao = new HDCTDao(this);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            listhdct = hdctDao.dshdct_mahd(b.getString("mahoadon"));
        }
        Intent in2 = getIntent();
        Bundle b2 = in2.getExtras();
        if (b2 != null) {
            edtmahd.setText(b.getString("mahoadon2"));
        }
        edtmahd.setEnabled(false);
        hdctAdapter = new HDCTAdapter(listhdct);
        rcv.setAdapter(hdctAdapter);
        getmasach();
        hdctAdapter.setDeletelistener(new HDCTAdapter.Onitemclicklistenerr() {
            @Override
            public void onitemclickk(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_HDCT.this, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle("Bạn chắc chắn xóa dữ liệu?");
                builder.setMessage("Hãy lựa chọn bên dưới để xác nhận");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HDCTDao hdctDao = new HDCTDao(getApplicationContext());
                        hdctDao.xoaHDCT(listhdct.get(position));
                        hdctAdapter.notifyDataSetChanged();
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
        spnmasach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Book book = listbook.get(spnmasach.getSelectedItemPosition());
                tvslc.setText(String.valueOf(book.getSoluong()));
                tvgiabia.setText(String.valueOf(book.getGiabia()));
                masach = book.getMasach();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnthem_hdct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String mahoadon = edtmahd.getText().toString();
                    int soluongmua = Integer.parseInt(edsoluongmua.getText().toString());
                    float giabia = Float.parseFloat(tvgiabia.getText().toString());
                    int slcon = Integer.parseInt(tvslc.getText().toString());
                    if (soluongmua>slcon){
                        Toast.makeText(getApplicationContext(),"Sl hàng ko đủ",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        HDCT hdct = new HDCT(1,mahoadon,masach,soluongmua,giabia);
                        HDCTDao hdctDao = new HDCTDao(Activity_HDCT.this);
                        hdctDao.themHDCT(hdct);
                        listhdct.add(hdct);
                        hdctAdapter.notifyDataSetChanged();
                    }

                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Lỗi nhập liệu",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btntt_hdct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slcon = Integer.parseInt(tvslc.getText().toString());
                int soluongmua = Integer.parseInt(edsoluongmua.getText().toString());
                thanhTienn = 0;
                for (HDCT hd: listhdct) {
                    BookDao bookDao = new BookDao(getApplicationContext());
                    bookDao.suaBookhdct(hd.getMasach(),slcon-soluongmua);
                    thanhTienn = thanhTienn + hd.getSoluongmua() * hd.getGiabia();
                }
                tvthanhtien.setText("Tổng tiền: " +thanhTienn);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Hóa đơn chi tiết");
        return true;
    }
    public void getmasach(){
        BookDao bookDao = new BookDao(Activity_HDCT.this);
        listbook = bookDao.dsbook();
        ArrayAdapter<Book> dataad = new ArrayAdapter<Book>(this,android.R.layout.simple_spinner_item,listbook);
        dataad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnmasach.setAdapter(dataad);
    }
}