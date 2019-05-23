package com.sean.chien.firestore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.core.View;

import java.util.ArrayList;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.TrackViewHolder> {

    private ArrayList<Track> mTrackArrayList;
    private Context mcontext;
    private LayoutInflater mLayoutInflater;
    private OnLongClickListenerDelegate mDelegate;

    public TrackAdapter(Context context, ArrayList<Track> TrackArrayList
            , OnLongClickListenerDelegate delegate) {
        mTrackArrayList = TrackArrayList;
        mcontext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mDelegate = delegate;

    }

    @NonNull
    @Override
    public TrackAdapter.TrackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        android.view.View itemView = mLayoutInflater.inflate(R.layout.activity_tracks, viewGroup, false);
        return new TrackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder trackViewHolder, int i) {
        TrackViewHolder.bind(mTrackArrayList.get(i));

    }

    @Override
    public int getItemCount() {
        return mTrackArrayList.size();

    }

    class TrackViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private TextView martistTextView;
        private TextView mgenreTextView;
        private TextView maddedDateTextView;

        public void bind(Artist artist) {
            martistTextView.setText(artist.getName());
            mgenreTextView.setText(artist.getGenre());
            maddedDateTextView.setText(artist.getAddedDate().toDate().toString());
        }

        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            martistTextView = itemView.findViewById(R.id.artistTextView);
            mgenreTextView = itemView.findViewById(R.id.genreTextView);
            maddedDateTextView = itemView.findViewById(R.id.addedDateTextView);
        }
    }

        @Override
        public boolean onLongClick(android.view.View v) {
            int pos = getAdapterPosition();
            Toast.makeText(mcontext, "" + mTrackArrayList.get(pos).getName(), Toast.LENGTH_LONG)
                    .show();
            mDelegate.onLongClickViewHolder(v, pos);
            return false;
        }
    }
    interface OnLongClickListenerDelegate {
        void onLongClickViewHolder(android.view.View view, int position);
    }


