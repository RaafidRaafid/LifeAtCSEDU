package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class showTeacherType extends Activity {

    RelativeLayout chairman,professor,assistantProfessor,associateProfessor,lecturer;


    String nowUer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_teacher_type);

        chairman = findViewById(R.id.chairman);
        professor = findViewById(R.id.Professor);
        assistantProfessor = findViewById(R.id.Assistant_professor);
        associateProfessor = findViewById(R.id.Associate_Professor);
        lecturer = findViewById(R.id.Lecturer);


        nowUer = getIntent().getStringExtra("current");

        chairman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(showTeacherType.this,showTeacherList.class);
                intent.putExtra("teacherType","chairman");
                intent.putExtra("current",nowUer);
                startActivity(intent);
            }
        });

        professor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showTeacherType.this,showTeacherList.class);
                intent.putExtra("teacherType","professor");
                intent.putExtra("current",nowUer);
                startActivity(intent);
            }
        });

        assistantProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showTeacherType.this,showTeacherList.class);
                intent.putExtra("teacherType","assistant_professor");
                intent.putExtra("current",nowUer);
                startActivity(intent);
            }
        });

        associateProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showTeacherType.this,showTeacherList.class);
                intent.putExtra("teacherType","associate_professor");
                intent.putExtra("current",nowUer);
                startActivity(intent);
            }
        });

        lecturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showTeacherType.this,showCourseList.class);
                intent.putExtra("teacherType","lecturer");
                intent.putExtra("current",nowUer);
                startActivity(intent);
            }
        });


    }
}
