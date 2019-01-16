package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.GONE;

public class EditStudent extends Activity {

    private EditText editStudentName;
    private EditText editStudentRoll;
    //private EditText editStudentBatch;
    private EditText editStudentPhone;
    private EditText editStudentEmail;
    private EditText editStudentRegistrationNo;
    private Button editStudentSubmitButton;
    private DatabaseReference studentDatabaseRef;

    Student student;
    String key;
    String batch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_student);

        key = getIntent().getStringExtra("showKey");
        batch =getIntent().getStringExtra("batch");
        studentDatabaseRef = FirebaseDatabase.getInstance().getReference("student/"+ batch + "/" + key);

        editStudentName =(EditText) findViewById(R.id.editStudentName);
        //editStudentBatch = (EditText) findViewById(R.id.editStudentBatch);
        editStudentRoll = (EditText) findViewById(R.id.editStudentRoll);
        editStudentPhone = (EditText) findViewById(R.id.editStudentPhone);
        editStudentEmail = (EditText) findViewById(R.id.editStudentEmail);
        editStudentRegistrationNo = (EditText) findViewById(R.id.editStudentRegistrationNo);
        editStudentSubmitButton=(Button) findViewById(R.id.editStudentSubmitButton);
        editStudentSubmitButton.setVisibility(GONE);

        studentDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                student = dataSnapshot.getValue(Student.class);

                editStudentName.setText(student.getName());
                //editStudentBatch.setText(student.getBatch());
                editStudentRoll.setText(student.getRoll());
                editStudentEmail.setText(student.getEmail());
                editStudentPhone.setText(student.getPhone());
                editStudentRegistrationNo.setText(student.getRegistrationNo());
                editStudentSubmitButton.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editStudentSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditStudent();
            }
        });
    }

    private void goToEditStudent() {
        Student changedStudent = student;

        changedStudent.setName(editStudentName.getText().toString().trim());
        //changedStudent.setBatch(editStudentBatch.getText().toString().trim());
        changedStudent.setRoll(editStudentRoll.getText().toString().trim());
        changedStudent.setEmail(editStudentEmail.getText().toString().trim());
        changedStudent.setPhone(editStudentPhone.getText().toString().trim());
        changedStudent.setRegistrationNo(editStudentRegistrationNo.getText().toString().trim());

        studentDatabaseRef.setValue(changedStudent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(EditStudent.this,"Update Done",Toast.LENGTH_LONG).show();
            }
        });
        finish();
    }
}
