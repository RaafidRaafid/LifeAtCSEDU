package edu.universitydhaka.cse2216.lifeatcsedu;

import android.content.Context;
import android.content.Intent;
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

class teacherListRecyclerViewAdapter extends RecyclerView.Adapter<teacherListRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "Recycler View";

    private ArrayList<String> mTeacherName = new ArrayList<>();
    private ArrayList<String> mTeacherDesignation = new ArrayList<>();
    private ArrayList<String> mTeacherKey = new ArrayList<>();
    private Context context;


    public teacherListRecyclerViewAdapter(Context context, ArrayList<String> mTeacherName, ArrayList<String> mTeacherDesignation, ArrayList<String> mTeacherKey) {
        this.mTeacherName = mTeacherName;
        this.mTeacherDesignation = mTeacherDesignation;
        this.mTeacherKey=mTeacherKey;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listofteacher,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull teacherListRecyclerViewAdapter.ViewHolder viewHolder, final int i) {

        Log.d(TAG, "onBindViewHolder: " + i);

        viewHolder.TeacherListName.setText(mTeacherName.get(i));


        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To Single Teacher Page
                Intent intent = new Intent(context,showSingleTeacher.class);
                intent.putExtra("showKey",mTeacherKey.get(i));
                intent.putExtra("designation",mTeacherDesignation.get(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTeacherName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView TeacherListName;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TeacherListName = itemView.findViewById(R.id.teacher_name);
            parentLayout = itemView.findViewById(R.id.teacher_element_layout);
        }
    }
}
