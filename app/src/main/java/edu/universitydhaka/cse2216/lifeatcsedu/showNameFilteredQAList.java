package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.logging.Filter;

public class showNameFilteredQAList extends Activity {

    String nameFilter;

    DatabaseReference QADatabase;

    ArrayList<String> QAKeys = new ArrayList<>();
    ArrayList<String> QATitles = new ArrayList<>();
    ArrayList<String> QAAskers = new ArrayList<>();
    ArrayList<String> QATimes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_name_filtered_qalist);

        nameFilter = getIntent().getStringExtra("nameFilter");

        QADatabase = FirebaseDatabase.getInstance().getReference("questions");

        QADatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    Question question = ds.getValue(Question.class);

                    if(question.getTitle().toLowerCase().contains(nameFilter.toLowerCase())){
                        QAAskers.add(question.getAsker());
                        QAKeys.add(ds.getKey());
                        QATitles.add(question.getTitle());
                        QATimes.add(question.getTime());
                    }
                }

                iniRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void iniRecyclerView(){

        RecyclerView FilteredQA = findViewById(R.id.recyclerv_view_Filtered_QA);
        QAListRecyclerViewAdapter adapterQ = new QAListRecyclerViewAdapter(QAKeys,QATitles,QAAskers,QATimes,this);
        FilteredQA.setAdapter(adapterQ);
        FilteredQA.setLayoutManager(new LinearLayoutManager(this));
    }
}
