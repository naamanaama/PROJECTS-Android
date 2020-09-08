package com.example.ex1;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class MyApp  extends Application{

    private final String COLLECTION_NAME = "defaults";
    private final String DOCUMENT_NAME = "userName";
    public UserModel userModel;
    private Executor executor = Executors.newCachedThreadPool();
    private MutableLiveData<String> userNameLiveData = new MutableLiveData<String>();
    private MutableLiveData<Message_item> messagesLiveData = new MutableLiveData<Message_item>();

    @Override
    public void onCreate(){
        super.onCreate();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docIdRef = db.collection(COLLECTION_NAME).document(DOCUMENT_NAME);

        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                            } else {
                                openConfigScreen();
                            }
                        } else {
                            Log.d("TAG", "Failed with: ", task.getException());
                        }
                    }
                });
            }
        });

    }

    public void openConfigScreen(){
        Intent intent = new Intent(this, ConfigureName.class);
        startActivity(intent);

    }
    public MutableLiveData getLiveDataUserName(){
        return userNameLiveData;
    }

    public MutableLiveData getMessagesLiveData(){
        return messagesLiveData;
    }
}
