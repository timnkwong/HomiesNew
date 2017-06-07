package edu.ucsb.cs.cs190i.rkuang.homies.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import edu.ucsb.cs.cs190i.rkuang.homies.R;
import edu.ucsb.cs.cs190i.rkuang.homies.models.Item;

import static android.content.ContentValues.TAG;

/**
 * Created by ricky on 6/5/17.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    ArrayList<Item> mData;
    SimpleDateFormat dateFormat;

    public PostAdapter() {
        mData = new ArrayList<>();
        dateFormat = new SimpleDateFormat("hh:mm a, MM/dd/yyyy", Locale.US);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout , parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String userName = mData.get(position).getUser().getName();
        String date = dateFormat.format(mData.get(position).getDate());
        String description = mData.get(position).getDescription();

        holder.userTextView.setText(userName);
        holder.dateTextView.setText(date);
        holder.itemTextView.setText(description);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView userTextView;
        public TextView dateTextView;
        public TextView itemTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            userTextView = (TextView) itemView.findViewById(R.id.user_textview);
            dateTextView = (TextView) itemView.findViewById(R.id.date_textview);
            itemTextView = (TextView) itemView.findViewById(R.id.item_textview);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void addItem(int position, Item item) {
        mData.add(position, item);
        notifyItemInserted(position);
    }
}
