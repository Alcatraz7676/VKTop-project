package com.ovchinnikovm.android.vktop.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import static com.vk.sdk.VKUIHelper.getApplicationContext;

public class PicassoImageLoader implements ImageLoader {
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
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {
                        int size = Math.min(source.getWidth(), source.getHeight());

                        int x = (source.getWidth() - size) / 2;
                        int y = (source.getHeight() - size) / 2;

                        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
                        if (squaredBitmap != source) {
                            source.recycle();
                        }

                        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

                        Canvas canvas = new Canvas(bitmap);
                        Paint paint = new Paint();
                        BitmapShader shader = new BitmapShader(squaredBitmap,
                                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
                        paint.setShader(shader);
                        paint.setAntiAlias(true);

                        float r = size / 2f;
                        canvas.drawCircle(r, r, r, paint);

                        squaredBitmap.recycle();
                        return bitmap;
                    }

                    @Override
                    public String key() {
                        return "circle";
                    }
                })
                .into(imageView);
    }

    @Override
    public void loadImage(ImageView imageView, String URL) {
        picasso
                .load(URL)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(imageView);
    }

    @Override
    public void loadRecyclerViewImage(ImageView imageView, String URL) {
        picasso
                .load(URL)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {
                        int targetWidth = (int) getApplicationContext().getResources().getDimension(R.dimen.photos_recyclerview_height);

                        double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                        int targetHeight = (int) (targetWidth * aspectRatio);
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
