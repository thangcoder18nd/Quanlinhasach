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
import com.example.asm.model.User;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements Filterable {
    ArrayList<User> listuser = new ArrayList<>();
    ArrayList<User> listuserold = new ArrayList<>();
    public UserAdapter(ArrayList<User> listuser) {
        this.listuser = listuser;
        this.listuserold = listuser;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new UserViewHolder(view,mlistioner);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        User user = listuser.get(position);
        if (user == null){
            return;
        }
        holder.username.setText("Username:"+user.getUsername());
        holder.phone.setText("Phone:"+user.getPhone());
        holder.hoten.setText("Name:"+user.getHoten());
        holder.ghichu.setText("Ghi chú:"+user.getGhichu());
        if (position%3==0){
            holder.avatar.setImageResource(R.drawable.emone);
        }
        else if (position%3==1){
            holder.avatar.setImageResource(R.drawable.emtwo);
        }
        else{
            holder.avatar.setImageResource(R.drawable.emthree);
        }
    }

    @Override
    public int getItemCount() {
        if (listuser!= null){
            return listuser.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView username,phone,hoten,ghichu;
        ImageView imgdelete,avatar;
        public UserViewHolder(@NonNull View itemView, Onitemclicklistener listioner) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            phone = itemView.findViewById(R.id.phone);
            hoten = itemView.findViewById(R.id.hoten);
            ghichu = itemView.findViewById(R.id.ghichu);
            imgdelete = itemView.findViewById(R.id.imgdelete);
            avatar = itemView.findViewById(R.id.avatar);
            imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listioner != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listioner.onitemclick(position);
                        }
                    }
                }
            });
        }
    }
    private Onitemclicklistener mlistioner;
    public interface Onitemclicklistener{
        void onitemclick(int position);
    }
    public void setonitemclicklistener(Onitemclicklistener listener){
        mlistioner = listener;
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strsearch = constraint.toString();
                if (strsearch.isEmpty()){
                    listuser = listuserold;
                }
                else {
                    ArrayList<User> listt =  new ArrayList<>();
                    for (User user : listuserold){
                        if (user.getHoten().toLowerCase().contains(strsearch.toLowerCase())){
                            listt.add(user);
                        }
                    }
                    listuser = listt;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listuser;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listuser = (ArrayList<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
