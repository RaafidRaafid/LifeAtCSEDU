package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class showSingleStudent extends Activity {

    private DatabaseReference showSingleStudentDatabaseRef;

    TextView studentName;
    TextView studentBatch;
    TextView studentRoll;
    TextView studentPhone;
    TextView studentEmail;
    TextView studentRegistrationNo;


    String key;
    String batch;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_student);

        studentName = findViewById(R.id.studentName_value);
        studentBatch = findViewById(R.id.studentBatch_value);
        studentRoll = findViewById(R.id.studentRoll_value);
        studentPhone = findViewById(R.id.studentPhone_value);
        studentEmail =findViewById(R.id.studentEmail_value);
        studentRegistrationNo = findViewById(R.id.studentRegistration_value);


        key = getIntent().getStringExtra("showKey");
        batch =getIntent().getStringExtra("batch");
        showSingleStudentDatabaseRef = FirebaseDatabase.getInstance().getReference("student/"+ batch + "/" + key);


        showSingleStudentDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                student = dataSnapshot.getValue(Student.class);
                studentName.setText(student.getName());
                studentBatch.setText(student.getBatch());
                studentRoll.setText(student.getRoll());
                studentPhone.setText(student.getPhone());
                studentEmail.setText(student.getEmail());
                studentRegistrationNo.setText(student.getRegistrationNo());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
