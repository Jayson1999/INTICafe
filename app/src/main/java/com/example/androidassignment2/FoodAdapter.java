package com.example.androidassignment2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//FoodAdapter class to store an ArrayList of Foods and Drinks to pass them into GridView
public class FoodAdapter extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<Food> foods;

    //overloaded constructor passing the foods and drinks
    public FoodAdapter(Context context, ArrayList<Food> foods) {
        this.mContext = context;
        this.foods = foods;
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Food food = foods.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.food_layout, null);
        }

        final ImageView foodImg = (ImageView)convertView.findViewById(R.id.foodImg);
        final TextView foodLayoutPrice = (TextView)convertView.findViewById(R.id.foodlayoutPrice);
        final TextView foodLayoutName = (TextView)convertView.findViewById(R.id.foodlayoutName);

        foodImg.setImageResource(food.getImageRes());
        foodLayoutName.setText(food.getName());
        foodLayoutPrice.setText(String.format("%s%.2f","RM",food.getPrice()));

        return convertView;
    }
}
