package com.ahmedalraziki.graduation_client.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ahmedalraziki.graduation_client.R;
import com.google.android.material.snackbar.Snackbar;

public class Sign_In extends AppCompatActivity {

    EditText etName  ;
    EditText etPhone ;
    EditText etEmail ;
    Button   btnSign ;
    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        parentLayout = findViewById(android.R.id.content);

        Assigner();
        Clicker();
    }

    private void Assigner(){
        etName = findViewById(R.id.vs_et1) ;
        etPhone = findViewById(R.id.vs_et2);
        etEmail = findViewById(R.id.vs_et3);
        btnSign = findViewById(R.id.vs_b1) ;
    }

    private void Clicker(){
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SI();
            }
        });
    }

    private void SI(){
        String Name = etName.getText().toString();
        String Phone = etPhone.getText().toString();
        String Email = etEmail.getText().toString();
        if (!Name.isEmpty() && !Phone.isEmpty() && !Email.isEmpty()) {
            SharedPreferences prefs = this.getSharedPreferences("hotelc", Context.MODE_PRIVATE);
            prefs.edit().putString("name", Name).apply();
            prefs.edit().putString("phone", Phone).apply();
            prefs.edit().putString("email", Email).apply();

            Snackbar.make(parentLayout , "Signed In Successfully" , Snackbar.LENGTH_LONG).show();
        }
        else {
            Snackbar.make(parentLayout , "Please Fill In Your Info" , Snackbar.LENGTH_LONG).show();
        }
    }

}