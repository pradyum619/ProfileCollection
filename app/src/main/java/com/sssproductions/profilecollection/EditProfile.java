package com.sssproductions.profilecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sssproductions.profilecollection.Models.ProfileModel;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity {

    private EditText name, age, mobile, city, occupation, gotra, dob, birth;
    private ImageView profile, backBtn;
    private HashMap hashMap;
    private Button addBtn;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("data").child(user.getUid());

        //bind layout
        addBtn = findViewById(R.id.addBtn);
        backBtn = findViewById(R.id.back_img_addProfile);
        profile = findViewById(R.id.img_addProfile);
        name = findViewById(R.id.name_tv_addProfile);
        age = findViewById(R.id.age_tv_addProfile);
        mobile = findViewById(R.id.mob_tv_addProfile);
        city = findViewById(R.id.city_tv_addProfile);
        occupation = findViewById(R.id.occupation_tv_addProfile);
        gotra = findViewById(R.id.gotra_tv_addProfile);
        dob = findViewById(R.id.dob_tv_addProfile);
        birth = findViewById(R.id.birth_tv_addProfile);

        sendData();
        backClicked();
    }

    private void backClicked(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void sendData() {

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = name.getText().toString().trim();
                String AgeStr = age.getText().toString().trim();
                String mobStr = mobile.getText().toString().trim();
                String cityStr = city.getText().toString().trim();
                String occupationStr = occupation.getText().toString().trim();
                String gotraStr = gotra.getText().toString().trim();
                String birthStr = birth.getText().toString().trim();
                //String dobStr = dob.getText().toString().trim();

                //Validate all Edittexts
                if ( TextUtils.isEmpty(nameStr) &&
                     TextUtils.isEmpty(AgeStr) &&
                     TextUtils.isEmpty(mobStr) &&
                     TextUtils.isEmpty(cityStr) &&
                     TextUtils.isEmpty(occupationStr) &&
                     TextUtils.isEmpty(gotraStr) &&
                     TextUtils.isEmpty(birthStr) ){

                    Toast.makeText(EditProfile.this, "Please Fill all the Details with Image to upload", Toast.LENGTH_SHORT).show();
                } else {
                    ProfileModel model = new ProfileModel();
                    model.setName(nameStr);
                    model.setAge(AgeStr);
                    model.setNumber(mobStr);
                    model.setCity(cityStr);
                    model.setOccupation(occupationStr);
                    model.setGotra(gotraStr);
                    model.setBirthPlace(birthStr);
                    model.setImg_url("https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1562018435l/50802861._SX0_SY0_.jpg");
                    model.setDob("13-09-1774");

                    //start Sending Data
                    final ProgressDialog pDialog = new ProgressDialog(EditProfile.this);
                    pDialog.setMessage("Loading ...");
                    pDialog.setIndeterminate(true);
                    pDialog.setCancelable(false);
                    pDialog.show();
                    databaseReference.child(model.getNumber()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(EditProfile.this, "Profile Saved", Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();
                        }
                    });
                }
            }
        });
    }
}