package edu.ucsb.cs.cs190i.rkuang.homies.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import edu.ucsb.cs.cs190i.rkuang.homies.R;
import edu.ucsb.cs.cs190i.rkuang.homies.models.Item;
import edu.ucsb.cs.cs190i.rkuang.homies.models.User;

import static android.content.ContentValues.TAG;

/**
 * Adapter for Items RecyclerView on PostsFragment
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Item> mData;
    private SimpleDateFormat dateFormat;
    private Context mContext;
    public static boolean new_post;

    public PostAdapter() {
        mData = new ArrayList<>();
        dateFormat = new SimpleDateFormat("hh:mm a, MM/dd/yyyy", Locale.US);
        new_post = false;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout , parent, false);
        mContext = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Item item = mData.get(position);
        final User user = item.getUser();

        String username = item.getUser().getName();
        String date = dateFormat.format(item.getDate());
        String description = item.getDescription();

        holder.userTextView.setText(username);
        holder.dateTextView.setText(date);
        holder.itemTextView.setText(description);

        String avatarURL = mData.get(position).getUser().getImageURL();
        Picasso.with(holder.userAvatar.getContext()).load(avatarURL).into(holder.userAvatar);
        holder.userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (firebaseUser.getUid().equals(user.getUid())) {
            holder.delete.setVisibility(View.VISIBLE);
        } else {
            holder.delete.setVisibility(View.INVISIBLE);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseUser.getUid().equals(user.getUid())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Are you sure you want to delete this post?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("items");
                                    db.child(item.getId()).removeValue();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItem(Item item) {
        mData.add(0, item);
        Log.i(TAG, "addItem: Item added at " + mData.indexOf(item));
        notifyItemInserted(0);
    }

    public void removeItem(Item item) {
        Log.i(TAG, "removeItem: "+mData.indexOf(item));
        int position = mData.indexOf(item);

        mData.remove(item);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView userTextView;
        TextView dateTextView;
        TextView itemTextView;

        ImageView userAvatar;

        ImageButton delete;

        ViewHolder(View itemView) {
            super(itemView);
            userTextView = (TextView) itemView.findViewById(R.id.user_textview);
            dateTextView = (TextView) itemView.findViewById(R.id.date_textview);
            itemTextView = (TextView) itemView.findViewById(R.id.item_textview);

            userAvatar = (ImageView) itemView.findViewById(R.id.user_avatar);

            delete = (ImageButton) itemView.findViewById(R.id.delete_button);
        }
    }
}
