package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editComment extends Activity {

    String path;
    String comment;

    EditText descBox;
    Button done,delete;

    DatabaseReference commentDeatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_comment);

        path = getIntent().getStringExtra("path");
        comment = getIntent().getStringExtra("desc");

        commentDeatabase = FirebaseDatabase.getInstance().getReference(path);
        descBox = findViewById(R.id.editCommentBox);
        done = findViewById(R.id.editCommentDone);
        delete = findViewById(R.id.editCommentDelete);
        descBox.setText(comment);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentDeatabase.child("description").setValue(descBox.getText().toString().trim())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                finish();
                            }
                        });
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentDeatabase.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                    }
                });
            }
        });
    }
}
