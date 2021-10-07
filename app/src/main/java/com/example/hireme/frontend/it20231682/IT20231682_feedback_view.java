package com.example.hireme.frontend.it20231682;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.hireme.models.IT20231682_feedback_model;
import com.example.hireme.util.it20231682.IT20231682_feedback_viewAdapter;
import com.example.hireme.R;
import com.example.hireme.frontend.it20224370.IT20224370_Session_Management;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class IT20231682_feedback_view extends AppCompatActivity {

    //Initialize Variables
    RecyclerView recyclerView;
    IT20231682_feedback_viewAdapter it20231682_feedback_viewAdapter;
    String userEmail,userName,userImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_it20231682_feedback_view);

        //Assign variable
        recyclerView = findViewById(R.id.feedrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //get the user details (mail,username) from the session
        IT20224370_Session_Management session;
        session = new IT20224370_Session_Management(IT20231682_feedback_view.this);

         userEmail = session.getusename();
         userName = session.getName();
         userImg = session.getImg();




        //database connection to get data from database
        FirebaseRecyclerOptions<IT20231682_feedback_model> options =
                new FirebaseRecyclerOptions.Builder<IT20231682_feedback_model>().setQuery(FirebaseDatabase.getInstance("https://hireme-d66c5-default-rtdb.firebaseio.com/").getReference().child("IT20231682_feedback_model"), IT20231682_feedback_model.class)
                        .build();

        it20231682_feedback_viewAdapter = new IT20231682_feedback_viewAdapter(options);
        recyclerView.setAdapter(it20231682_feedback_viewAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        it20231682_feedback_viewAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        it20231682_feedback_viewAdapter.startListening();
    }


    // for searching data
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.feedback_search,menu);
        MenuItem item = menu.findItem(R.id.feedsearch);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                textSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                textSearch(query);
               // Toast.makeText(getApplicationContext(),"no results! " , Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void textSearch(String str){
        FirebaseRecyclerOptions<IT20231682_feedback_model> options =
                new FirebaseRecyclerOptions.Builder<IT20231682_feedback_model>()
                        .setQuery(FirebaseDatabase.getInstance("https://hireme-d66c5-default-rtdb.firebaseio.com/").getReference().child("IT20231682_feedback_model").orderByChild("name").startAt(str).endAt(str+"~"), IT20231682_feedback_model.class)
                        .build();

        it20231682_feedback_viewAdapter = new IT20231682_feedback_viewAdapter(options);
        it20231682_feedback_viewAdapter.startListening();
        recyclerView.setAdapter(it20231682_feedback_viewAdapter);
    }

}