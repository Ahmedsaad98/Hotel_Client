package com.ahmedalraziki.graduation_client.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.ahmedalraziki.graduation_client.MainActivity;
import com.ahmedalraziki.graduation_client.R;

public class Dashboard extends AppCompatActivity {

    Button gtSign;
    Button gtNewRes;
    Button gtMyRes;

    Animation leftAnimation;
    Animation rightAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Assigner();
        Clicker();
    }

    private void Assigner(){
        gtSign = findViewById(R.id.vd_b1);
        gtNewRes = findViewById(R.id.vd_b2);
        gtMyRes = findViewById(R.id.vd_b3);

        leftAnimation = AnimationUtils.loadAnimation(this , R.anim.leftanimation);
        rightAnimation = AnimationUtils.loadAnimation(this , R.anim.rightanimation);

        gtSign.setAnimation(leftAnimation);
        gtNewRes.setAnimation(rightAnimation);
        gtMyRes.setAnimation(leftAnimation);
    }

    private void Clicker(){

        gtSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Dashboard.this, Sign_In.class);
                startActivity(i);
            }
        });

        gtNewRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Dashboard.this, Make_Res.class);
                startActivity(i);
            }
        });

        gtMyRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Dashboard.this, My_Res.class);
                startActivity(i);
            }
        });

    }

}