package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class editStaff extends Activity {

    private EditText editStaffName;
    private EditText editStaffPhone;
    private EditText editStaffWork;
    private EditText editStaffEmail;
    private Button editStaffSubmitButton;
    private DatabaseReference staffDatabaseRef;
    Stuff staff;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_staff);

        editStaffName =(EditText) findViewById(R.id.editStaffName);
        editStaffPhone = (EditText) findViewById(R.id.editStaffPhone);
        editStaffWork = (EditText) findViewById(R.id.editStaffWork);
        editStaffEmail = (EditText) findViewById(R.id.editStaffEmail);
        editStaffSubmitButton=(Button) findViewById(R.id.editStaffSubmitButton);
        key = getIntent().getStringExtra("showKey");
        staffDatabaseRef = FirebaseDatabase.getInstance().getReference().child("stuff/"+ key);

        staffDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                staff = dataSnapshot.getValue(Stuff.class);

                editStaffName.setText(staff.getName());
                editStaffEmail.setText(staff.getEmail());
                editStaffPhone.setText(staff.getPhoneNo());
                editStaffWork.setText(staff.getWork());
                editStaffSubmitButton.setVisibility(View.VISIBLE);
                staffDatabaseRef.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editStaffSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditStaff();
            }
        });
    }

    private void goToEditStaff() {

        Stuff changedStaff = staff;
        changedStaff.setName(editStaffName.getText().toString().trim());
        changedStaff.setEmail(editStaffEmail.getText().toString().trim());
        changedStaff.setPhoneNo(editStaffPhone.getText().toString().trim());
        changedStaff.setWork(editStaffWork.getText().toString().trim());

        staffDatabaseRef.setValue(changedStaff).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(editStaff.this,"Update Done",Toast.LENGTH_LONG).show();
            }
        });
        Intent intent = new Intent(this, showSingleStuff.class);
        intent.putExtra("showKey",key);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
