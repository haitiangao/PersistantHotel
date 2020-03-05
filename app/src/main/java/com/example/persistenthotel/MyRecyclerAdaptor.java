package com.example.persistenthotel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyRecyclerAdaptor extends RecyclerView.Adapter<MyRecyclerAdaptor.GuestViewHolder> {

    private List<Guest> guestList;
    private UserClickListener userClickListener;

    public MyRecyclerAdaptor(List<Guest> guestList, UserClickListener userClickListener) {
        this.guestList = guestList;
        this.userClickListener = userClickListener;
    }

    public interface UserClickListener {
        void displayUser(Guest guest);
    }

    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.guest_item_layout, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdaptor.GuestViewHolder holder, int position) {

        holder.NameTextView.setText(guestList.get(position).getActualName());
        holder.PrefixTextView.setText(guestList.get(position).getPrefix());

        holder.itemView.setOnClickListener(view -> {
            userClickListener.displayUser(guestList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return guestList.size();
    }


    class GuestViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name_textview)
        TextView NameTextView;
        @BindView(R.id.prefix_textview)
        TextView PrefixTextView;

        public GuestViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
