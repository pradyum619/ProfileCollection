package com.sssproductions.profilecollection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sssproductions.profilecollection.Models.ProfileModel;

import java.util.HashMap;

public class EditProfile extends AppCompatActivity {

    private EditText name, age, mobile, city, occupation, gotra, dob, birth;
    private ImageView profile, backBtn;
    private Button addBtn;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private StorageReference Folder;
    private static final int IMAGE_BACK = 12;
    private String img_url;
    private Uri ImageData;
    private ProfileModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("data").child(user.getUid());
        Folder = FirebaseStorage.getInstance().getReference().child(user.getUid());

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
        ImageUpload();
    }

    private void ImageUpload(){
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,IMAGE_BACK);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_BACK){
            if (resultCode == RESULT_OK){
                ImageData = data.getData();
            }
        }
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
                     TextUtils.isEmpty(birthStr) &&
                     ImageData == null){

                    Toast.makeText(EditProfile.this, "Please Fill all the Details with Image to upload", Toast.LENGTH_SHORT).show();

                } else {

                    //Start Loading Box
                    final ProgressDialog pDialog = new ProgressDialog(EditProfile.this);
                    pDialog.setMessage("Please Wait ...");
                    pDialog.setIndeterminate(true);
                    pDialog.setCancelable(false);
                    pDialog.show();

                    //create model object to store in database
                    model = new ProfileModel();
                    model.setName(nameStr);
                    model.setAge(AgeStr);
                    model.setNumber(mobStr);
                    model.setCity(cityStr);
                    model.setOccupation(occupationStr);
                    model.setGotra(gotraStr);
                    model.setBirthPlace(birthStr);
                    model.setDob("13-09-1774");

                    //storage ref for image to upload
                    final StorageReference ImageName = Folder.child(model.getNumber()).child("image"+ImageData.getLastPathSegment());

                    //Image uploading
                    ImageName.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(EditProfile.this, "UPLOADED", Toast.LENGTH_SHORT).show();

                            ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    img_url = String.valueOf(uri);
                                    model.setImg_url(img_url);

                                    //Start Sending Data

                                    databaseReference.child(model.getNumber()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(EditProfile.this, "Profile Saved", Toast.LENGTH_SHORT).show();
                                            pDialog.dismiss();
                                            finish();
                                        }
                                    });
                                }
                            });

                        }
                    });


                }
            }
        });
    }
}