package edu.ucsb.cs.cs190i.rkuang.homies.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import edu.ucsb.cs.cs190i.rkuang.homies.R;
import edu.ucsb.cs.cs190i.rkuang.homies.adapters.PostAdapter;
import edu.ucsb.cs.cs190i.rkuang.homies.fragments.CreatePostFragment;
import edu.ucsb.cs.cs190i.rkuang.homies.models.Item;
import edu.ucsb.cs.cs190i.rkuang.homies.models.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int CREATE_POST_REQUEST = 0;

    RecyclerView recyclerView;
    PostAdapter postAdapter;

    FirebaseDatabase db;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        postAdapter = new PostAdapter();

        recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postAdapter);

        db = FirebaseDatabase.getInstance();
        mRef = db.getReference();
        mRef.child("items").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG, "onChildAdded: new item");
                Item item = dataSnapshot.getValue(Item.class);
                Log.i(TAG, "onChildAdded: "+dataSnapshot.getValue(Item.class));
                postAdapter.addItem(0, item);
                recyclerView.scrollToPosition(0);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG, "onChildChanged: item changed");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onChildRemoved: item deleted");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        firebaseTest();

        FloatingActionButton create_post_button = (FloatingActionButton) findViewById(R.id.create_post_button);
        create_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: launch add post fragment");
                FragmentManager fm = getSupportFragmentManager();
                CreatePostFragment dialogFragment = CreatePostFragment.newInstance();
                dialogFragment.show(fm, "fragment_create_post");
            }
        });
    }

    /*
    private ArrayList<Item> fakePosts() {
        Calendar c = Calendar.getInstance();
        User u1 = new User("John Smith");
        User u2 = new User("Jane Doe");

        Item i1 = new Item(u1, new Date(c.getTimeInMillis()), "Hey can someone buy me some milk?");
        Item i2 = new Item(u2, new Date(c.getTimeInMillis()), "We need more toilet paper");
        Item i3 = new Item(u1, new Date(c.getTimeInMillis()), "Oh can I get some more cereal also?");

        ArrayList<Item> list = new ArrayList<>();
        list.add(i3);
        list.add(i2);
        list.add(i1);

        return list;
    }
    */

    private void firebaseTest() {
        Calendar c = Calendar.getInstance();
        User u1 = new User("Brendan Truong");
        Item i1 = new Item(u1, "I love spaghetti");

        mRef.child("items").child("pos9").setValue(i1);
    }
//    */
}
