package com.example.android_proiect_final_version;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseService {
    private final DatabaseReference reference;
    private static FirebaseService firebaseService;

    private FirebaseService() {
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseService getInstance() {
        if (firebaseService == null) {
            synchronized (FirebaseService.class) {
                if (firebaseService == null) {
                    firebaseService = new FirebaseService();
                }
            }
        }
        return firebaseService;
    }

    // CRUD pentru DateContact
    public void insert(DateContact dateContact) {
        if (dateContact == null || dateContact.getId() != null) {
            return;
        }
        String id = reference.child("dateContact").push().getKey();
        dateContact.setId(id);
        reference.child("dateContact").child(dateContact.getId()).setValue(dateContact);
    }

    public void update(DateContact dateContact) {
        if (dateContact == null || dateContact.getId() == null) {
            return;
        }
        reference.child("dateContact").child(dateContact.getId()).setValue(dateContact);
    }

    public void delete(DateContact dateContact) {
        if (dateContact == null || dateContact.getId() == null) {
            return;
        }
        reference.child("dateContact").child(dateContact.getId()).removeValue();
    }

    public void addDateContactListener(Callback<List<DateContact>> callback) {
        reference.child("dateContact").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<DateContact> dateContactLista = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    DateContact dateContact = data.getValue(DateContact.class);
                    if (dateContact != null) {
                        dateContactLista.add(dateContact);
                    }
                }
                callback.runOnUI(dateContactLista);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "DateContact: Error while reading data", error.toException());
            }
        });
    }

    // CRUD pentru Feedback
    public void insert(Feedback feedback) {
        if (feedback == null || feedback.getId() != null) {
            return;
        }
        String id = reference.child("feedback").push().getKey();
        feedback.setId(id);
        reference.child("feedback").child(feedback.getId()).setValue(feedback);
    }

    public void update(Feedback feedback) {
        if (feedback == null || feedback.getId() == null) {
            return;
        }
        reference.child("feedback").child(feedback.getId()).setValue(feedback);
    }

    public void delete(Feedback feedback) {
        if (feedback == null || feedback.getId() == null) {
            return;
        }
        reference.child("feedback").child(feedback.getId()).removeValue();
    }

    public void addFeedbackListener(Callback<List<Feedback>> callback) {
        reference.child("feedback").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Feedback> feedbackList = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Feedback feedback = data.getValue(Feedback.class);
                    if (feedback != null) {
                        feedbackList.add(feedback);
                    }
                }
                callback.runOnUI(feedbackList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase", "Feedback: Error while reading data", error.toException());
            }
        });
    }
}
