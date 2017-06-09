package edu.ucsb.cs.cs190i.rkuang.homies.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.ucsb.cs.cs190i.rkuang.homies.R;
import edu.ucsb.cs.cs190i.rkuang.homies.adapters.PostAdapter;
import edu.ucsb.cs.cs190i.rkuang.homies.models.Item;

import static edu.ucsb.cs.cs190i.rkuang.homies.R.layout.fragment_posts;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    private FirebaseDatabase db;
    private DatabaseReference itemsRef;

    private GoogleApiClient googleApiClient;

    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = FirebaseDatabase.getInstance();
        itemsRef = db.getReference();

        return inflater.inflate(fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        postAdapter = new PostAdapter();

        recyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postAdapter);

        itemsRef = db.getReference("items");
        itemsRef.orderByChild("date").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Item item = dataSnapshot.getValue(Item.class);
                postAdapter.addItem(0, item);
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

        FloatingActionButton create_post_button = (FloatingActionButton) view.findViewById(R.id.create_post_button);
        create_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreatePostFragment dialogFragment = CreatePostFragment.newInstance();
                dialogFragment.show(getFragmentManager(), "fragment_create_post");
            }
        });
    }
}
