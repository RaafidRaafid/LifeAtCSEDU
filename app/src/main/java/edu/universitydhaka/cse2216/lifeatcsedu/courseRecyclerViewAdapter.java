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

public class courseRecyclerViewAdapter extends RecyclerView.Adapter<courseRecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> titleNames = new ArrayList<>();
    private ArrayList<String> courseCodes = new ArrayList<>();
    private Context context;

    public courseRecyclerViewAdapter(Context context,ArrayList<String> titleNames, ArrayList<String> courseCodes) {
        this.titleNames = titleNames;
        this.courseCodes = courseCodes;
        this.context = context;
    }

    @NonNull
    @Override
    public courseRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listofcourses,viewGroup,false);
        courseRecyclerViewAdapter.ViewHolder viewHolder = new courseRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull courseRecyclerViewAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.courseTitle.setText(titleNames.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To Single User Page
                Intent intent = new Intent(context,showSingleCourse.class);
                intent.putExtra("coursecode",courseCodes.get(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseCodes.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView courseTitle;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.course_title);
            parentLayout = itemView.findViewById(R.id.course_element_layout);
        }
    }
}
