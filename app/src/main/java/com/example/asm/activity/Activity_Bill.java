package com.example.asm.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.adapter.BillAdapter;
import com.example.asm.dao.BillDao;
import com.example.asm.model.Bill;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Activity_Bill extends AppCompatActivity {
    EditText eddate;
    ArrayList<Bill> listbill = new ArrayList<>();
    BillAdapter billAdapter ;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        RecyclerView rcv = findViewById(R.id.rcvbill);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        BillDao billDao = new BillDao(this);
        try {
            listbill = billDao.dsbill();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        billAdapter = new BillAdapter(listbill);
        rcv.setAdapter(billAdapter);
        billAdapter.notifyDataSetChanged();
        billAdapter.setDeletelistener(new BillAdapter.Onitemclicklistenerr() {
            @Override
            public void onitemclickk(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Bill.this, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle("Bạn chắc chắn xóa dữ liệu?");
                builder.setMessage("Hãy lựa chọn bên dưới để xác nhận");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BillDao billDao = new BillDao(getApplicationContext());
                        billDao.xoaBill(listbill.remove(position));
                        billAdapter.notifyDataSetChanged();
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
        billAdapter.setonitemclicklistener(new BillAdapter.Onitemclicklistener() {
            @Override
            public void onitemclick(int position) {
                Bill bill = listbill.get(position);
                Intent intent = new Intent(Activity_Bill.this,listHDCT_id.class);
                Bundle b = new Bundle();
                b.putString("mahoadon", bill.getMahoadon());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Hóa đơn");
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        opendialog(Gravity.CENTER);
        return super.onOptionsItemSelected(item);
    }

    private void opendialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_bill);
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
        TextInputEditText edmahoadon;
        Button btnok,btn_pick;
        edmahoadon = dialog.findViewById(R.id.edmahoadon);
        eddate = dialog.findViewById(R.id.edthang);
        btn_pick = dialog.findViewById(R.id.btn_pick_from);
        btnok = dialog.findViewById(R.id.btnokk);
        btn_pick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                opendialogdate(Gravity.CENTER);
            }
        });
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String mahoadon = edmahoadon.getText().toString();
                    Date ngaymua = sdf.parse(eddate.getText().toString());
                    if (!mahoadon.isEmpty()&& !eddate.getText().toString().isEmpty()){
                        Bill bill = new Bill(mahoadon,ngaymua);
                        BillDao billDao = new BillDao(Activity_Bill.this);
                        billDao.themBill(bill);
                        listbill.add(bill);
                        billAdapter.notifyDataSetChanged();
                        Intent intent = new Intent(Activity_Bill.this,Activity_HDCT.class);
                        Bundle b2 = new Bundle();
                        b2.putString("mahoadon2", bill.getMahoadon());
                        intent.putExtras(b2);
                        startActivity(intent);
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Lỗi nhập liệu",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void opendialogdate(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_date);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAtribute = window.getAttributes();
        windowAtribute.gravity = gravity;
        if (Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }
        else {
            dialog.setCancelable(false);
        }
        DatePicker datePicker = dialog.findViewById(R.id.datepicker);
        Button btnokee = dialog.findViewById(R.id.btnokee);

        btnokee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                eddate.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
            }
        });
        dialog.show();
    }

}