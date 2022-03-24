package com.example.asm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.asm.R;
import com.example.asm.adapter.BookAdapter;
import com.example.asm.dao.BookDao;
import com.example.asm.dao.CategoryDao;
import com.example.asm.model.Book;

import com.example.asm.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Activity_Book extends AppCompatActivity {
    BookAdapter bookAdapter;
    ArrayList<Book> listbook = new ArrayList<>();
    ArrayList<Category> listtheloai = new ArrayList<>();
    RecyclerView rcv;
    Spinner spn;
    String matheloai="";
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        FloatingActionButton fabbook = findViewById(R.id.fabbook);
        rcv = findViewById(R.id.rcvbook);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        BookDao bookDao = new BookDao(this);
        listbook = bookDao.dsbook();
        bookAdapter = new BookAdapter(listbook);
        rcv.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
        rcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy>0){
                    fabbook.hide();
                }
                else {
                    fabbook.show();
                }
            }
        });
        fabbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialogthem(Gravity.CENTER);
            }
        });
        bookAdapter.setonitemclicklistener(new BookAdapter.Onitemclicklistener() {
            @Override
            public void onitemclick(int position) {
                Book book = listbook.get(position);
                opendialogsua(Gravity.CENTER,book);
            }
        });
        bookAdapter.setDeletelistener(new BookAdapter.Onitemclicklistenerr() {
            @Override
            public void onitemclickk(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Book.this, android.R.style.Theme_Material_Dialog_Alert);
                builder.setTitle("Bạn chắc chắn xóa dữ liệu?");
                builder.setMessage("Hãy lựa chọn bên dưới để xác nhận");
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BookDao bookDAO = new BookDao(getApplicationContext());
                        bookDAO.xoaBook(listbook.remove(position));
                        bookAdapter.notifyDataSetChanged();
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
        setTitle("Quản lý sách");
        getMenuInflater().inflate(R.menu.menu3,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.id_searchh).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bookAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bookAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void opendialogthem(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_addbook);
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
        spn = dialog.findViewById(R.id.spnTheloai);
        getmatl();
        TextInputEditText edmasach,edtensach,edtacgia,ednxb,edgiabia,edsoluong;
        Button btnadd2,btncancel2;
        edmasach = dialog.findViewById(R.id.edMasach);
        edtensach = dialog.findViewById(R.id.masachhdct);
        edtacgia = dialog.findViewById(R.id.edTacgia);
        ednxb = dialog.findViewById(R.id.edNxb);
        edgiabia = dialog.findViewById(R.id.edGiabia);
        edsoluong = dialog.findViewById(R.id.edSoluong);
        btnadd2 = dialog.findViewById(R.id.btnadd_hdct);
        btncancel2 = dialog.findViewById(R.id.btncancel_hdct);
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                matheloai = listtheloai.get(spn.getSelectedItemPosition()).getMatheloai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnadd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String masach = edmasach.getText().toString();
                    String tacgia = edtacgia.getText().toString();
                    String nxb = ednxb.getText().toString();
                    float giabia =Float.parseFloat(edgiabia.getText().toString());
                    int soluong = Integer.parseInt(edsoluong.getText().toString());
                    String tensach = edtensach.getText().toString();
                    if (!masach.isEmpty()&& !tacgia.isEmpty() && !nxb.isEmpty() && !String.valueOf(giabia).isEmpty()
                            && !String.valueOf(soluong).isEmpty()){
                        Book book = new Book(masach,matheloai,tacgia,nxb,giabia,soluong,tensach);
                        BookDao bookDao = new BookDao(Activity_Book.this);
                        bookDao.themBook(book);
                        listbook.add(book);
                        bookAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Lỗi nhập liệu",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btncancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edmasach.setText("");
                edtensach.setText("");
                edtacgia.setText("");
                ednxb.setText("");
                edgiabia.setText("");
                edsoluong.setText("");
            }
        });
        dialog.show();
    }
    private void opendialogsua(int gravity,Book book){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_addbook);
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
        spn = dialog.findViewById(R.id.spnTheloai);
        getmatl();
        TextInputEditText edmasach,edtensach,edtacgia,ednxb,edgiabia,edsoluong;
        Button btnadd2,btncancel2;
        edmasach = dialog.findViewById(R.id.edMasach);
        edtensach = dialog.findViewById(R.id.masachhdct);
        edtacgia = dialog.findViewById(R.id.edTacgia);
        ednxb = dialog.findViewById(R.id.edNxb);
        edgiabia = dialog.findViewById(R.id.edGiabia);
        edsoluong = dialog.findViewById(R.id.edSoluong);
        btnadd2 = dialog.findViewById(R.id.btnadd_hdct);
        btncancel2 = dialog.findViewById(R.id.btncancel_hdct);
        edmasach.setText(book.getMasach());
        edmasach.setEnabled(false);
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                matheloai = listtheloai.get(spn.getSelectedItemPosition()).getMatheloai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnadd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String masach = edmasach.getText().toString();
                    String tacgia = edtacgia.getText().toString();
                    String nxb = ednxb.getText().toString();
                    float giabia =Float.parseFloat(edgiabia.getText().toString());
                    int soluong = Integer.parseInt(edsoluong.getText().toString());
                    String tensach = edtensach.getText().toString();
                    if (!masach.isEmpty()&& !tacgia.isEmpty() && !nxb.isEmpty() && !String.valueOf(giabia).isEmpty()
                            && !String.valueOf(soluong).isEmpty()){
                        Book book = new Book(masach,matheloai,tacgia,nxb,giabia,soluong,tensach);
                        BookDao bookDao = new BookDao(Activity_Book.this);
                        bookDao.suaBook(book);
                        for (int i = 0; i < listbook.size(); i++) {
                            if (book.getMasach().equals(listbook.get(i).getMasach())){
                                listbook.set(i,book);
                            }
                        }
                        bookAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Lỗi nhập liệu",Toast.LENGTH_LONG).show();
                }
            }
        });
        btncancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edmasach.setText("");
                edtensach.setText("");
                edtacgia.setText("");
                ednxb.setText("");
                edgiabia.setText("");
                edsoluong.setText("");
            }
        });
        dialog.show();
    }
    public void getmatl(){
        CategoryDao  categoryDao = new CategoryDao(Activity_Book.this);
        listtheloai = categoryDao.dscategory();
        ArrayAdapter<Category> dataadapter = new ArrayAdapter<Category>(this,android.R.layout.simple_spinner_item,listtheloai);
        dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(dataadapter);
    }
}