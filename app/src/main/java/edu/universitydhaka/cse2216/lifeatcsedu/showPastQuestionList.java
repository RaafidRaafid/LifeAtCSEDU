package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class showPastQuestionList extends Activity {

    private DatabaseReference pastQuestionsDatabaseRef;
    private DatabaseReference userDatabase;
    private FirebaseAuth userAuth;
    private FirebaseUser userAuthUser;

    private ArrayList<String> pastQuestionsTitles = new ArrayList<>();
    private ArrayList<String> pastQuestionsLinks = new ArrayList<>();

    String nowUser,courseCode;
    User user;

    Button addPastQuestionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_past_question_list);

        courseCode = getIntent().getStringExtra("courseCode");
        pastQuestionsDatabaseRef = FirebaseDatabase.getInstance().getReference("pastquestions/"+courseCode);
        addPastQuestionButton=findViewById(R.id.addPastQuestion);
        addPastQuestionButton.setVisibility(View.GONE);
        userAuth = FirebaseAuth.getInstance();
        userAuthUser = userAuth.getCurrentUser();
        nowUser= userAuthUser.getEmail();
        userDatabase=FirebaseDatabase.getInstance().getReference("users/"+nowUser.replace('.','&'));

        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                if(user.getIsModerator().equals("true")){
                    addPastQuestionButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addPastQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(showPastQuestionList.this,addPastQuestionPage.class);
                intent.putExtra("courseCode",courseCode);
                startActivity(intent);
            }

        });

        pastQuestionsDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                pastQuestionsTitles.clear();
                pastQuestionsLinks.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    PastQuestion pastQuestion = ds.getValue(PastQuestion.class);
                    System.out.println("kop mama kop " + pastQuestion.getTitle() + " " + pastQuestion.getLink());
                    if(pastQuestion.getCourseCode().equals(courseCode)){
                        pastQuestionsTitles.add(pastQuestion.getTitle());
                        pastQuestionsLinks.add(pastQuestion.getLink());
                    }
                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view_pastQuestions);
        pastQuestionRecyclerViewAdapter adapter = new pastQuestionRecyclerViewAdapter(this,pastQuestionsTitles,pastQuestionsLinks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
