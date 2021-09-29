package com.example.onlineclothingstore.user_and_payment_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineclothingstore.MainActivity;
import com.example.onlineclothingstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class CardDetails extends AppCompatActivity {

    TextView txt1,txt2,txt3,txt4;
    Button btn,swap,Pay;

    DatabaseReference ref;
    Dialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        myDialog = new Dialog(this);
        setContentView(R.layout.activity_card_details);
        txt1 = (TextView)findViewById(R.id.nameText3);
        txt2 = (TextView)findViewById(R.id.price3);
        txt3 = (TextView)findViewById(R.id.discountPrice4);
        txt4 = (TextView)findViewById(R.id.discountPrice5);
        swap = (Button)findViewById(R.id.swap);
        Pay = (Button)findViewById(R.id.popupbtn);
        ref = FirebaseDatabase.getInstance().getReference().child("paymentCards");

        String cardKey = getIntent().getStringExtra("cardKey");

        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CardDetails.this, Addedcards.class));
            }
        });

        Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder dialog = new AlertDialog.Builder(CardDetails.this);
                dialog.setTitle("Confirm payment");
                dialog.setMessage("Confirm the payment ?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextView textclose;
                        Button btnfollow;
                        myDialog.setContentView(R.layout.payment_popup);
                        textclose = (TextView) myDialog.findViewById(R.id.txtclose);
                        btnfollow = (Button) myDialog.findViewById(R.id.popupbtn);
                        textclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myDialog.dismiss();
                            }
                        });
                        myDialog.show();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        ref.child(cardKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String cardname = snapshot.child("cardname").getValue().toString();
                    String number = snapshot.child("decNumber").getValue().toString();
                    String expdate = snapshot.child("expdate").getValue().toString();
                    String cvv = snapshot.child("expdate").getValue().toString();

                    txt1.setText(cardname);
                    txt2.setText(number);
                    txt3.setText(expdate);
                    txt4.setText(cvv);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    public void ShowPopup(View v){
//        TextView textclose;
//        Button btnfollow;
//        myDialog.setContentView(R.layout.payment_popup);
//        textclose = (TextView) myDialog.findViewById(R.id.txtclose);
//        btnfollow = (Button) myDialog.findViewById(R.id.popupbtn);
//        textclose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myDialog.dismiss();
//            }
//        });
//        myDialog.show();
//
//    }


}