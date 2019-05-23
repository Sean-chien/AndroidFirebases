package com.sean.chien.firestore;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class TracksActivity extends AppCompatActivity {
    private RecyclerView maddedDateRecyclerView;
    private TrackAdapter mTrackAdapter;
    private EditText mtrackEditText;
    private SeekBar mSeekBar;
    private ArrayList<Track> mTrackArrayList;


    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListenerRegistration mListenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        maddedDateRecyclerView = findViewById(R.id.addedDateRecyclerView);
        mtrackEditText = findViewById(R.id.trackEditText);
        mSeekBar = findViewById(R.id.addedSeekBar);
        maddedDateRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();
        // read in data (attach data change listener)
        mListenerRegistration = db.collection("Track")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        mTrackArrayList = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()) {
                            Track track = documentSnapshot.toObject(Track.class);
                            track.setId(documentSnapshot.getId());
                            mTrackArrayList.add(track);
                        }
                        mTrackAdapter = new TracksActivity(getApplicationContext(), mTrackArrayList, TracksActivity.this);
                        maddedDateRecyclerView.setAdapter(mTrackAdapter);
                    }
                });
    }
    @Override
    protected void onStop() {
        super.onStop();
        // detach listener
        mListenerRegistration.remove();

    }

    @Override
    public void onLongClickViewHolder(View view, int position) {
        showAlertDialog(position);
    }

    private void showAlertDialog(int position) {
        final Track track = mTrackArrayList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater
                .from(this)
                .inflate(R.layout.activity_alert_dialog, null);
        builder.setView(dialogView);

        final EditText nameET = dialogView.findViewById(R.id.dialogNameEditText);
        nameET.setSelection(Track.getName());
        final Spinner spinner = dialogView.findViewById(R.id.dialogGenreSpinner);
        spinner.setSelection(getIndexForGenre(Track.getGenre()));

        Button updateBtn = dialogView.findViewById(R.id.dialogUpdateButton);
        Button deleteBtn = dialogView.findViewById(R.id.dialogDeleteButton);

        builder.setTitle("Update " + Track.getName());
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }


    public void addTracks(View view) {
    }
}
