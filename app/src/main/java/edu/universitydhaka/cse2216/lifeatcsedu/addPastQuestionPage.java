package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class addPastQuestionPage extends Activity {

    final static int PICK_FILE_NUM = 619;

    EditText add_past_question_title;
    TextView add_past_question_link;
    Button add_past_question_done,add_past_link_button;

    String courseCode,key;
    Uri fileUri;
    String dlLink;

    private DatabaseReference pastQuestionsDatabaseRef;
    private StorageReference pastQuestionStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_past_question);

        add_past_question_title = findViewById(R.id.add_past_question_title);
        add_past_question_link = findViewById(R.id.add_past_question_link);
        add_past_question_done = findViewById(R.id.add_past_question_done);
        add_past_link_button = findViewById(R.id.past_questions_link_button);

        courseCode = getIntent().getStringExtra("courseCode");

        pastQuestionsDatabaseRef = FirebaseDatabase.getInstance().getReference("pastquestions/"+courseCode);
        pastQuestionStorage = FirebaseStorage.getInstance().getReference("pastquestions");

        add_past_link_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePastQuestion();
            }
        });

        add_past_question_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String tempTitle = add_past_question_title.getText().toString().trim();
                String tempLink = add_past_question_link.getText().toString().trim();
                if(add_past_question_title.getText()==null || add_past_question_link.getText().toString().equals("File not uploaded.")){
                    Toast.makeText(addPastQuestionPage.this, "Fulfil the fields properly", Toast.LENGTH_SHORT).show();
                }else{
                    if(fileUri != null){
                        key = pastQuestionsDatabaseRef.push().getKey();
                        final StorageReference fileReference = pastQuestionStorage.child(key);
                        StorageTask uploadTask = fileReference.putFile(fileUri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                dlLink = uri.toString();

                                                //System.out.println(key + "   @@@@@@@@@@@@@@@   " + dlLink);
                                                pastQuestionsDatabaseRef.child(key).setValue(new PastQuestion(tempTitle,dlLink,courseCode))
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(addPastQuestionPage.this, "PastQuestion Added", Toast.LENGTH_SHORT).show();
                                                                finish();
                                                            }
                                                        });
                                            }
                                        });
                                    }
                                });
                    }

                }
            }
        });
    }

    public void choosePastQuestion(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_FILE_NUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_FILE_NUM && resultCode == RESULT_OK && data != null && data.getData()!=null){
            fileUri = data.getData();
            add_past_question_link.setText("File Uploaded");
        }
    }
}
