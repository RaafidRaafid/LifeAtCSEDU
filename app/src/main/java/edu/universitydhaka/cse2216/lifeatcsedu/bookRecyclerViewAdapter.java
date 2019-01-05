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

public class bookRecyclerViewAdapter extends RecyclerView.Adapter<bookRecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> bookTitles = new ArrayList<>();
    private ArrayList<String> bookLinks = new ArrayList<>();
    private Context context;
    String courseCode;

    public bookRecyclerViewAdapter(Context context,ArrayList<String> bookTitles, ArrayList<String> bookLinks) {
        this.bookTitles = bookTitles;
        this.bookLinks = bookLinks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listofbooks,viewGroup,false);
        bookRecyclerViewAdapter.ViewHolder viewHolder = new bookRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        viewHolder.bookTitle.setText(bookTitles.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To Single User Page
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(bookLinks.get(i)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView bookTitle;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.book_title);
            parentLayout = itemView.findViewById(R.id.book_element_layout);
        }
    }
}
