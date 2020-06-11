package com.sssproductions.profilecollection;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.sssproductions.profilecollection.Models.ProfileModel;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView rv_items;
    FirebaseRecyclerAdapter  adapter;
    FloatingActionButton addBtnFab;
    FirebaseUser user;


    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //binding
        addBtnFab = view.findViewById(R.id.addFabBtn);
        rv_items = view.findViewById(R.id.rv_items);
        rv_items.setHasFixedSize(true);
        rv_items.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));

        setData();
        fabClicked();
        rv_items.setAdapter(adapter);


        return view;
    }

    private void fabClicked() {
        addBtnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), EditProfile.class));
            }
        });
    }

    private void setData() {
        //Log.d("CALLED","SET DATA");
        //reference to db
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("data")
                .child(user.getUid());

        FirebaseRecyclerOptions<ProfileModel> options =
                new FirebaseRecyclerOptions.Builder<ProfileModel>()
                        .setQuery(query, ProfileModel.class)
                        .setLifecycleOwner(this)
                        .build();

        adapter = new FirebaseRecyclerAdapter<ProfileModel, ItemHolder>(options) {
            @Override
            public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);

                return new ItemHolder(view);
            }

            @Override
            protected void onBindViewHolder(ItemHolder holder, int position, final ProfileModel model) {
                holder.tv_name.setText(model.getName());
                String age = ("" + model.getAge() + " Yrs.");
                holder.tv_age.setText(age);
                holder.tv_gotra.setText(model.getGotra());
                holder.tv_occupation.setText(model.getOccupation());


                Glide.with(getContext())
                        .load(model.getImg_url())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .placeholder(R.drawable.progress_anim)
                        .into(holder.iv_thumb);

                //implementing on Click Item
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), ""+model.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), UserDetailActivity.class);
                        intent.putExtra("model",model);
                        startActivity(intent);
                    }
                });
            }

        };
    }
}