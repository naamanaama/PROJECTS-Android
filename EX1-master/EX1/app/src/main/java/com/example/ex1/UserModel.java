package com.example.ex1;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserModel extends ViewModel {

        private MutableLiveData<String> userNameLiveData = MainActivity.app.getLiveDataUserName();
        private Executor executor = Executors.newCachedThreadPool();
        private final String COLLECTION_NAME = "defaults";
        private final String DOCUMENT_NAME = "userName";
        private boolean nameExists;


        public LiveData<String> getUserLiveData() {
                return userNameLiveData;
        }

        public UserModel() {
            loadUserNameFromDB();
        }

        public void loadUserNameFromDB() {
            // depending on the action, do necessary business logic calls and update the
            // userLiveData.

            //userNameLiveData.setValue("");
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(COLLECTION_NAME).document(DOCUMENT_NAME).get().addOnCompleteListener(task -> {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                //String userName= document.toObject(String.class);
                                Map<String,Object> userName = document.getData();

                                UserModel.this.userNameLiveData.postValue(userName.toString());
                                Log.d("FragNotif", "THATS THE NAME "+ userName);
                            }
                        }
                          else {
                            Log.d("FragNotif", "get failed with ", task.getException());
                        }
                    }
                });
            });
        }


        public void inserUserNameToDB(String userName){
            // first update our local phone - post to the live data
            userNameLiveData.postValue(userName);
            // than update to remote locations - for example firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userName",userName);
            db.collection(COLLECTION_NAME).document(DOCUMENT_NAME).set(userInfo);

        }

}
