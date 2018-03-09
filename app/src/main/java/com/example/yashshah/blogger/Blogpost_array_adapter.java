package com.example.yashshah.blogger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Yash shah on 11-07-2017.
 */

public class Blogpost_array_adapter extends BaseAdapter
{

    Context context;
    private static LayoutInflater inflater=null;
    private int mLastPosition;
    ArrayList<PostElement> arrayList_blogpost=new ArrayList<>();
    public Blogpost_array_adapter(Context context,ArrayList<PostElement> arrayList_blogpost)
    {
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.arrayList_blogpost.addAll(arrayList_blogpost);
    }

    @Override
    public int getCount() {
        return arrayList_blogpost.size();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return arrayList_blogpost.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.blogpost_card_view,null);
            holder.Username=(TextView)convertView.findViewById(R.id.Username);
            holder.Category=(TextView)convertView.findViewById(R.id.Category);
            holder.Timestamp=(TextView)convertView.findViewById(R.id.TimeStamp);
            holder.BlogPost=(TextView)convertView.findViewById(R.id.Post_Heading);
            holder.PostImage=(ImageView)convertView.findViewById(R.id.Post_image);
            convertView.setTag(holder);
        }
        else {
            holder = (Holder) convertView.getTag();
        }
        holder.Username.setText(arrayList_blogpost.get(position).getUsername().toString());
        holder.Category.setText(arrayList_blogpost.get(position).getCategory().toString());
        holder.Timestamp.setText(arrayList_blogpost.get(position).getDate().toString());
        holder.BlogPost.setText(arrayList_blogpost.get(position).getPost().toString());
        System.out.print(arrayList_blogpost.get(position).getUsername().toString());
        Glide.with(context).load(arrayList_blogpost.get(position).getImage_link().toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.PostImage);

        float initialTranslation = (mLastPosition <= position ? 500f : -500f);

        convertView.setTranslationY(initialTranslation);
        convertView.animate()
                .setInterpolator(new DecelerateInterpolator(1.0f))
                .translationY(0f)
                .setDuration(300l)
                .setListener(null);

        // Keep track of the last position we loaded
        mLastPosition = position;

        return convertView;
    }

    public class Holder
    {
        TextView Username,Category,Timestamp,BlogPost;
        ImageView PostImage;
    }
}
