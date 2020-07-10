package com.ovchinnikovm.android.vktop.lib;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class GlideImageLoader implements ImageLoader{
    private Picasso picasso;

    public GlideImageLoader(Picasso picasso) {
        this.picasso = picasso;
    }

    @Override
    public void loadIcon(ImageView imageView, String URL) {
        picasso
                .load(URL)
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
                //.apply(RequestOptions
                //        .circleCropTransform()
                //        .dontAnimate())
                .into(imageView);
    }

    @Override
    public void loadImage(ImageView imageView, String URL) {
        picasso
                .load(URL)
                //.apply(RequestOptions
                //        .noTransformation()
                //        .skipMemoryCache(true)
                //        .override(600))
                .into(imageView);
    }
}
