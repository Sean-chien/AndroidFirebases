package com.sean.chien.firestore;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class Track {
    private String id;
    private String name;
    private String genre;

    private Timestamp addedDate;

    public Track() {
        // firestore will use this to read data
    }

    public Track(String name, String genre) {
        this.name = name;
        this.genre = genre;
        this.addedDate = new Timestamp(new Date());
    }

    @Exclude
    public String getId() {
        // use @Exclude on the getter of id field to make sure
        // the id does not get sent to Firestore on Save
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public Timestamp getAddedDate() {
        return addedDate;
    }
}
