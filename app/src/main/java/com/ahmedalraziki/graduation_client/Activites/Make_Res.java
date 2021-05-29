package com.ahmedalraziki.graduation_client.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.ahmedalraziki.graduation_client.Classes.DatePickerFragment;
import com.ahmedalraziki.graduation_client.Classes.Reservation;
import com.ahmedalraziki.graduation_client.MainActivity;
import com.ahmedalraziki.graduation_client.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Make_Res extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button dci    ;
    Button dco    ;
    Button nofp   ;
    Button nofm   ;
    Button MRES   ;
    Button SP     ;
    TextView dcin ;
    TextView dcio ;
    TextView nor  ;
    int cioco     ;
    int nob = 1   ;
    View parentLayout;
    Calendar date1;
    Calendar date2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_res);

        parentLayout = findViewById(android.R.id.content);
        Assigner();
        Clicker();
    }

    private void Assigner() {
        SP  = findViewById(R.id.vr_btn6);
        dci  = findViewById(R.id.vr_btn5);
        dco  = findViewById(R.id.vr_btn4);
        nofp = findViewById(R.id.vr_btn3);
        nofm = findViewById(R.id.vr_btn2);
        MRES = findViewById(R.id.vr_btn1);
        dcin = findViewById(R.id.vr_tv4);
        dcio = findViewById(R.id.vr_tv5);
        nor  = findViewById(R.id.vr_tv6);
    }

    private void Clicker() {

        SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Make_Res.this, Pricing.class);
                startActivity(i);
            }
        });

        dci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cioco = 1;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager() , "Date Check In");
            }
        });

        dco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cioco = 2;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager() , "Date Check Out");
            }
        });

        nofp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nob <= 4){
                nob = nob + 1;
                nor.setText(String.valueOf(nob));
            }}
        });
        nofm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nob >= 2){
                nob = nob - 1;
                nor.setText(String.valueOf(nob));
            }}
        });

        MRES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToPayments();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        String cdST = format1.format(c.getTime());

        if (cioco == 1){
            dcin.setText(cdST);
            date1 = c ;
        } else {
            dcio.setText(cdST);
            date2 = c;
        }
    }

    private void GoToPayments(){
        String rdci = dcin.getText().toString();
        String rdco = dcio.getText().toString();
        String rnob = nor.getText().toString();
        String rid = UUID.randomUUID().toString();


        if (rdci.equals("") || rdco.equals("") || rdci.equals("DD/MM/YYYY") || rdco.equals("DD/MM/YYYY")){
            Snackbar.make(parentLayout , "Please Fill In All Fields" , Snackbar.LENGTH_LONG).show();
        }
        else {
        Intent intent = new Intent(Make_Res.this , Payments.class);
        String total = calcCost();

        intent.putExtra("idp" , rid);
        intent.putExtra("dcip" , rdci);
        intent.putExtra("dcop" , rdco);
        intent.putExtra("nobp" , rnob);
        intent.putExtra("total" , total);
        startActivity(intent);
        }
    }

    private String calcCost () {
        long Diff = date2.getTimeInMillis() - date1.getTimeInMillis();
        long seconds = Diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        // Tacking a 20$ for all room types per night
        long dpd = 0;
        if (nob == 1 ){
            dpd = 20;
        } else if (nob == 2 ){
            dpd = 30;
        } else if (nob == 3){
            dpd = 35;
        } else if (nob >= 4){
            dpd = 40;
        }

        long Total = dpd * days ;
        return String.valueOf(Total);
    }



}