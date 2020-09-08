package com.example.ex1;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class readMessagesAsyncTask {

    public static class readAsyncTaskFromFirestore extends AsyncTask<Void, Void, Void> {

        private final String COLLECTION_NAME = "MessagesInfo";
        private Executor executor = Executors.newCachedThreadPool();
        readAsyncTaskFromFirestore() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<Message_item> messagesList = new ArrayList<>();


            MainActivity.db.collection(COLLECTION_NAME).get().addOnCompleteListener((task) -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                                Log.d("TAG", doc.getId() + "=>" + doc.getData());
                                Message_item message_item = doc.toObject(Message_item.class);
                                messagesList.add(message_item);
                            }
                            MainActivity.mToPresent = new ArrayList<>(messagesList);
                            MainActivity.adapter.submitList(MainActivity.mToPresent);

                        } else {
                            Log.d("TAG", "Error getting Documents: ", task.getException());
                        }

                    }
                });

            });
            return null;
        }
    }
}