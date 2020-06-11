package com.sssproductions.profilecollection;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemHolder extends RecyclerView.ViewHolder {

    ImageView iv_thumb;
    TextView tv_name, tv_age, tv_occupation, tv_gotra;

    public ItemHolder(@NonNull View itemView) {
        super(itemView);

        iv_thumb = itemView.findViewById(R.id.item_image);
        tv_name = itemView.findViewById(R.id.item_name);
        tv_age = itemView.findViewById(R.id.item_age);
        tv_occupation = itemView.findViewById(R.id.item_occupation);
        tv_gotra = itemView.findViewById(R.id.item_gotra);

    }
}
