package com.ahmedalraziki.graduation_client.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedalraziki.graduation_client.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class My_Res extends AppCompatActivity {

    ImageView imgQR;
    TextView  txtUID;
    TextView  txtRN;
    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_res);

        parentLayout = findViewById(android.R.id.content);
        Assigner();
        cQR();
    }

    private void Assigner(){
        imgQR = findViewById(R.id.vqr_img1);
        txtUID = findViewById(R.id.vqr_tv1);
        txtRN = findViewById(R.id.vqr_tv2);
    }

    private void cQR (){
        SharedPreferences prefs = this.getSharedPreferences("hotelc", Context.MODE_PRIVATE);
        String qrData = prefs.getString("qrdata" , "Error in VMTRES qr data Retrieval");
        String uid = prefs.getString("uid" , "Error in VMTRES uid Retrieval");
        String roomNo = prefs.getString("rn", "Error in room no view My_Res");

        if (qrData.equals("Error in VMTRES name Retrieval")){
            Snackbar.make(parentLayout , "You Have No Reservation" , Snackbar.LENGTH_LONG).show();
        }

        else{
        txtUID.setText("Your Reservation ID is: \n" + uid);
        txtRN.setText("Your room's number is : \n" + roomNo);
        BitMatrix resultQR ;
        try {
            resultQR = new MultiFormatWriter().encode(qrData , BarcodeFormat.QR_CODE , 200 ,200 , null
            );
        } catch (WriterException e){
            e.printStackTrace();
            return ;
        }
        int width = resultQR.getWidth();
        int height = resultQR.getHeight();
        int[] pixels = new int[width * height];
        for (int x  = 0 ; x < height ; x++){
            int offset = x * width;
            for (int k = 0 ; k < width ; k++){
                pixels[offset + k] = resultQR.get(k , x) ? BLACK : WHITE ;
            }
        }
        Bitmap myQR = Bitmap.createBitmap(width , height , Bitmap.Config.ARGB_8888);
        myQR.setPixels(pixels, 0 , width , 0 , 0 , width , height);
        imgQR.setImageBitmap(myQR);
            }
    }
}