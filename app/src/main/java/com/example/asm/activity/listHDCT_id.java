package com.example.asm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.asm.R;
import com.example.asm.adapter.HDCTAdapter;
import com.example.asm.dao.BookDao;
import com.example.asm.dao.HDCTDao;
import com.example.asm.model.Bill;
import com.example.asm.model.HDCT;

import java.util.ArrayList;
import java.util.List;

public class listHDCT_id extends AppCompatActivity {
    ArrayList<HDCT> listhdct = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdct_id);
        RecyclerView rcv = findViewById(R.id.rcvhtct_id);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        HDCTDao hdctDao = new HDCTDao(this);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            listhdct = hdctDao.dshdct_mahd(b.getString("mahoadon"));
        }
        //nghi vấn lỗi
        HDCTAdapter hdctAdapter = new HDCTAdapter(listhdct);
        rcv.setAdapter(hdctAdapter);
        hdctAdapter.setDeletelistener(new HDCTAdapter.Onitemclicklistenerr() {
            @Override
            public void onitemclickk(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(listHDCT_id.this, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle("Bạn chắc chắn xóa dữ liệu?");
                builder.setMessage("Hãy lựa chọn bên dưới để xác nhận");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HDCTDao hdctDao = new HDCTDao(getApplicationContext());
                        hdctDao.xoaHDCT(listhdct.get(position));
                        listhdct.remove(position);
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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Danh sách HDCT");
        return true;
    }
}