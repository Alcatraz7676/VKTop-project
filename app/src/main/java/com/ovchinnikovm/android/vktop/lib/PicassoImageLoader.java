package com.ovchinnikovm.android.vktop.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class PicassoImageLoader implements ImageLoader {

    public static final String POST_IMAGE_TAG = "post_image_tag";
    public static final String GROUP_ICON_TAG = "group_icon_tag";

    private Picasso picasso;
    private Context context;

    public PicassoImageLoader(Picasso picasso, Context context) {
        this.picasso = picasso;
        this.context = context;
    }

    @Override
    public void loadIcon(ImageView imageView, String URL) {
        picasso
                .load(URL)
                .placeholder(ContextCompat.getDrawable(context, R.drawable.group_oval))
                .error(ContextCompat.getDrawable(context, R.drawable.group_oval))
                .tag(GROUP_ICON_TAG)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), imageBitmap);
                        imageDrawable.setCircular(true);
                        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                        imageView.setImageDrawable(imageDrawable);
                    }

                    @Override
                    public void onError(Exception e) {
                        imageView.setImageResource(R.drawable.group_oval);
                        Crashlytics.log("Error with making round image. Text of error: " + e.getMessage());
                    }
                });
    }

    @Override
    public void loadImage(ImageView imageView, String URL, Double heightToWidthRatio) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(ContextCompat.getColor(context, R.color.photoBackground));
        int height = (int) (604 * heightToWidthRatio);
        gd.setSize(604, height);
        picasso
                .load(URL)
                .placeholder(gd)
                .error(R.drawable.image_placeholder)
                .tag(POST_IMAGE_TAG)
                .into(imageView);
    }

    @Override
    public void loadRecyclerViewImage(ImageView imageView, String URL) {
        picasso
                .load(URL)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .tag(POST_IMAGE_TAG)
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {
                        int targetHeight = (int) context.getResources().getDimension(R.dimen.photos_recyclerview_height);

                        double aspectRatio = (double) source.getWidth() / (double) source.getHeight();
                        int targetWidth = (int) (targetHeight * aspectRatio);
                        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                        if (result != source) {
                            // Same bitmap is returned if sizes are the same
                            source.recycle();
                        }
                        return result;
                    }

                    @Override
                    public String key() {
                        return "transformation" + " desiredWidth";
                    }
                })
                .into(imageView);
    }
}
