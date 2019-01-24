package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.GONE;

public class editNotice extends Activity {

    private DatabaseReference editNoticeDatabaseRef;
    String key;
    EditText editNoticeTitle;
    EditText editNoticeDescription;
    Button editNoticeSubmitButton;

    Notice notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_notice);

        key = getIntent().getStringExtra("showKey");
        editNoticeDatabaseRef = FirebaseDatabase.getInstance().getReference("notice/"+key);

        editNoticeTitle = findViewById(R.id.editNoticeTitle);
        editNoticeDescription = findViewById(R.id.editNoticeDescription);
        editNoticeSubmitButton = findViewById(R.id.editNoticeSubmitButton);
        editNoticeSubmitButton.setVisibility(GONE);

        editNoticeDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notice = dataSnapshot.getValue(Notice.class);

                editNoticeTitle.setText(notice.getTitle());
                editNoticeDescription.setText(notice.getDescription());
                editNoticeSubmitButton.setVisibility(View.VISIBLE);

                editNoticeDatabaseRef.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editNoticeSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditNotice();
            }
        });
    }

    private void goToEditNotice() {
        Notice changedNotice = notice;

        changedNotice.setTitle(editNoticeTitle.getText().toString().trim());
        changedNotice.setDescription(editNoticeDescription.getText().toString().trim());

        editNoticeDatabaseRef.setValue(changedNotice).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(editNotice.this,"Update Done",Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = new Intent(this, showSingleNotice.class);
        intent.putExtra("showKey",key);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
