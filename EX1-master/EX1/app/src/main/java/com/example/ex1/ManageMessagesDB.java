package com.example.ex1;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ManageMessagesDB {

    private final String COLLECTION_NAME = "MessagesInfo";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Executor executor = Executors.newCachedThreadPool();

    ManageMessagesDB(){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public void loadMessages(){
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

                    } else {
                        Log.d("TAG", "Error getting Documents: ", task.getException());
                    }


                    MainActivity.adapter.submitList(MainActivity.mToPresent);
                }

            });
            MainActivity.adapter.submitList(MainActivity.mToPresent);

        });
    }


    public void insertMessage(Message_item message_item){
        Map<String, Object> messagesInfo = new HashMap<>();
        messagesInfo.put("text", message_item.text);
        messagesInfo.put("timestamp", message_item.timestamp);
        messagesInfo.put("id", message_item.id);
        db.collection(COLLECTION_NAME).document("messagesInfo "+message_item.id)
                .set(messagesInfo, SetOptions.merge());

    }

    public void deleteMessage(Message_item message_item){
        db.collection(COLLECTION_NAME).document("messagesInfo "+message_item.id).delete();

    }

}

