package edu.ucsb.cs.cs190i.rkuang.homies.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import edu.ucsb.cs.cs190i.rkuang.homies.R;
import edu.ucsb.cs.cs190i.rkuang.homies.adapters.PostAdapter;
import edu.ucsb.cs.cs190i.rkuang.homies.models.Item;
import edu.ucsb.cs.cs190i.rkuang.homies.models.User;

import static android.content.ContentValues.TAG;
import static edu.ucsb.cs.cs190i.rkuang.homies.R.layout.fragment_create_post;

public class CreatePostFragment extends DialogFragment {

    DatabaseReference db;
    FirebaseUser user;

    public CreatePostFragment() {

    }

    public static CreatePostFragment newInstance() {
        Bundle args = new Bundle();
        CreatePostFragment fragment = new CreatePostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        return inflater.inflate(fragment_create_post, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final CreatePostFragment me = this;
        final EditText editText = (EditText) view.findViewById(R.id.description_field);
        Button button = (Button) view.findViewById(R.id.post_message_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: post message button clicked");
                String description = editText.getText().toString();
                if (description.length() != 0) {
                    PostAdapter.new_post = true;
                    String name = user.getDisplayName();
                    String photoURL = user.getPhotoUrl().toString();
                    String uid = user.getUid();

                    Item i = new Item(new User(name, photoURL, uid), description, UUID.randomUUID().toString());
                    db.child("items").child(i.getId()).setValue(i);
                    me.getDialog().dismiss();
                } else {
                    Snackbar.make(me.getView(), "Enter a description", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        return d;
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;

        Window params = this.getDialog().getWindow();
        params.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setGravity(Gravity.BOTTOM);
    }
}
