package edu.universitydhaka.cse2216.lifeatcsedu;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class commentRecyclerViewAdapter extends RecyclerView.Adapter<commentRecyclerViewAdapter.ViewHolder>{

    ArrayList<String> commentUserName = new ArrayList<>();
    ArrayList<String> commentDescriptions = new ArrayList<>();
    ArrayList<String> commentKeys = new ArrayList<>();
    ArrayList<String> commentUsers = new ArrayList<>();
    Context context;

    FirebaseAuth userAuth;

    public commentRecyclerViewAdapter(ArrayList<String> commentUserName, ArrayList<String> commentDescriptions, ArrayList<String> commentKeys,ArrayList<String> commentUsers,Context context) {
        this.commentUserName = commentUserName;
        this.commentDescriptions = commentDescriptions;
        this.commentKeys = commentKeys;
        this.commentUsers = commentUsers;
        this.context = context;

        userAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listof_comments,viewGroup,false);
        commentRecyclerViewAdapter.ViewHolder viewHolder = new commentRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        //System.out.println(commentUserName.get(i) + " " + commentDescriptions.get(i));

        viewHolder.cuser.setText(commentUserName.get(i));
        viewHolder.cdesc.setText(commentDescriptions.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userAuth.getCurrentUser().getEmail().replace('.','&').equals(commentUsers.get(i))){
                    Intent intent = new Intent(context,editComment.class);
                    intent.putExtra("path",commentKeys.get(i));
                    intent.putExtra("desc",commentDescriptions.get(i));
                    context.startActivity(intent);
                }
                else{
                    Toast.makeText(context, "Not your comment.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            parentLayout = itemView.findViewById(R.id.resource_element_layout_comment);
        }
    }
}
