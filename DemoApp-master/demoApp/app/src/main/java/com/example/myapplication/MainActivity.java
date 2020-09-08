package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public TextView texview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button fisherman_button = (Button)findViewById(R.id.sendButton);
        texview = (TextView)findViewById(R.id.textView);
        if (savedInstanceState!= null){
            onRestoreInstanceState(savedInstanceState);
        }

        fisherman_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFisherman();
            }
        });
        texview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDigits();
            }
        });

    }

    public void openFisherman(){
        Intent intent = new Intent(this, fisherman.class);
        startActivity(intent);
    }

    public void openDigits(){
        Intent intent = new Intent(this, Digits.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                texview.setText("you have entered: " + result);
            }
        }
    }
}
