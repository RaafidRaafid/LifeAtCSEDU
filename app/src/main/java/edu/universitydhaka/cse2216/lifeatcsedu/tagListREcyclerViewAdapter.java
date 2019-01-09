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

public class tagListREcyclerViewAdapter extends RecyclerView.Adapter<tagListREcyclerViewAdapter.ViewHolder>{

    ArrayList<String> tagkeys = new ArrayList<>();
    ArrayList<String> currentFilters = new ArrayList<>();
    Context context;

    public tagListREcyclerViewAdapter(ArrayList<String> tagkeys, ArrayList<String> currentFilters,Context context) {
        this.tagkeys = tagkeys;
        this.context = context;
        this.currentFilters = currentFilters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listodtags,viewGroup,false);
        tagListREcyclerViewAdapter.ViewHolder viewHolder = new tagListREcyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.tagName.setText(tagkeys.get(i));

        if(currentFilters == null) currentFilters = new ArrayList<>();

        for(String now : currentFilters){
            if(now.equals(tagkeys.get(i))) return;
        }

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,showQAList.class);
                currentFilters.add(tagkeys.get(i));
                intent.putExtra("currentFilters",currentFilters);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagkeys.size();
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
