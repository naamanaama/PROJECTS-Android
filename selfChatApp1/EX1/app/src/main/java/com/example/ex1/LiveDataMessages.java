package com.example.ex1;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class LiveDataMessages extends LiveData<List<Message_item>> {

    private ExecutorService executor = Executors.newSingleThreadExecutor();


    private class MessagesEventListener implements ValueEventListener {
        /**
         * Read data from firestore.  This task should work not on main thread
         */
        public ArrayList<Message_item> getDataFirestore() {

            ArrayList<Message_item> messagesList = new ArrayList<>();
            new Thread(() -> {

                MainActivity.db.collection("MessagesInfo").get().addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                            Log.d("TAG", doc.getId() + "=>" + doc.getData());
                            Message_item message_item = doc.toObject(Message_item.class);
                            messagesList.add(message_item);
                        }
                        MainActivity.mToPresent = messagesList;

                    } else {
                        Log.d("TAG", "Error getting Documents: ", task.getException());
                    }
                });


            }).start();
            return messagesList;
        }


        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            // Do parsing on background thread
            executor.submit(() -> {
                        ArrayList<Message_item> messages = getDataFirestore();
                        postValue(messages);
                    }
            );
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    }
}
