package com.example.ex1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigureName extends AppCompatActivity{

    Button nameButton, skipButton;
    TextView helloText, fillName;
    EditText nameEditText;

    private final String COLLECTION_NAME = "defaults";
    private final String DOCUMENT_NAME = "userName";
    private UserModel userModel =new UserModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_name);
        helloText = (TextView)findViewById(R.id.hello);
        fillName = (TextView)findViewById(R.id.fillName);
        nameEditText = (EditText)findViewById(R.id.nameEditText);
        nameButton = (Button)findViewById(R.id.buttonThatsMyName);
        skipButton = (Button)findViewById(R.id.buttonSkip);


        nameButton.setVisibility(View.INVISIBLE);
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() >0){
                    nameButton.setVisibility(View.VISIBLE);
                }
                else {
                    nameButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    userModel.inserUserNameToDB(nameEditText.getText().toString());
                    finish();
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
