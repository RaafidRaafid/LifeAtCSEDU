package edu.universitydhaka.cse2216.lifeatcsedu;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.apg.mobile.roundtextview.BadgeView;

import java.util.ArrayList;

public class horTagRecyclerViewAdapterV2 extends RecyclerView.Adapter<horTagRecyclerViewAdapterV2.ViewHolder>{

    ArrayList<String> currentFilters = new ArrayList<>();
    String currentTitle;
    String currentDescription;
    Context context;

    public horTagRecyclerViewAdapterV2(ArrayList<String> currentFilters, String currentTitle, String currentDescription, Context context) {
        this.currentFilters = currentFilters;
        this.currentTitle = currentTitle;
        this.currentDescription = currentDescription;
        this.context = context;

        System.out.println("ashlo " + currentTitle + "  " + currentDescription);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listof_hortags,viewGroup,false);
        horTagRecyclerViewAdapterV2.ViewHolder viewHolder = new horTagRecyclerViewAdapterV2.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.tag.setBadgeMainText(currentFilters.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFilters.remove(i);

                Intent intent = new Intent(context,addQuestion.class);
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
        return currentFilters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        BadgeView tag;
        LinearLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.hor_tag_name);
            parentLayout = itemView.findViewById(R.id.resource_element_layout_hor_tag);
        }
    }
}
