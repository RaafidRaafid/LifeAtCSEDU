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

public class addResourcePage extends Activity {

    EditText add_resource_title,add_resource_link;
    Button add_resource_done;

    String courseCode,key;
    private DatabaseReference resourcesDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_resource_page);

        add_resource_title = findViewById(R.id.add_resource_title);
        add_resource_link = findViewById(R.id.add_resource_link);
        add_resource_done = findViewById(R.id.add_resource_done);

        courseCode = getIntent().getStringExtra("courseCode");

        resourcesDatabaseRef = FirebaseDatabase.getInstance().getReference("resources/"+courseCode);

        add_resource_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tempTitle = add_resource_title.getText().toString().trim();
                String tempLink = add_resource_link.getText().toString().trim();
                if(add_resource_title.getText()==null || add_resource_link.getText()==null){
                    Toast.makeText(addResourcePage.this, "Fulfil the fields properly", Toast.LENGTH_SHORT).show();
                }else{
                    key = resourcesDatabaseRef.push().getKey();
                    //System.out.println(key + "   @@@@@@@@@@@@@@@   " + courseCode);
                    resourcesDatabaseRef.child(key).setValue(new Resource(tempTitle,tempLink,courseCode))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(addResourcePage.this, "Resource Added", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }
            }
        });
    }
}
