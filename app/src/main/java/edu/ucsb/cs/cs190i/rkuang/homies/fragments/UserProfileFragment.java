package edu.ucsb.cs.cs190i.rkuang.homies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import edu.ucsb.cs.cs190i.rkuang.homies.R;

import static edu.ucsb.cs.cs190i.rkuang.homies.R.layout.fragment_user_profile;

/**
 * Created by Ky on 6/10/2017.
 */

public class UserProfileFragment extends DialogFragment {

    private static String uid;

    public UserProfileFragment() {

    }

    public static UserProfileFragment newInstance(String user_id) {
        Bundle args = new Bundle();
        UserProfileFragment fragment = new UserProfileFragment();
        fragment.setArguments(args);

        uid = user_id;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ImageView profile_pic = (ImageView) view.findViewById(R.id.profpic);
        TextView username     = (TextView)  view.findViewById(R.id.username);
        TextView user_email   = (TextView)  view.findViewById(R.id.email);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");
        db.child(uid)

        String name = firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();
        final String photoURL = firebaseUser.getPhotoUrl().toString();
        String uid = firebaseUser.getUid();

        Picasso.with(this).load(photoURL).resize(500,500).into(profile_pic);
        String dispUname = username.getText() + " " + name;
        String dispUmail = user_email.getText() + " " + email;
        String dispUid = userid.getText() + " " + uid;
        username.setText(dispUname);
        user_email.setText(dispUmail);
        userid.setText(dispUid);
    }
}
