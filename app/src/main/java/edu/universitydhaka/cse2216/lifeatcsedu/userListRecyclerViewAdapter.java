package edu.universitydhaka.cse2216.lifeatcsedu;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class userListRecyclerViewAdapter extends RecyclerView.Adapter<userListRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "Recycler View";

    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImageEmails = new ArrayList<>();
    private Context context;

    public userListRecyclerViewAdapter( Context context, ArrayList<String> mImages, ArrayList<String> mImageNames, ArrayList<String> mImageEmails) {
        this.mImages = mImages;
        this.mImageNames = mImageNames;
        this.mImageEmails = mImageEmails;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listofitems,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {

        Log.d(TAG, "onBindViewHolder: " + i);
        
        Glide.with(context)
                .asBitmap()
                .load(mImages.get(i))
                .into(viewHolder.userListImage);

        viewHolder.userListName.setText(mImageNames.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To Single User Page
                Intent intent = new Intent(context,showSingleUser.class);
                intent.putExtra("showUser",mImageEmails.get(i));
                context.startActivity(intent);
                Toast.makeText(context,mImageNames.get(i),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView userListImage;
        TextView userListName;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userListImage = itemView.findViewById(R.id.image);
            userListName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.user_element_layout);
        }
    }
}
