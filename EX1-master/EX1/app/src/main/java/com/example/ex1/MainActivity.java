package com.example.ex1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity{

    public static final String TO_PRESENT = "to_present";
    final String LIST_STATE_KEY = "list_state_key";
    final String SAVE_TEXT_EDIT_TEXT = "save_text_edit_text";
    final static String SAVE_MlIST_TO_PREFERENCE = "messageList";
    private final String COLLECTION_NAME = "defaults";
    private static LinearLayoutManager linearLayoutManager;
    private Parcelable mListState = null;
    public static RecyclerUtils.RecyclerViewAdapter adapter = new RecyclerUtils.RecyclerViewAdapter();
    public static ArrayList<Message_item> mToPresent = new ArrayList<>();
    private EditText edittext;
    private TextView HelloUser;
    public static MyApp app;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Map<String, Map<String, Object>> MessagesInfo = new HashMap<>();
    private ManageMessagesDB manageMessagesDB;
    LiveDataMessages myLiveData = new LiveDataMessages();
    MutableLiveData messagesLiveData = new MutableLiveData();
    private UserModel model;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        //use on Create and save instance state
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (MyApp) getApplicationContext();
        messagesLiveData = app.getMessagesLiveData();
        manageMessagesDB =  new ManageMessagesDB();
        manageMessagesDB.loadMessages();
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        Button button = (Button) findViewById(R.id.sendButton);
        edittext = (EditText) findViewById(R.id.textToSend);
        HelloUser = (TextView)findViewById(R.id.helloUser);
        HelloUser.setVisibility(View.INVISIBLE);

        //todo here we get the view model - how does it work?
        // Get the ViewModel.
        model = ViewModelProviders.of(this).get(UserModel.class);

        model.getUserLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String newName) {
                // Update the UI
                HelloUser.setText(getString(R.string.helloUser,newName));
                HelloUser.setVisibility(View.VISIBLE);
            }
        });

        messagesLiveData.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Message_item message_item = (Message_item) o;
                openConfigureMessages(message_item);

            }
        });

        if (savedInstanceState!= null){
            onRestoreInstanceState(savedInstanceState);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = edittext.getText().toString();
                if(newText.equals("")){
                    String message = "Error! can't send empty message";
                    int duration = Snackbar.LENGTH_SHORT;
                    showSnackbar(v, message, duration);
                }
                else {
                    Message_item new_message =new Message_item(newText);
                    manageMessagesDB.insertMessage(new_message);
                    manageMessagesDB.loadMessages();
                    edittext.getText().clear();
                }
            }
        });
    }


    public void showSnackbar(View view, String message,int duration ){
        Snackbar.make(view,message,duration).show();
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            adapter.submitList(mToPresent);
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
            linearLayoutManager.onRestoreInstanceState(mListState);
            mToPresent = (ArrayList<Message_item>) savedInstanceState.getSerializable(TO_PRESENT);
            edittext.setText(savedInstanceState.get(SAVE_TEXT_EDIT_TEXT).toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mListState = linearLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY,mListState);
        outState.putSerializable(TO_PRESENT,mToPresent);
        outState.putString(SAVE_TEXT_EDIT_TEXT,edittext.getText().toString());
        Map<List,Object> dataToSave = new HashMap<List, Object>();
    }

    public void openConfigureMessages(Message_item message_item){
        Intent intent = new Intent(this,MessageDetails.class);
        intent.putExtra("message_item", message_item);
        startActivity(intent);


    }

}
