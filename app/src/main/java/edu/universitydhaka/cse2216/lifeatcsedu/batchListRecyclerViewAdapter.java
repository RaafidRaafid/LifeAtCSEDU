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

import java.util.ArrayList;

class batchListRecyclerViewAdapter extends RecyclerView.Adapter<batchListRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "Recycler View";
    private ArrayList<String >mBatch = new ArrayList<>();
    private Context context;

    public batchListRecyclerViewAdapter(Context context, ArrayList<String> mBatch) {
        this.mBatch = mBatch;
        this.context = context;
    }
    @NonNull
    @Override
    public batchListRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listofbatch,viewGroup,false);
        batchListRecyclerViewAdapter.ViewHolder viewHolder = new batchListRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull batchListRecyclerViewAdapter.ViewHolder viewHolder, final int i) {

        Log.d(TAG, "onBindViewHolder: " + i);

        viewHolder.BatchList.setText(mBatch.get(i));


        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To Single Teacher Page
                Intent intent = new Intent(context,showStudentList.class);
                intent.putExtra("showKey",mBatch.get(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBatch.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView BatchList;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            BatchList = itemView.findViewById(R.id.batch_name);
            parentLayout = itemView.findViewById(R.id.batch_element_layout);
        }
    }
}
