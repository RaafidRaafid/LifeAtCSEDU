package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class showSemesterLIst extends Activity {

    RelativeLayout s11,s12,s21,s22,s31,s32,s41,s42;


    String nowUer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.semester_list);

        s11 = findViewById(R.id.sem1_1);
        s12 = findViewById(R.id.sem1_2);
        s21 = findViewById(R.id.sem2_1);
        s22 = findViewById(R.id.sem2_2);
        s31 = findViewById(R.id.sem3_1);
        s32 = findViewById(R.id.sem3_2);
        s41 = findViewById(R.id.sem4_1);
        s42 = findViewById(R.id.sem4_2);

        nowUer = getIntent().getStringExtra("current");

        s11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(showSemesterLIst.this,showCourseList.class);
                intent.putExtra("semester","11");
                intent.putExtra("current",nowUer);
                startActivity(intent);
            }
        });

        s12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showSemesterLIst.this,showCourseList.class);
                intent.putExtra("semester","12");
                intent.putExtra("current",nowUer);
                startActivity(intent);
            }
        });

        s21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showSemesterLIst.this,showCourseList.class);
                intent.putExtra("semester","21");
                intent.putExtra("current",nowUer);
                startActivity(intent);
            }
        });

        s22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showSemesterLIst.this,showCourseList.class);
                intent.putExtra("semester","22");
                intent.putExtra("current",nowUer);
                startActivity(intent);
            }
        });

        s31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showSemesterLIst.this,showCourseList.class);
                intent.putExtra("semester","31");
                intent.putExtra("current",nowUer);
                startActivity(intent);
            }
        });

        s32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showSemesterLIst.this,showCourseList.class);
                intent.putExtra("semester","32");
                intent.putExtra("current",nowUer);
                startActivity(intent);
            }
        });

        s41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showSemesterLIst.this,showCourseList.class);
                intent.putExtra("semester","41");
                intent.putExtra("current",nowUer);
                startActivity(intent);
            }
        });

        s42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showSemesterLIst.this,showCourseList.class);
                intent.putExtra("semester","42");
                intent.putExtra("current",nowUer);
                startActivity(intent);
            }
        });
    }
}
