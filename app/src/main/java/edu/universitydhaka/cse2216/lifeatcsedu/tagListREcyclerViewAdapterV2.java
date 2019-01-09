package edu.universitydhaka.cse2216.lifeatcsedu;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class tagListREcyclerViewAdapterV2 extends RecyclerView.Adapter<tagListREcyclerViewAdapterV2.ViewHolder>{

    ArrayList<String> currentFilters = new ArrayList<>();
    ArrayList<String> tagNames = new ArrayList<>();
    String currentTitle,currentDescription;
    Context context;

    public tagListREcyclerViewAdapterV2(ArrayList<String> currentFilters, ArrayList<String> tagNames, String currentTitle, String currentDescription, Context context) {
        this.currentFilters = currentFilters;
        this.tagNames = tagNames;
        this.currentTitle = currentTitle;
        this.currentDescription = currentDescription;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listodtags,viewGroup,false);
        tagListREcyclerViewAdapterV2.ViewHolder viewHolder = new tagListREcyclerViewAdapterV2.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tagName.setText(tagNames.get(i));

        for(String now : currentFilters){
            if(now.equals(tagNames.get(i))) return;
        }

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentFilters.size()>=5) return;
                Intent intent = new Intent(context,addQuestion.class);
                currentFilters.add(tagNames.get(i));
                intent.putExtra("currentFilters",currentFilters);
                intent.putExtra("currentTitle",currentTitle);
                intent.putExtra("currentDescription",currentDescription);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tagName;
        LinearLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.tag_name);
            parentLayout = itemView.findViewById(R.id.resource_element_tag);
        }
    }
}
