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

public class resourceRecyclerViewAdapter extends RecyclerView.Adapter<resourceRecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> resourceTitles = new ArrayList<>();
    private ArrayList<String> resourceLinks = new ArrayList<>();
    private Context context;
    String courseCode;

    public resourceRecyclerViewAdapter(Context context,ArrayList<String> resourceTitles, ArrayList<String> resourceLinks) {
        this.resourceTitles = resourceTitles;
        this.resourceLinks = resourceLinks;
        this.context = context;
    }


    @NonNull
    @Override
    public resourceRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listofresources,viewGroup,false);
        resourceRecyclerViewAdapter.ViewHolder viewHolder = new resourceRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull resourceRecyclerViewAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.resourceTitle.setText(resourceTitles.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To Single User Page
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(resourceLinks.get(i)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resourceTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView resourceTitle;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            resourceTitle = itemView.findViewById(R.id.resource_title);
            parentLayout = itemView.findViewById(R.id.resource_element_layout);
        }
    }
}
