package com.example.todo.Model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class TId {
    @Exclude
    public String TId;

    public <T extends TId> T withId(@NonNull final String id){
        this.TId = id;
        return (T) this;
    }
}
