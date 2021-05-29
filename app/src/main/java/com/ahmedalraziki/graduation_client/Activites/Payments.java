package com.ahmedalraziki.graduation_client.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.ahmedalraziki.graduation_client.Classes.Reservation;
import com.ahmedalraziki.graduation_client.Classes.Room;
import com.ahmedalraziki.graduation_client.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Payments extends AppCompatActivity {


    TextView tot;
    Button MR;
    String dci;
    String dco;
    String nob;
    String total;
    String rid;
    View parentLayout;
    List<Room> freRoms;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        freRoms = new ArrayList<>();
        parentLayout = findViewById(android.R.id.content);
        Assigner();
        dataGetter();
        Clicker();
        getFreeRooms();
    }

    private void Assigner(){
        tot = findViewById(R.id.vpy_tvTotal);
        MR = findViewById(R.id.vpy_btnMR);
    }

    private void Clicker(){
        MR.setOnClickListener(v -> MakeReservation());
    }

    private void dataGetter(){
        Intent intent = getIntent();
        dci = intent.getStringExtra("dcip");
        dco = intent.getStringExtra("dcop");
        nob = intent.getStringExtra("nobp");
        total = intent.getStringExtra("total");
        rid = intent.getStringExtra("idp");
        tot.setText(total);
    }

    private void MakeReservation (){
        SharedPreferences prefs = this.getSharedPreferences("hotelc", Context.MODE_PRIVATE);

        String rname = prefs.getString("name" , "Error in AGQ name Retrieval");
        String rphone = prefs.getString("phone" , "Error in AGQ phone Retrieval");
        String remail = prefs.getString("email" , "Error in AGQ email Retrieval");
        String rdci = dci;
        String rdco = dco;
        String rnob = nob;
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy");
        DateFormat dateFormat3 = new SimpleDateFormat("MM");
        DateFormat dateFormat4 = new SimpleDateFormat("dd");
        String currDate = dateFormat.format(calendar.getTime());
        String currYear = dateFormat2.format(calendar.getTime());
        String currMonth = dateFormat3.format(calendar.getTime());
        String currDay = dateFormat4.format(calendar.getTime());
        UUID incomeID = UUID.randomUUID();

        if (rname.equals("Error in AGQ name Retrieval")){
            Intent i=new Intent(Payments.this, Sign_In.class);
            startActivity(i);
        }
        else if (freRoms.size() != 0) {

            Room resRoom = freRoms.get(0);

            prefs.edit().putString("qrdata", rid).apply();
            prefs.edit().putString("uid", rid).apply();
            prefs.edit().putString("rn", resRoom.getNumber()).apply();

            Reservation nr = new Reservation(rid, rname, rphone, remail, rdci, rdco, rnob,total);
            nr.setRoomNo(resRoom.getNumber());

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference resRef = database.getReference();

            resRoom.getRoomRef().setValue(0);

            resRef.child("reservations").child("nci").child(nr.getPhone()).child(nr.getId()).setValue(nr);
            Snackbar.make(parentLayout , "Done !" , Snackbar.LENGTH_LONG).show();

            new Handler().postDelayed(() -> {
                Intent i=new Intent(Payments.this, Dashboard.class);
                startActivity(i);
            }, 200);
        } else {
            Snackbar.make(parentLayout, "Still Loading ...." , Snackbar.LENGTH_LONG).show();
        }
    }

    private void getFreeRooms(){
        if (nob != null){
        int nobI = Integer.parseInt(nob);
        DatabaseReference refFlor = null;
        switch (nobI){
            case 1:
                refFlor = reference.child("rooms").child("floor1");
                break;
            case 2:
                refFlor = reference.child("rooms").child("floor2");
                break;
            case 3:
                refFlor = reference.child("rooms").child("floor3");
                break;
            case 4:
                refFlor = reference.child("rooms").child("floor4");
                break;
            case 5:
                refFlor = reference.child("rooms").child("floor5");
                break;
        }

        if(refFlor != null){
            refFlor.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String numberTemp ;
                    String floorTemp = String.valueOf(nobI);
                    int availabilityTemp ;
                    DatabaseReference roomRefTemp ;

                    for(DataSnapshot d1 : snapshot.getChildren()){
                        numberTemp = d1.getKey();
                        availabilityTemp = Integer.parseInt
                                (Objects.requireNonNull(d1.getValue()).toString());
                        roomRefTemp = d1.getRef();
                        Room roomTemp = new Room(numberTemp, floorTemp, availabilityTemp, roomRefTemp);
                        fillFreeRooms(roomTemp);
                    }
                    Snackbar.make(parentLayout, "Redy !", Snackbar.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) { }});
        }}
    }

    private void fillFreeRooms(Room room){
        if(room.getAvailability() == 1){
            freRoms.add(room);
        }
    }

}