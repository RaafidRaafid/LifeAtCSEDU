package edu.universitydhaka.cse2216.lifeatcsedu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class pastQuestionRecyclerViewAdapter extends RecyclerView.Adapter<pastQuestionRecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> pastQuestionTitles = new ArrayList<>();
    private ArrayList<String> pastQuestionLinks = new ArrayList<>();
    private Context context;
    String courseCode;

    public pastQuestionRecyclerViewAdapter(Context context,ArrayList<String> pastQuestionTitles, ArrayList<String> pastQuestionLinks) {
        this.pastQuestionTitles = pastQuestionTitles;
        this.pastQuestionLinks = pastQuestionLinks;
        this.context = context;
    }

    @NonNull
    @Override
    public pastQuestionRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listofpastquestions,viewGroup,false);
        pastQuestionRecyclerViewAdapter.ViewHolder viewHolder = new pastQuestionRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull pastQuestionRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.pastQuestionTitleX.setText(pastQuestionTitles.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To Single User Page
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(pastQuestionLinks.get(i)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pastQuestionTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView pastQuestionTitleX;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pastQuestionTitleX = itemView.findViewById(R.id.past_question_title_new);
            parentLayout = itemView.findViewById(R.id.past_question_element_layout);
        }
    }
}
