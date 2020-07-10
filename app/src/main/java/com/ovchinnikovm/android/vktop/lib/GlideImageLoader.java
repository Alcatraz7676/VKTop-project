package com.ovchinnikovm.android.vktop.lib;

import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;

public class GlideImageLoader implements ImageLoader{
    private RequestManager glideRequestManager;

    public GlideImageLoader(RequestManager glideRequestManager) {
        this.glideRequestManager = glideRequestManager;
    }

    @Override
    public void loadIcon(ImageView imageView, String URL) {
        glideRequestManager
                .load(URL)
                .apply(RequestOptions
                        .circleCropTransform()
                        .dontAnimate())
                .into(imageView);
    }

    @Override
    public void loadImage(ImageView imageView, String URL) {
        glideRequestManager
                .load(URL)
                .apply(RequestOptions
                        .noTransformation()
                        .skipMemoryCache(true)
                        .override(600))
                .into(imageView);
    }
}
