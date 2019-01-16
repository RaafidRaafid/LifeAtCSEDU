package edu.universitydhaka.cse2216.lifeatcsedu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class commentRecyclerViewAdapter extends RecyclerView.Adapter<commentRecyclerViewAdapter.ViewHolder>{

    ArrayList<String> commentUserName = new ArrayList<>();
    ArrayList<String> commentDescriptions = new ArrayList<>();
    Context context;

    public commentRecyclerViewAdapter(ArrayList<String> commentUsers, ArrayList<String> commentDescriptions, Context context) {
        this.commentUserName = commentUsers;
        this.commentDescriptions = commentDescriptions;
        this.context = context;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listof_comments,viewGroup,false);
        commentRecyclerViewAdapter.ViewHolder viewHolder = new commentRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //System.out.println(commentUserName.get(i) + " " + commentDescriptions.get(i));

        viewHolder.cuser.setText(commentUserName.get(i));
        viewHolder.cdesc.setText(commentDescriptions.get(i));
    }

    @Override
    public int getItemCount() {
        return commentDescriptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView cuser, cdesc;
        LinearLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cuser = itemView.findViewById(R.id.comment_user);
            cdesc = itemView.findViewById(R.id.comment_description);
        }
    }
}
