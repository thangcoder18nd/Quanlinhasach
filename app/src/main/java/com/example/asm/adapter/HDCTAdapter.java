package com.example.asm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.asm.R;
import com.example.asm.dao.HDCTDao;
import com.example.asm.model.Book;
import com.example.asm.model.HDCT;
import java.util.ArrayList;

public class HDCTAdapter extends RecyclerView.Adapter<HDCTAdapter.HDCTViewHolder>{
    ArrayList<HDCT> listhdct;
    public HDCTAdapter(ArrayList<HDCT> listhdct) {
        this.listhdct = listhdct;
    }

    @NonNull
    @Override
    public HDCTAdapter.HDCTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hdct,parent,false);
        return new HDCTAdapter.HDCTViewHolder(view,deletelistener);
    }

    @Override
    public void onBindViewHolder(@NonNull HDCTAdapter.HDCTViewHolder holder, int position) {
        HDCT hdct = listhdct.get(position);
        if (hdct == null){
            return;
        }
        holder.mahdct.setText("Mã HDCT :"+hdct.getMahdct());
        holder.mahd.setText("Mã HD:"+hdct.getMahoadon());
        holder.masachhdct.setText("Mã sách:"+hdct.getMasach());
        holder.giabia.setText("Giá bìa :"+hdct.getGiabia() +"vnd");
        holder.thanhtien.setText("Thành tiền :"+hdct.getGiabia()*hdct.getSoluongmua() +"vnd");
    }

    @Override
    public int getItemCount() {
        if (listhdct!= null){
            return listhdct.size();
        }
        return 0;
    }

    public class HDCTViewHolder extends RecyclerView.ViewHolder{
        TextView mahdct,mahd,masachhdct,giabia,thanhtien;
        ImageView imgdelete2;
        public HDCTViewHolder(@NonNull View itemView, Onitemclicklistenerr listioner) {
            super(itemView);
            mahdct = itemView.findViewById(R.id.mahdct);
            mahd = itemView.findViewById(R.id.mahd);
            masachhdct = itemView.findViewById(R.id.masachhdct);
            giabia = itemView.findViewById(R.id.giabia);
            thanhtien = itemView.findViewById(R.id.thanhtien);
            imgdelete2 = itemView.findViewById(R.id.imgdelete2);
            imgdelete2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deletelistener != null){
                        int position = getLayoutPosition();
                        if (position != RecyclerView.NO_POSITION){
                            deletelistener.onitemclickk(position);
                        }
                    }
                }
            });
        }
    }

    private HDCTAdapter.Onitemclicklistenerr deletelistener;
    public interface Onitemclicklistenerr{
        void onitemclickk(int position);
    }
    public void setDeletelistener(HDCTAdapter.Onitemclicklistenerr deletelistener) {
        this.deletelistener = deletelistener;
    }
}
