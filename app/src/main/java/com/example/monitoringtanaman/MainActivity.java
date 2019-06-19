package com.example.monitoringtanaman;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase getDatabase;
    private DatabaseReference getRefenence;
    private FirebaseAuth firebaseAuth;
    private EditText sensorPh, sensorEc, sensorAir;
    private Button btnNutrisi, btnPhUp, btnPhDown, btnWater, btnReport;

    private TextView returnData;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorPh = (EditText) findViewById(R.id.input_phSens);
        sensorEc = (EditText) findViewById(R.id.input_ecSens);
        sensorAir = (EditText) findViewById(R.id.input_waterLvl);
        btnNutrisi = (Button)findViewById(R.id.btn_nutrisi);
        btnPhUp = (Button)findViewById(R.id.btn_phUp);
        btnPhDown = (Button)findViewById(R.id.btn_phDown);
        btnWater = (Button)findViewById(R.id.btn_water);
        btnReport = (Button)findViewById(R.id.btn_report);

        returnData = (TextView)findViewById(R.id.textView2);

        getDatabase = FirebaseDatabase.getInstance();
        getRefenence = getDatabase.getReference();

        firebaseAuth = FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser() == null){
            firebaseAuth.signInWithEmailAndPassword("jamilwahyu53@gmail.com", "echoAlpha")
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "oke", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(MainActivity.this, "not sukses", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }
        else {
            Toast.makeText(MainActivity.this, "current user: " + firebaseAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
        }

        /*
        getRefenence.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(firebaseAuth.getCurrentUser().getUid())) {
                    Toast.makeText(MainActivity.this, "ada", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "KOSONG", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    */

        getRefenence.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataFireBase dataSens = dataSnapshot.getValue(dataFireBase.class);
                //Toast.makeText(MainActivity.this, "dapat "+dataSens.getPhSens(), Toast.LENGTH_SHORT).show();
                sensorPh.setText(dataSens.getPhSens());
                sensorEc.setText(dataSens.getEcSens());
                sensorAir.setText(dataSens.getWaterLvl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });

        btnNutrisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValueControl(new dataControlFireBase("2"));
            }
        });

        btnPhUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRefenence.child("control").child("phUp").setValue("4");
            }
        });

        btnPhDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRefenence.child("control").child("phDown").setValue("5");
            }
        });

        btnWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRefenence.child("control").child("water").setValue("6");
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, report.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
        /*
        //when press and release button
        btnPhDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    returnData.setText("press");
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    returnData.setText("release");
                }

                return false;
            }
        });
        */

    }

    private void setValueControl(dataControlFireBase nilai) {

        getRefenence.child("control").setValue(nilai).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "sukses", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
