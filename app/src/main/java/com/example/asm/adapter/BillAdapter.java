package com.example.asm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.R;
import com.example.asm.model.Bill;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder>{
    ArrayList<Bill> listbill = new ArrayList<>();
    public BillAdapter(ArrayList<Bill> listbill) {
        this.listbill = listbill;
    }

    @NonNull
    @Override
    public BillAdapter.BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill,parent,false);
        return new BillAdapter.BillViewHolder(view,mlistioner,deletelistener);
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.BillViewHolder holder, int position) {
        Bill bill = listbill.get(position);
        if (bill == null){
            return;
        }
        holder.mahoadon.setText("Mã HD :"+bill.getMahoadon());
        holder.ngaymua.setText("Ngày mua :"+bill.getNgaymua());

    }

    @Override
    public int getItemCount() {
        if (listbill!= null){
            return listbill.size();
        }
        return 0;
    }

    public class BillViewHolder extends RecyclerView.ViewHolder{
        TextView mahoadon,ngaymua;
        ImageView imgdelete2;
        public BillViewHolder(@NonNull View itemView, BillAdapter.Onitemclicklistener mlistioner, BillAdapter.Onitemclicklistenerr listioner) {
            super(itemView);
            mahoadon = itemView.findViewById(R.id.mahoadon);
            ngaymua = itemView.findViewById(R.id.ngaymua);
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
    private BillAdapter.Onitemclicklistener mlistioner;
    public interface Onitemclicklistener{
        void onitemclick(int position);
    }
    public void setonitemclicklistener(BillAdapter.Onitemclicklistener listener){
        mlistioner = listener;
    }

    private BillAdapter.Onitemclicklistenerr deletelistener;
    public interface Onitemclicklistenerr{
        void onitemclickk(int position);
    }
    public void setDeletelistener(BillAdapter.Onitemclicklistenerr deletelistener) {
        this.deletelistener = deletelistener;
    }
}
