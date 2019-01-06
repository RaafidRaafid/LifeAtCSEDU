package edu.universitydhaka.cse2216.lifeatcsedu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.apg.mobile.roundtextview.BadgeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class showBooksList extends Activity {

    private DatabaseReference booksDatabaseRef;
    private DatabaseReference userDatabase;
    private FirebaseAuth userAuth;
    private FirebaseUser userAuthUser;

    private ArrayList<String> bookTitles = new ArrayList<>();
    private ArrayList<String> bookLinks = new ArrayList<>();

    String nowUser,courseCode;
    User user;

    BadgeView addBookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_books_list);

        courseCode = getIntent().getStringExtra("courseCode");
        booksDatabaseRef = FirebaseDatabase.getInstance().getReference("books/"+courseCode);
        addBookButton=findViewById(R.id.addBook);
        addBookButton.setVisibility(View.GONE);
        userAuth = FirebaseAuth.getInstance();
        userAuthUser = userAuth.getCurrentUser();
        nowUser= userAuthUser.getEmail();
        userDatabase=FirebaseDatabase.getInstance().getReference("users/"+nowUser.replace('.','&'));

        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                if(user.getIsModerator().equals("true")){
                    addBookButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(showBooksList.this,addBooksPage.class);
                intent.putExtra("courseCode",courseCode);
                startActivity(intent);
            }

        });

        booksDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                bookTitles.clear();
                bookLinks.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Book book = ds.getValue(Book.class);
                    if(book.getCourseCode().equals(courseCode)){
                        bookTitles.add(book.getTitle());
                        bookLinks.add(book.getLink());
                    }
                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view_books);
        bookRecyclerViewAdapter adapter = new bookRecyclerViewAdapter(this,bookTitles,bookLinks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
