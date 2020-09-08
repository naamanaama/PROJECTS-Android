package com.example.myapplication;
import com.example.*;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;


public class Digits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dig);
        final EditText editText = (EditText)findViewById(R.id.edit_text_digits);
        final TextView textView = (TextView)findViewById(R.id.textView);
        final String digits = "";
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==5){
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("result", editText.getText().toString());
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
