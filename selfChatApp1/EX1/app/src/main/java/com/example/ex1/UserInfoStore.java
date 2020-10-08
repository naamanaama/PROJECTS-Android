package com.example.ex1;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;


public class UserInfoStore {

    public String storedMessagesList;
    public SharedPreferences.Editor editor;
    public ArrayList<Message_item> restoredMessages;
    public SharedPreferences sp;
    final String SAVE_MlIST_TO_PREFERENCE = "messageList";

    public UserInfoStore(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        storedMessagesList =sp.getString(SAVE_MlIST_TO_PREFERENCE, null);

        Gson gson = new Gson();
        restoredMessages = gson.fromJson(storedMessagesList,ArrayList.class); //restore messages
        editor = sp.edit();
    }
}
