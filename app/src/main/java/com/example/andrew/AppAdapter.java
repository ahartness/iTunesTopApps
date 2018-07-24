package com.example.andrew.midterm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by andrew on 10/24/16.
 */

public class AppAdapter extends ArrayAdapter<App>{

    List<App> mData;
    Context mContext;
    int mResource;

    public AppAdapter(Context context, int resource, List<App> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }

        App app = mData.get(position);

        ImageView imgView = (ImageView) convertView.findViewById(R.id.icon);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        ImageView priceIcon = (ImageView) convertView.findViewById(R.id.priceIcon);

        title.setText(app.getName());
        price.setText(app.getPrice());
        //priceIcon.setImageDrawable(R.drawable.);

        double numPrice = Float.valueOf(app.getPrice());
/*
        i)   One dollar icon, for apps with prices from $0 to $1.99.
        ii)  Two dollar icon, for apps with prices from $2.00 to $5.99.
        iii) Three dollar icon, for apps with prices greater than or equal to $6.00.

        imageview= (ImageView)findViewById(R.id.imageView);
        Drawable res = getResources().getDrawable(imageResource);
        imageView.setImageDrawable(res);
*/
        String low = "@drawable/price_low";
        String medium = "@drawable/price_medium";
        String high = "@drawable/price_high";

        int lowResource = mContext.getResources().getIdentifier(low, null, mContext.getPackageName());
        int medResource = mContext.getResources().getIdentifier(medium, null, mContext.getPackageName());
        int highResource = mContext.getResources().getIdentifier(high, null, mContext.getPackageName());

        if(numPrice <= 1.99){
            priceIcon.setImageDrawable(mContext.getResources().getDrawable(lowResource));
        }
        if(numPrice > 1.99 && numPrice <= 5.99){
            priceIcon.setImageDrawable(mContext.getResources().getDrawable(medResource));
        }
        if(numPrice > 5.99){
            priceIcon.setImageDrawable(mContext.getResources().getDrawable(highResource));
        }

        Picasso.with(mContext).load(app.getImg()).into(imgView);

        return convertView;
    }
}
