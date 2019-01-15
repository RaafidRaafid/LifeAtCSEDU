package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class PeoplePage extends Activity {

    ImageButton toStudent;
    ImageButton toStuff;
    ImageButton toTeacher;

    String nowUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_page);

        toStudent = findViewById(R.id.toStudent);
        toStuff = findViewById(R.id.toStaff);
        toTeacher = findViewById(R.id.toTeacher);

        nowUser = getIntent().getStringExtra("current");

        toStuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeToStuff(v);
            }
        });
        toTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeToTeacher(v);
            }
        });
        toStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeToStudent(v);
            }
        });

    }

    private void takeToStudent(View v) {
        Intent intent = new Intent(this,showBatchList.class);
        intent.putExtra("current",nowUser);
        startActivity(intent);
    }

    private void takeToTeacher(View v) {
        Intent intent = new Intent(this,showTeacherType.class);
        intent.putExtra("current",nowUser);
        startActivity(intent);
    }


    public void takeToStuff(View view){
        Intent intent = new Intent(this,stuffShowPage.class);
        intent.putExtra("current",nowUser);
        startActivity(intent);
    }
}
