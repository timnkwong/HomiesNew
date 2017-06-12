package edu.ucsb.cs.cs190i.rkuang.homies.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.ucsb.cs.cs190i.rkuang.homies.R;
import edu.ucsb.cs.cs190i.rkuang.homies.adapters.PostAdapter;
import edu.ucsb.cs.cs190i.rkuang.homies.adapters.EventAdapter;
import edu.ucsb.cs.cs190i.rkuang.homies.models.Event;
import edu.ucsb.cs.cs190i.rkuang.homies.models.Item;

import static edu.ucsb.cs.cs190i.rkuang.homies.R.layout.fragment_posts;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView eventsRecyclerView;
    private PostAdapter postAdapter;
    private EventAdapter eventAdapter;

    private FirebaseDatabase db;
    private DatabaseReference itemsRef;
    private DatabaseReference eventsRef;


    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = FirebaseDatabase.getInstance();
        itemsRef = db.getReference();
        eventsRef = db.getReference();

        return inflater.inflate(fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        postAdapter = new PostAdapter();
        eventAdapter = new EventAdapter();

        recyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postAdapter);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        eventsRecyclerView = (RecyclerView) view.findViewById(R.id.events_recyclerview);
        eventsRecyclerView.setHasFixedSize(true);
        eventsRecyclerView.setLayoutManager(layoutManager2);
        eventsRecyclerView.setAdapter(eventAdapter);

        itemsRef = db.getReference("items");
        eventsRef = db.getReference("events");

        itemsRef.orderByChild("date").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Item item = dataSnapshot.getValue(Item.class);
                postAdapter.addItem(item);
                recyclerView.scrollToPosition(0);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Item item = dataSnapshot.getValue(Item.class);
                postAdapter.removeItem(item);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        eventsRef.orderByChild("eventDate").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event event = dataSnapshot.getValue(Event.class);
                eventAdapter.addItem(event);
                recyclerView.scrollToPosition(0);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                eventAdapter.removeEvent(event);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        FloatingActionButton create_post_button = (FloatingActionButton) view.findViewById(R.id.create_post_button);
        create_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePostFragment dialogFragment = CreatePostFragment.newInstance();
                dialogFragment.show(getFragmentManager(), "fragment_create_post");
            }
        });

        FloatingActionButton create_event_button = (FloatingActionButton) view.findViewById(R.id.create_event_button);
        create_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateEventFragment dialogFragment = CreateEventFragment.newInstance();
                dialogFragment.show(getFragmentManager(), "fragment_create_post");
            }
        });
    }
}

