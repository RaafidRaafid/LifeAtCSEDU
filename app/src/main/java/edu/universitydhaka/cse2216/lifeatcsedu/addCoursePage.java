package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addCoursePage extends Activity {

    EditText add_course_title,add_course_code;
    Button add_course_done;

    private DatabaseReference courseDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course_page);

        add_course_title = findViewById(R.id.add_course_title);
        add_course_code = findViewById(R.id.add_course_code);
        add_course_done = findViewById(R.id.add_course_done);

        courseDatabaseRef = FirebaseDatabase.getInstance().getReference("courses");

        add_course_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add_course_title.getText()==null || add_course_code.getText()==null){
                    Toast.makeText(addCoursePage.this, "Fulfil the fields", Toast.LENGTH_SHORT).show();
                }else{
                    courseDatabaseRef.child(add_course_code.getText().toString().trim()).setValue(new Course(add_course_title.getText().toString().trim(),add_course_code.getText().toString().trim()))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(addCoursePage.this, "Course Added", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }
            }
        });
    }
}
