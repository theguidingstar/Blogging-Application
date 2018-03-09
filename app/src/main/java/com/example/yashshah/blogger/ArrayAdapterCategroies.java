package com.example.yashshah.blogger;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Yash shah on 11-07-2017.
 */

public class ArrayAdapterCategroies extends RecyclerView.Adapter<ArrayAdapterCategroies.MyViewHolder> {
    Context context;
    private int mLastPosition;
    ArrayList<String> arrayList_Categories1=new ArrayList<>();
    ArrayList<String> arrayList_Categories_Images1=new ArrayList<>();
    ArrayList<String> arrayList_Categories=new ArrayList<>();
    ArrayList<String> arrayList_Categories_Images=new ArrayList<>();
    ArrayAdapterCategroies(Context context,ArrayList<String> arrayList_Categories,ArrayList<String> arrayList_Categories_Images)
    {
        this.context=context;
        arrayList_Categories1=arrayList_Categories;
        arrayList_Categories_Images1=arrayList_Categories_Images;
        this.arrayList_Categories.addAll(arrayList_Categories1);
        this.arrayList_Categories_Images.addAll(arrayList_Categories_Images1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return arrayList_Categories.size();
    }


    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Glide.with(context).load(arrayList_Categories_Images.get(position).toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.relativeLayout);

        holder.Categories_Name.setText(arrayList_Categories.get(position).toString());
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categories_cardview, parent, false);

        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView relativeLayout;
        TextView Categories_Name;

        public MyViewHolder(View view) {
            super(view);
            relativeLayout=(ImageView)view.findViewById(R.id.Relative_background_categories);
            Categories_Name=(TextView)view.findViewById(R.id.Categories_textview);

        }
    }
}
