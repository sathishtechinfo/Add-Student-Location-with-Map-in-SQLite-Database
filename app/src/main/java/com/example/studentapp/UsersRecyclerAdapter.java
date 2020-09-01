package com.example.studentapp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

/**
 * Created by lalit on 10/10/2016.
 */

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<Student> listUsers;
    Context mContext;

    public UsersRecyclerAdapter(Context mContext,List<Student> listUsers) {
        this.listUsers = listUsers;
        this.mContext=mContext;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_recycler, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {
        holder.txtname.setText(listUsers.get(position).getUsername());
        holder.txtclass.setText(listUsers.get(position).getClassname());
        holder.txtsection.setText(listUsers.get(position).getSection());

        if(listUsers.get(position).getImagePath()!=null)
        {
            File imgFile = new  File(listUsers.get(position).getImagePath());
            if(imgFile.exists()) {

                try {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    holder.imgprofile.setImageBitmap(myBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }


        holder.llLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext,AddStudent_Activity.class);
                i.putExtra("ID",listUsers.get(position).getID());
                mContext.startActivity(i);


            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+listUsers.size());
        return listUsers.size();
    }


    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView txtname;
        public TextView txtclass;
        public TextView txtsection;
        public ImageView imgprofile;
        LinearLayout llLayout;

        public UserViewHolder(View view) {
            super(view);
            txtname = (TextView) view.findViewById(R.id.txtname);
            txtclass = (TextView) view.findViewById(R.id.txtclass);
            txtsection = (TextView) view.findViewById(R.id.txtsec);
            imgprofile=(ImageView) view.findViewById(R.id.img_pro);
            llLayout=view.findViewById(R.id.ll_layout);
        }
    }


}
