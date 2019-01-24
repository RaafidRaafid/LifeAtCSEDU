package edu.universitydhaka.cse2216.lifeatcsedu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class stuffListRecyclerViewAdapter extends RecyclerView.Adapter<stuffListRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "Recycler View";

    private ArrayList<String> mStuffName = new ArrayList<>();
    private ArrayList<String> mStuffEmail = new ArrayList<>();
    private ArrayList<String> mStuffKey = new ArrayList<>();
    private Context context;

    public stuffListRecyclerViewAdapter( Context context, ArrayList<String> mStuffName, ArrayList<String> mStuffEmail,ArrayList<String> mStuffKey) {
        this.mStuffName = mStuffName;
        this.mStuffEmail = mStuffEmail;
        this.mStuffKey=mStuffKey;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listofstuff,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {

        Log.d(TAG, "onBindViewHolder: " + i);

        viewHolder.stuffListName.setText(mStuffName.get(i));


        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To Single User Page
                Intent intent = new Intent(context,showSingleStuff.class);
                intent.putExtra("showKey",mStuffKey.get(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStuffName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView stuffListName;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stuffListName = itemView.findViewById(R.id.stuff_name);
            parentLayout = itemView.findViewById(R.id.stuff_element_layout);
        }
    }
}
