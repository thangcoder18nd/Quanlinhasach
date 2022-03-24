package com.example.asm.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
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
import com.example.asm.model.Category;
import com.example.asm.model.User;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> implements Filterable {
    ArrayList<Category> listcategory = new ArrayList<>();
    ArrayList<Category> listcategoryold = new ArrayList<>();
    public CategoryAdapter(ArrayList<Category> listcategory) {
        this.listcategory = listcategory;
        this.listcategoryold = listcategory;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new CategoryAdapter.CategoryViewHolder(view,mlistioner,deletelistener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        Category category = listcategory.get(position);
        if (category == null){
            return;
        }
        if (position == 0 ){
            holder.matheloai.setTextColor(Color.GREEN);
            holder.tentheloai.setTextColor(Color.GREEN);
            holder.mota.setTextColor(Color.GREEN);
            holder.vitri.setTextColor(Color.GREEN);
        }
        else if(position >0 && position %2 ==0){
            holder.matheloai.setTypeface(null, Typeface.ITALIC);
            holder.tentheloai.setTypeface(null, Typeface.ITALIC);
            holder.mota.setTypeface(null, Typeface.ITALIC);
            holder.vitri.setTypeface(null, Typeface.ITALIC);
        }
        else {
            holder.matheloai.setTypeface(null, Typeface.BOLD);
            holder.tentheloai.setTypeface(null, Typeface.BOLD);
            holder.mota.setTypeface(null, Typeface.BOLD);
            holder.vitri.setTypeface(null, Typeface.BOLD);
        }
        holder.matheloai.setText("Mã thể loại :"+category.getMatheloai());
        holder.tentheloai.setText("Tên thể loại :"+category.getTentheloai());
        holder.mota.setText("Mô tả :"+category.getMota());
        holder.vitri.setText("Vị trí :" + category.getVitri());
    }

    @Override
    public int getItemCount() {
        if (listcategory!= null){
            return listcategory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView matheloai,tentheloai,mota,vitri;
        ImageView imgdelete2;
        public CategoryViewHolder(@NonNull View itemView, Onitemclicklistener mlistioner, Onitemclicklistenerr listioner) {
            super(itemView);
            matheloai = itemView.findViewById(R.id.matheloai);
            tentheloai = itemView.findViewById(R.id.tentheloai);
            mota = itemView.findViewById(R.id.mota);
            vitri = itemView.findViewById(R.id.vitri);
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
    private CategoryAdapter.Onitemclicklistener mlistioner;
    public interface Onitemclicklistener{
        void onitemclick(int position);
    }
    public void setonitemclicklistener(CategoryAdapter.Onitemclicklistener listener){
        mlistioner = listener;
    }

    private CategoryAdapter.Onitemclicklistenerr deletelistener;
    public interface Onitemclicklistenerr{
        void onitemclickk(int position);
    }
    public void setDeletelistener(CategoryAdapter.Onitemclicklistenerr deletelistener) {
        this.deletelistener = deletelistener;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strsearch = constraint.toString();
                if (strsearch.isEmpty()){
                    listcategory = listcategoryold;
                }
                else {
                    ArrayList<Category> listt =  new ArrayList<>();
                    for (Category category : listcategoryold){
                        if (category.getTentheloai().toLowerCase().contains(strsearch.toLowerCase())){
                            listt.add(category);
                        }
                    }
                    listcategory = listt;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listcategory;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listcategory = (ArrayList<Category>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
