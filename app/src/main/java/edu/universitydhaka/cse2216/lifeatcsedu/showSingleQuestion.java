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

    BadgeView[] t = new BadgeView[5];
    TextView title,description;
    EditText commentBox;
    Button doneComment;

    DatabaseReference singleQARef,commentsRef,questionTagRef;
    FirebaseAuth userAuth;
    FirebaseUser userUser;
    String userUserEmail;

    String key,currentUser;
    ArrayList<String> commentUsers = new ArrayList<>();
    ArrayList<String> commentDescriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_single_question);

        t[0] = findViewById(R.id.single_question_tag1);
        t[0].setVisibility(View.GONE);
        t[1] = findViewById(R.id.single_question_tag2);
        t[1].setVisibility(View.GONE);
        t[2] = findViewById(R.id.single_question_tag3);
        t[2].setVisibility(View.GONE);
        t[3] = findViewById(R.id.single_question_tag4);
        t[3].setVisibility(View.GONE);
        t[4] = findViewById(R.id.single_question_tag5);
        t[4].setVisibility(View.GONE);

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

        commentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentUsers.clear();
                commentDescriptions.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Comment comment = ds.getValue(Comment.class);
                    commentUsers.add(comment.getUser());
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
                    t[i].setBadgeMainText(ds.getValue(String.class));
                    t[i].setVisibility(View.VISIBLE);

                    i++;
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

        doneComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = commentsRef.push().getKey();
                commentsRef.child(temp).setValue(new Comment(userUserEmail.replace('.','&'),commentBox.getText().toString().trim()));

                commentBox.setText("");
            }
        });
    }

    public void initRecyclerVIew(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view_comments);
        commentRecyclerViewAdapter adapter = new commentRecyclerViewAdapter(commentUsers,commentDescriptions,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
