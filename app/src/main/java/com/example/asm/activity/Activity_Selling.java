package com.example.asm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.asm.R;
import com.example.asm.adapter.BookAdapter;
import com.example.asm.dao.BookDao;
import com.example.asm.model.Book;
import java.util.ArrayList;

public class Activity_Selling extends AppCompatActivity {
//    ArrayList<String> list = new ArrayList<>();
    ArrayList<Book> listbook = new ArrayList<>();
//    Spinner spn_thang;
    EditText edttktop10;
    ImageView btntktop10;
    BookAdapter bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        spn_thang = findViewById(R.id.spn_thang);
//        setContentView(R.layout.activity_selling);
//        RecyclerView rcv = findViewById(R.id.rcv_selling);

//        getthang();
//        spn_thang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
//                rcv.setLayoutManager(linearLayoutManager);
//                BookDao bookDao = new BookDao(getApplicationContext());
//                listbook = bookDao.dstop10(position+1);
//                bookAdapter = new BookAdapter(listbook);
//                rcv.setAdapter(bookAdapter);
//                bookAdapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        edttktop10 = findViewById(R.id.edttktop10);
        btntktop10 = findViewById(R.id.btntktop10);
        RecyclerView rcv = findViewById(R.id.rcv_selling);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        BookDao bookDao = new BookDao(this);
        listbook = bookDao.dsbook();
        bookAdapter = new BookAdapter(listbook);
        rcv.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
        btntktop10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk = edttktop10.getText().toString();
                bookAdapter.getFilter().filter(tk);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Top 10 sách bán chạy");
        return true;
    }
//    public void getthang(){
//        for (int i = 1; i < 13; i++) {
//            list.add("Tháng"+ i);
//        }
//        ArrayAdapter<String> dataadapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,list);
//        dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spn_thang.setAdapter(dataadapter);
//    }
}