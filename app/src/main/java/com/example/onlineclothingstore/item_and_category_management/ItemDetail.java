package com.example.onlineclothingstore.item_and_category_management;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.onlineclothingstore.R;
import com.example.onlineclothingstore.cart_and_order_management.CartActivity;
import com.example.onlineclothingstore.cart_and_order_management.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.onlineclothingstore.R;
import com.google.android.material.imageview.ShapeableImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

//import static android.content.ContentValues.TAG;

public class ItemDetail extends AppCompatActivity {


    public static String TAG = "TAG";
    private Button  btn,back;
    private ShapeableImageView shapeableImageView;
    private ElegantNumberButton numberButton;
//    private TextView txt1, txt2, txt3;
    //private  String productID= "";
    public String itemkey="",qty="";
    public String imgUrl ="";





    TextView txt1,txt2,txt3,txt4;


    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_item_detail);


        shapeableImageView = (ShapeableImageView)findViewById(R.id.img3);
        txt1 = (TextView)findViewById(R.id.nameText3);
        txt2 = (TextView)findViewById(R.id.price3);
        txt3 = (TextView)findViewById(R.id.discountPrice3);
        txt4 = (TextView)findViewById(R.id.descri);

        ref = FirebaseDatabase.getInstance().getReference().child("Products");

         itemkey = getIntent().getStringExtra("itemKey");
        // qty = getIntent().getStringExtra("qty");
         //if(qty=="") numberButton.setNumber(qty);


        //addToCartBtn = (Button) findViewById(R.id.add_product_to_cart_btn);
        numberButton = (ElegantNumberButton)findViewById(R.id.number_btn);
        txt3 = (TextView)findViewById(R.id.discountPrice3);
        txt2 = (TextView)findViewById(R.id.price3);
        txt1 = (TextView)findViewById(R.id.nameText3);
        shapeableImageView = (ShapeableImageView)findViewById(R.id.img3);
        btn = (Button)findViewById(R.id.btnDelete3);

        //getProductDetails(productID);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingtoToCartList();
            }
        });


        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("Products");
        ref.child(itemkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                


                  if(snapshot.exists()){
                      String itemName = snapshot.child("itemName").getValue().toString();
                      String itemprice = snapshot.child("iprice").getValue().toString();
                      String itemdis = snapshot.child("idiscountPrice").getValue().toString();
                      String itemimage = snapshot.child("image").getValue().toString();
                      String itemdes = snapshot.child("idescription").getValue().toString();

                      Picasso.get().load(itemimage).into(shapeableImageView);
                      txt1.setText(itemName);
                      txt2.setText(itemprice);
                      txt3.setText(itemdis);
                      txt4.setText(itemdes);
                      imgUrl = itemimage;

                      //Navigation to main
//                      btn.setOnClickListener(new View.OnClickListener() {
//                          @Override
//                          public void onClick(View v) {
//                              Intent int1 = new Intent(ItemDetail.this,DisplayCategory.class);
//                              startActivity(int1);
//                          }
//                      });
                  }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void addingtoToCartList() {

        String SaveCurrentTime,SaveCurrentDate;

           Calendar calForDate =  Calendar.getInstance();
          SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            SaveCurrentDate = currentDate.format(calForDate.getTime());

       SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
       SaveCurrentTime = currentTime.format(calForDate.getTime());

        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid",itemkey);
        cartMap.put("pname",txt1.getText().toString());
        cartMap.put("price",txt2.getText().toString());
        cartMap.put("date",SaveCurrentDate);
        cartMap.put("time",SaveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("discount",txt3.getText().toString());
        cartMap.put("image",imgUrl);

        cartListRef.child("User View").child(FirebaseAuth.getInstance().getUid())
                .child("Products").child(itemkey)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(ItemDetail.this, "Added to Cart List.",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ItemDetail.this, CartActivity.class);
                            startActivity(intent);
                        }
                    }
                });

    }


    //to  stop app get close when pressing back key
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent int1 = new Intent(ItemDetail.this, DisplayItemsUser.class);
        startActivity(int1);
    }



    }
