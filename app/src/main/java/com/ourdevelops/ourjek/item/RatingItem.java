package com.ourdevelops.ourjek.item;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ornolfr.ratingview.RatingView;
import com.ourdevelops.ourjek.R;
import com.ourdevelops.ourjek.activity.RentCarActivity;
import com.ourdevelops.ourjek.activity.RideCarActivity;
import com.ourdevelops.ourjek.activity.SendActivity;
import com.ourdevelops.ourjek.constants.Constants;
import com.ourdevelops.ourjek.models.PromoModel;
import com.ourdevelops.ourjek.models.RatingModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by otacodes on 3/24/2019.
 */

public class RatingItem extends PagerAdapter {

    private List<RatingModel> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public RatingItem(List<RatingModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_review, container, false);

        TextView name, tanggal;
        ImageView images;
        RatingView rating;
        TextView catatan;

        images = view.findViewById(R.id.userimages);
        name = view.findViewById(R.id.fullname);
        tanggal = view.findViewById(R.id.datetxt);
        catatan = view.findViewById(R.id.message);
        rating = view.findViewById(R.id.ratingView);

        final RatingModel singleItem = models.get(position);
        name.setText(singleItem.getFullnama());
        if (!singleItem.getFotopelanggan().isEmpty()) {
            Picasso.with(context)
                    .load(Constants.IMAGESUSER + singleItem.getFotopelanggan())
                    .resize(100, 100)
                    .into(images);
        }
        Date myDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.US);
        try {
            myDate = dateFormat.parse(singleItem.getUpdate_at());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        String finalDate = timeFormat.format(myDate);
        tanggal.setText(finalDate);

        catatan.setText(singleItem.getCatatan());

        if (!singleItem.getRating().equals("null"))
            rating.setRating(Float.parseFloat(singleItem.getRating()));


        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
