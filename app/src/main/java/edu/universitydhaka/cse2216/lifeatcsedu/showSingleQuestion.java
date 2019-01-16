package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apg.mobile.roundtextview.BadgeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class showSingleQuestion extends Activity {

    TextView title,description;
    EditText commentBox;
    Button doneComment;

    DatabaseReference singleQARef,commentsRef,questionTagRef,userDatabase;
    FirebaseAuth userAuth;
    FirebaseUser userUser;
    String userUserEmail;
    String name;

    String key,currentUser;
    ArrayList<String> commentUsers = new ArrayList<>();
    ArrayList<String> commentUsername = new ArrayList<>();
    ArrayList<String> commentDescriptions = new ArrayList<>();
    ArrayList<String> currentFilters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_single_question);

        title = findViewById(R.id.single_question_title);
        description = findViewById(R.id.single_question_description);
        doneComment = findViewById(R.id.commentBoxDone);
        commentBox = findViewById(R.id.commentBox);

        key = getIntent().getStringExtra("qkey");
        singleQARef = FirebaseDatabase.getInstance().getReference("questions/" + key);
        commentsRef = FirebaseDatabase.getInstance().getReference("question_comments/" + key);
        questionTagRef = FirebaseDatabase.getInstance().getReference("question_tags/" + key);
        userAuth = FirebaseAuth.getInstance();
        userUser = userAuth.getCurrentUser();
        userUserEmail = userUser.getEmail();
        userDatabase = FirebaseDatabase.getInstance().getReference("users/" + userUserEmail.replace('.','&'));

        userDatabase.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue(String.class);

                doneComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String temp = commentsRef.push().getKey();

                        commentsRef.child(temp).setValue(new Comment(userUserEmail.replace('.','&'),name,commentBox.getText().toString().trim()));

                        commentBox.setText("");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        commentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentUsers.clear();
                commentUsername.clear();
                commentDescriptions.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Comment comment = ds.getValue(Comment.class);
                    commentUsers.add(comment.getUser());
                    commentUsername.add(comment.getName());
                    commentDescriptions.add(comment.getDescription());
                }

                initRecyclerVIew();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        questionTagRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    currentFilters.add(ds.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        singleQARef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Question question = dataSnapshot.getValue(Question.class);
                title.setText(question.getTitle());
                description.setText(question.getDesc());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void initRecyclerVIew(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_comments);
        RecyclerView horRecyclerView = findViewById(R.id.hor_tag_recycler_view_single);
        commentRecyclerViewAdapter adapter = new commentRecyclerViewAdapter(commentUsername,commentDescriptions,this);
        horTagSingleRecyclerViewAdapter adapterHTS = new horTagSingleRecyclerViewAdapter(currentFilters,this);
        recyclerView.setAdapter(adapter);
        horRecyclerView.setAdapter(adapterHTS);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        horRecyclerView.setLayoutManager(layoutManager);
    }
}
