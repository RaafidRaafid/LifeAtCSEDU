package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

    ArrayList<String> userFiltered = new ArrayList<>();

    //BadgeView[] t = new BadgeView[5];
    BadgeView addButton;

    String filter;

    int index,userIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_qalist);

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

        userFiltered = getIntent().getStringArrayListExtra("userFiltered");
        if(userFiltered != null) userIndex =userFiltered.size();

        QADatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                QATitles.clear();
                QATimes.clear();
                QAAskers.clear();
                QAKeys.clear();

                int ui = 0;

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(userFiltered!=null){
                        if(ui == userIndex) continue;

                        if(userFiltered.get(ui).equals(ds.getKey())) ui++;
                        else continue;
                    }
                    final String TheKey = ds.getKey();
                    final Question question = ds.getValue(Question.class);

                    getQuestionTagDatabaseRefChild = questionTagDatabaseRef.child(ds.getKey());

                    getQuestionTagDatabaseRefChild.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            questionTags.clear();
                            for(DataSnapshot dds : dataSnapshot.getChildren()){
                                questionTags.add(dds.getValue(String.class));
                            }

                            Collections.sort(questionTags);

                            int i=0;
                            for(String now : questionTags){
                                if(i==index) break;
                                if(now.equals(currentFilters.get(i))) i++;
                            }
                            if(i==index){
                                QAAskers.add(question.getAsker());
                                QAKeys.add(TheKey);
                                QATitles.add(question.getTitle());
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
        System.out.println(QAAskers.size() + " " + QATitles.size());

        RecyclerView QARecyclerView = findViewById(R.id.recyclerv_view_QA);
        RecyclerView tagRecyclerView = findViewById(R.id.recyclerv_view_tags);
        RecyclerView horTagRecyclerView = findViewById(R.id.hor_tag_recycler_view);
        QAListRecyclerViewAdapter adapterQ = new QAListRecyclerViewAdapter(QAKeys,QATitles,QAAskers,QATimes,this);
        tagListREcyclerViewAdapter adapterT = new tagListREcyclerViewAdapter(tagkeys,currentFilters,this);
        horTagRecyclerViewAdapter adapaterHT = new horTagRecyclerViewAdapter(currentFilters,this);
        QARecyclerView.setAdapter(adapterQ);
        tagRecyclerView.setAdapter(adapterT);
        horTagRecyclerView.setAdapter(adapaterHT);
        QARecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tagRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        horTagRecyclerView.setLayoutManager(layoutManager);
    }
}
