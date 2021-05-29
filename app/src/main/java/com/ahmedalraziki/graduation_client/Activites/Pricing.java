package com.ahmedalraziki.graduation_client.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ahmedalraziki.graduation_client.R;

public class Pricing extends AppCompatActivity {

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricing);

        Assigner();
        Clicker();
    }

    private void Assigner(){
        back = findViewById(R.id.vp_btnBack);
    }

    private void Clicker(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}