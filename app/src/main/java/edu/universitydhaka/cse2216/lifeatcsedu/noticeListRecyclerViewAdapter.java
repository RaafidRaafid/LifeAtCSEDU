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

public class noticeListRecyclerViewAdapter extends RecyclerView.Adapter<noticeListRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "Recycler View";

    private ArrayList<String> mNoticeTitles = new ArrayList<>();
    private ArrayList<String> mNoticeKey = new ArrayList<>();
    private ArrayList<String> mNoticeTime = new ArrayList<>();
    private Context context;

    public noticeListRecyclerViewAdapter( Context context, ArrayList<String> mNoticeTitles, ArrayList<String> mNoticeTime,ArrayList<String> mNoticeKey) {
        this.mNoticeTitles = mNoticeTitles;
        this.mNoticeTime = mNoticeTime;
        this.mNoticeKey=mNoticeKey;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listofnotices,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {

        Log.d(TAG, "onBindViewHolder: " + i);

        viewHolder.noticeListTitle.setText(mNoticeTitles.get(i));
        viewHolder.noticeListTime.setText(mNoticeTime.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To Single User Page
                Intent intent = new Intent(context,showSingleNotice.class);
                intent.putExtra("showKey",mNoticeKey.get(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNoticeTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView noticeListTitle;
        TextView noticeListTime;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeListTitle = itemView.findViewById(R.id.notice_title);
            noticeListTime = itemView.findViewById(R.id.notice_time);
            parentLayout = itemView.findViewById(R.id.notice_element_layout);
        }
    }
}
