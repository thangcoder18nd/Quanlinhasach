package com.example.asm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.asm.R;
import com.example.asm.model.Book;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>implements Filterable {
    ArrayList<Book> listbook;
    ArrayList<Book> listbookold ;
    public BookAdapter(ArrayList<Book> listbook) {
        this.listbook = listbook;
        this.listbookold=listbook;
    }

    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent,false);
        return new BookAdapter.BookViewHolder(view,mlistioner,deletelistener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
        Book book = listbook.get(position);
        if (book == null){
            return;
        }
        holder.masach.setText("Mã sách :"+book.getMasach());
        holder.matheloai.setText("Mã TL :"+book.getMatheloai());
        holder.tacgia.setText("TG:"+book.getTacgia());
        holder.nxb.setText("NXB :"+book.getNxb());
        holder.giabia.setText("Giá :"+book.getGiabia());
        holder.soluong.setText("SL :"+book.getSoluong());
        holder.tensach.setText("Tên sách :"+book.getTensach());
    }

    @Override
    public int getItemCount() {
        if (listbook!= null){
            return listbook.size();
        }
        return 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        TextView masach,matheloai,tacgia,nxb,giabia,soluong,tensach;
        ImageView imgdelete3;
        public BookViewHolder(@NonNull View itemView, Onitemclicklistener listioner, Onitemclicklistenerr deletelistener) {
            super(itemView);
            masach = itemView.findViewById(R.id.masach);
            matheloai = itemView.findViewById(R.id.matheloai);
            tacgia = itemView.findViewById(R.id.tacgia);
            nxb = itemView.findViewById(R.id.nxb);
            giabia = itemView.findViewById(R.id.giabia);
            soluong = itemView.findViewById(R.id.soluong);
            tensach = itemView.findViewById(R.id.masachhdct);
            imgdelete3 = itemView.findViewById(R.id.btndelete3);
            imgdelete3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deletelistener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            deletelistener.onitemclickk(position);
                        }
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mlistioner != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mlistioner.onitemclick(position);
                        }
                    }
                }
            });
        }
    }
    private BookAdapter.Onitemclicklistener mlistioner;
    public interface Onitemclicklistener{
        void onitemclick(int position);
    }
    public void setonitemclicklistener(BookAdapter.Onitemclicklistener listener){
        mlistioner = listener;
    }
    private BookAdapter.Onitemclicklistenerr deletelistener;
    public interface Onitemclicklistenerr{
        void onitemclickk(int position);
    }
    public void setDeletelistener(BookAdapter.Onitemclicklistenerr deletelistener) {
        this.deletelistener = deletelistener;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strsearch = constraint.toString();
                if (strsearch.isEmpty()){
                    listbook = listbookold;
                }
                else {
                    ArrayList<Book> listt =  new ArrayList<>();
                    for (Book book : listbookold){
                        if (book.getTensach().toLowerCase().contains(strsearch.toLowerCase())){
                            listt.add(book);
                        }
                    }
                    listbook = listt;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listbook;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listbook = (ArrayList<Book>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
