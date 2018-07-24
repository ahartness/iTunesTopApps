package com.example.andrew.midterm;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by andrew on 10/24/16.
 */

public class FilteredAdapter extends RecyclerView.Adapter<FilteredAdapter.ViewHolder>{

    private List<App> mList;
    private static Context mContext;

    public FilteredAdapter(Context context, List<App> apps){
        mList = apps;
        mContext = context;
    }

    @Override
    public FilteredAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.filtered_item_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        App app = mList.get(position);

        double numPrice = Float.valueOf(app.getPrice());

        holder.tvTitle.setText(app.getName());
        holder.tvPrice.setText(app.getPrice());

        String low = "@drawable/price_low";
        String medium = "@drawable/price_medium";
        String high = "@drawable/price_high";

        int lowResource = mContext.getResources().getIdentifier(low, null, mContext.getPackageName());
        int medResource = mContext.getResources().getIdentifier(medium, null, mContext.getPackageName());
        int highResource = mContext.getResources().getIdentifier(high, null, mContext.getPackageName());

        if(numPrice <= 1.99)
            holder.rvPriceIcon.setImageDrawable(mContext.getResources().getDrawable(lowResource));
        if(numPrice > 1.99 && numPrice <= 5.99)
            holder.rvPriceIcon.setImageDrawable(mContext.getResources().getDrawable(medResource));
        if(numPrice > 5.99)
            holder.rvPriceIcon.setImageDrawable(mContext.getResources().getDrawable(highResource));


        Picasso.with(mContext).load(app.getImg()).resize(600, 600).centerInside().into(holder.mainIcon);

        holder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvTitle, tvPrice;
        public ImageView mainIcon, rvPriceIcon;
        public ImageButton trash;

        public ViewHolder(View itemView) {
            super(itemView);

            int scrWidth = ((Activity)mContext).getWindowManager().getDefaultDisplay().getWidth();
            itemView.getLayoutParams().width = scrWidth / 2;

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);

            mainIcon = (ImageView) itemView.findViewById(R.id.mainIcon);
            rvPriceIcon = (ImageView) itemView.findViewById(R.id.rvPriceIcon);

            trash = (ImageButton) itemView.findViewById(R.id.trash);
        }
    }

    public void addItemToList(App app){
        mList.add(app);
    }

}
