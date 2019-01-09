package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apg.mobile.roundtextview.BadgeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class addQuestion extends Activity {

    EditText title,description,addTagField;
    BadgeView[] t = new BadgeView[5];
    Button doneButton,addTagButton;

    ArrayList<String> currentFilters = new ArrayList<>();
    ArrayList<String> tagNames = new ArrayList<>();

    String currentTitle,currentDescription,key;
    int index,tempo;

    DatabaseReference addQuestionTQDatabaseRef,addQuestionQDatabaseRef,addQuestionQTDatabaseRef,addQuestionUQDatabaseRef;
    FirebaseAuth userAuth;
    FirebaseUser userUser;
    String userUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_question);

        title = findViewById(R.id.add_question_title);
        description = findViewById(R.id.add_question_description);
        doneButton = findViewById(R.id.add_question_done_button);
        addTagButton = findViewById(R.id.add_tag_add_button);
        addTagField = findViewById(R.id.add_tag_add_text);

        t[0] = findViewById(R.id.add_question_tag1);
        t[0].setVisibility(View.GONE);
        t[1] = findViewById(R.id.add_question_tag2);
        t[1].setVisibility(View.GONE);
        t[2] = findViewById(R.id.add_question_tag3);
        t[2].setVisibility(View.GONE);
        t[3] = findViewById(R.id.add_question_tag4);
        t[3].setVisibility(View.GONE);
        t[4] = findViewById(R.id.add_question_tag5);
        t[4].setVisibility(View.GONE);

        currentTitle = getIntent().getStringExtra("currentTitle");
        currentDescription = getIntent().getStringExtra("currentDescription");

        if(currentTitle==null) title.setText("");
        else title.setText(currentTitle);
        if(currentDescription==null) description.setText("");
        else description.setText(currentDescription);

        userAuth = FirebaseAuth.getInstance();
        userUser = userAuth.getCurrentUser();
        userUserEmail = userUser.getEmail();

        currentFilters = getIntent().getStringArrayListExtra("currentFilters");
        if(currentFilters == null){
            currentFilters = new ArrayList<>();
        }
        if(currentFilters != null) index = currentFilters.size();
        else index =0;
        for(int i=0;i<index;i++){
            t[i].setBadgeMainText(currentFilters.get(i));
            t[i].setVisibility(View.VISIBLE);
        }

        addQuestionTQDatabaseRef = FirebaseDatabase.getInstance().getReference("tag_questions");
        addQuestionQDatabaseRef = FirebaseDatabase.getInstance().getReference("questions");
        addQuestionQTDatabaseRef = FirebaseDatabase.getInstance().getReference("question_tags");
        addQuestionUQDatabaseRef = FirebaseDatabase.getInstance().getReference("user_questions");

        addQuestionTQDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tagNames.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    tagNames.add(ds.getKey());
                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().trim().equals("") || description.getText().toString().trim().equals("") || currentFilters.size()==0){
                    Toast.makeText(addQuestion.this, "Hoy naikka", Toast.LENGTH_SHORT).show();
                    return;
                }
                key = addQuestionQDatabaseRef.push().getKey();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                String currentDateAndTime = sdf.format(new Date());
                addQuestionQDatabaseRef.child(key).setValue(new Question(title.getText().toString().trim(),
                        userUserEmail.replace('.','&'),
                        currentDateAndTime,
                        description.getText().toString().trim()));

                addQuestionQTDatabaseRef.child(key).setValue(currentFilters);
                for(String tag : currentFilters){
                    addQuestionTQDatabaseRef.child(tag).setValue(true);
                }
                addQuestionUQDatabaseRef.child(userUserEmail.replace('.','&')).child(key).setValue(true);
                finish();
            }
        });

        addTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addTagField.getText().toString().trim().equals("")) return;

                if(currentFilters!=null && currentFilters.size()>=5) return;
                Intent intent = new Intent(addQuestion.this,addQuestion.class);
                currentFilters.add(addTagField.getText().toString().trim());
                intent.putExtra("currentFilters",currentFilters);
                intent.putExtra("currentTitle",title.getText().toString().trim());
                intent.putExtra("currentDescription",description.getText().toString().trim());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.add_question_recycler_view);
        tagListREcyclerViewAdapterV2 adapter = new tagListREcyclerViewAdapterV2(currentFilters,tagNames,title.getText().toString().trim(),description.getText().toString().trim(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
