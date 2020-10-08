package com.example.ex1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MessageDetails extends AppCompatActivity {
    private TextView messageDetails;
    private TextView messageTime;
    private TextView fromWhichPhone;
    private Button permenentDelete;
    Message_item message_item = new Message_item();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        ManageMessagesDB manageMessagesDB = new ManageMessagesDB();
        Intent intent = getIntent();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            message_item = (Message_item)getIntent().getSerializableExtra("message_item");
        }

        String model = android.os.Build.MODEL;
        messageDetails = (TextView)findViewById(R.id.messageDetailsTextView);
        messageTime = (TextView)findViewById(R.id.sentTimeTextView);
        messageTime.setText(getString(R.string.sentTime,message_item.timestamp));
        fromWhichPhone = (TextView)findViewById(R.id.sentFrom);
        fromWhichPhone.setText(getString(R.string.sentFrom,model));
        permenentDelete = (Button)findViewById(R.id.permenentDelete);



        permenentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageMessagesDB.deleteMessage(message_item);
                manageMessagesDB.loadMessages();
                finish();
            }
        });


    }
}
