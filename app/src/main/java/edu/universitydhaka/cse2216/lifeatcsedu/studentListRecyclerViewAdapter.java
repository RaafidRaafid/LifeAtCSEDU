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

import java.net.ConnectException;
import java.util.ArrayList;

class studentListRecyclerViewAdapter extends RecyclerView.Adapter<studentListRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "Recycler View";
    private ArrayList<String >mStudentName = new ArrayList<>();
    private ArrayList<String >mStudentBatch = new ArrayList<>();
    private ArrayList<String >mStudentKey = new ArrayList<>();
    private Context context;

    public studentListRecyclerViewAdapter(Context context, ArrayList<String> mStudentName, ArrayList<String> mStudentBatch, ArrayList<String> mStudentKey) {
        this.mStudentName = mStudentName;
        this.mStudentBatch = mStudentBatch;
        this.mStudentKey = mStudentKey;
        this.context = context;
    }
    @NonNull
    @Override
    public studentListRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listofstudent,viewGroup,false);
        studentListRecyclerViewAdapter.ViewHolder viewHolder = new studentListRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull studentListRecyclerViewAdapter.ViewHolder viewHolder, final int i) {

        Log.d(TAG, "onBindViewHolder: " + i);

        viewHolder.StudentNameList.setText(mStudentName.get(i));


        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To Single Teacher Page
                Intent intent = new Intent(context,showSingleStudent.class);
                intent.putExtra("showKey",mStudentKey.get(i));
                intent.putExtra("batch",mStudentBatch.get(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStudentName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView StudentNameList;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            StudentNameList = itemView.findViewById(R.id.student_name);
            parentLayout = itemView.findViewById(R.id.student_element_layout);
        }
    }
}
