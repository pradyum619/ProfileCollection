package com.sssproductions.profilecollection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sssproductions.profilecollection.Models.ProfileModel;

public class UserDetailActivity extends AppCompatActivity {

    private TextView name, age, mobile, city, occupation, gotra, dob, birth;
    private ImageView profile, backBtn;
    private ProfileModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        model = (ProfileModel) getIntent().getSerializableExtra("model");

        //bind layout
        backBtn = findViewById(R.id.back_img_profile);
        profile = findViewById(R.id.img_profile);
        name = findViewById(R.id.name_tv_profile);
        age = findViewById(R.id.age_tv_profile);
        mobile = findViewById(R.id.mob_tv_profile);
        city = findViewById(R.id.city_tv_profile);
        occupation = findViewById(R.id.occupation_tv_profile);
        gotra = findViewById(R.id.gotra_tv_profile);
        dob = findViewById(R.id.dob_tv_profile);
        birth = findViewById(R.id.birth_tv_profile);

        setData();
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
    private void setData() {
        name.setText(model.getName());
        age.setText(model.getAge());
        mobile.setText(model.getNumber());
        city.setText(model.getCity());
        occupation.setText(model.getOccupation());
        gotra.setText(model.getGotra());
//        dob.setText(model.get);
        birth.setText(model.getBirthPlace());

        //image
        Glide.with(this)
                .load(model.getImg_url())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.progress_anim)
                .into(profile);

    }
}