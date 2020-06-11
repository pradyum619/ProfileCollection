package com.sssproductions.profilecollection.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

public class MyPagerAdapterInfo extends PagerAdapter {

    private LayoutInflater inflator;
    private int[]layouts;
    private Context context;

    public MyPagerAdapterInfo(int[] layouts, Context context) {
        this.layouts = layouts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(layouts[position],container,false);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem( ViewGroup container, int position, Object object) {
        View v = (View)object;
        container.removeView(v);
    }
}