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

public class addBooksPage extends Activity {

    EditText add_book_title,add_book_link;
    Button add_book_done;

    String courseCode,key;
    private DatabaseReference bookDatabaseRef;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_books_page);

        add_book_title = findViewById(R.id.add_book_title);
        add_book_link = findViewById(R.id.add_book_link);
        add_book_done = findViewById(R.id.add_book_done);

        courseCode = getIntent().getStringExtra("courseCode");
        System.out.println(courseCode + " *Before going in*");

        bookDatabaseRef = FirebaseDatabase.getInstance().getReference("books/"+courseCode);

        add_book_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tempTitle = add_book_title.getText().toString().trim();
                String tempLink = add_book_link.getText().toString().trim();
                if(add_book_title.getText()==null || add_book_link.getText()==null){
                    Toast.makeText(addBooksPage.this, "Fulfil the fields properly", Toast.LENGTH_SHORT).show();
                }else{
                    key = bookDatabaseRef.push().getKey();
                    System.out.println(key + "   @@@@@@@@@@@@@@@   " + courseCode);
                    bookDatabaseRef.child(key).setValue(new Book(tempTitle,tempLink,courseCode))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(addBooksPage.this, "Book Added", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }
            }
        });
    }
}
