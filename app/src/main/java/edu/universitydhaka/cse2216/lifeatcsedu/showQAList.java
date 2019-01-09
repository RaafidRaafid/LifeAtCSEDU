package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.apg.mobile.roundtextview.BadgeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class showQAList extends Activity {


    DatabaseReference QADatabaseRef;
    DatabaseReference tagDatabaseRef;
    DatabaseReference questionTagDatabaseRef;
    DatabaseReference getQuestionTagDatabaseRefChild;

    ArrayList<String> QAKeys = new ArrayList<>();
    ArrayList<String> QATitles = new ArrayList<>();
    ArrayList<String> QAAskers = new ArrayList<>();
    ArrayList<String> QATimes = new ArrayList<>();

    ArrayList<String> tagkeys = new ArrayList<>();
    ArrayList<String> currentFilters = new ArrayList<>();
    ArrayList<String> questionTags = new ArrayList<>();

    BadgeView[] t = new BadgeView[5];
    BadgeView addButton;

    String filter;

    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_qalist);

        filter = getIntent().getStringExtra("tagFilter");

        t[0] = findViewById(R.id.list_question_tag1);
        t[0].setVisibility(View.GONE);
        t[1] = findViewById(R.id.list_question_tag2);
        t[1].setVisibility(View.GONE);
        t[2] = findViewById(R.id.list_question_tag3);
        t[2].setVisibility(View.GONE);
        t[3] = findViewById(R.id.list_question_tag4);
        t[3].setVisibility(View.GONE);
        t[4] = findViewById(R.id.list_question_tag5);
        t[4].setVisibility(View.GONE);
        addButton = findViewById(R.id.addQButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(showQAList.this,addQuestion.class));
            }
        });

        QADatabaseRef = FirebaseDatabase.getInstance().getReference("questions");
        tagDatabaseRef = FirebaseDatabase.getInstance().getReference("tag_questions");
        questionTagDatabaseRef = FirebaseDatabase.getInstance().getReference("question_tags");

        currentFilters = getIntent().getStringArrayListExtra("currentFilters");
        if(currentFilters == null) currentFilters = new ArrayList<>();
        Collections.sort(currentFilters);
        index = currentFilters.size();
        for(int i=0;i<index;i++){
            t[i].setBadgeMainText(currentFilters.get(i));
            t[i].setVisibility(View.VISIBLE);
        }

        QADatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                QATitles.clear();
                QATimes.clear();
                QAAskers.clear();
                QAKeys.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    QAKeys.add(ds.getKey());
                    final Question question = ds.getValue(Question.class);

                    getQuestionTagDatabaseRefChild = questionTagDatabaseRef.child(ds.getKey());

                    getQuestionTagDatabaseRefChild.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            questionTags.clear();
                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                questionTags.add(ds.getValue(String.class));
                            }

                            Collections.sort(questionTags);

                            int i=0;
                            System.out.println("size dekhi " + currentFilters.size() + " " +questionTags.size());
                            for(String now : questionTags){
                                if(i==index) break;
                                if(now.equals(currentFilters.get(i))) i++;
                            }
                            System.out.println("number " + i);
                            if(i==index){
                                QATitles.add(question.getTitle());
                                QAAskers.add(question.getAsker());
                                QATimes.add(question.getTime());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                tagDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        tagkeys.clear();

                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            tagkeys.add(ds.getKey());
                        }

                        initRecyclerView();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void initRecyclerView(){
        RecyclerView QARecyclerView = findViewById(R.id.recyclerv_view_QA);
        RecyclerView tagRecyclerView = findViewById(R.id.recyclerv_view_tags);
        QAListRecyclerViewAdapter adapterQ = new QAListRecyclerViewAdapter(QAKeys,QATitles,QAAskers,QATimes,this);
        tagListREcyclerViewAdapter adapterT = new tagListREcyclerViewAdapter(tagkeys,currentFilters,this);
        QARecyclerView.setAdapter(adapterQ);
        tagRecyclerView.setAdapter(adapterT);
        QARecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tagRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
