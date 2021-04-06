package com.example.shanyu.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.shanyu.MyApplication;

import java.security.MessageDigest;

import jp.wasabeef.glide.transformations.internal.FastBlur;


/**
 * 作者： HeroCat
 * 时间：2019/3/4/004
 * 描述：
 */
public class ImageLoaderUtil {

    public static void loadImageBlurTransformation(String path, ImageView view) {
        Glide.with(MyApplication.context)
                .load(path)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(15, 1)))
                .into(view);

    }

    public static void loadImage(String path, ImageView view) {
        if (!StringUtil.isEmpty(path))
            Glide.with(MyApplication.context).load(path).into(view);
    }

    public static void loadImage(String path, ImageView view, int defImage) {
        if (StringUtil.isEmpty(path)) return;
        Glide.with(MyApplication.context).load(path).apply(new RequestOptions().error(defImage)).into(view);
    }

    public static void loadImage(String path, final ImageView view, final float n) {
        if (StringUtil.isEmpty(path)) return;
        Glide.with(MyApplication.context).asBitmap().load(path).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(MyApplication.context.getResources(), resource);//传入bitmap
                roundedDrawable.setCornerRadius(n);
                view.setImageDrawable(roundedDrawable);
            }
        });
    }


    public static void getBitMap(String path1, BitMapinterface mapinterface) {
        Glide.with(MyApplication.context).asBitmap().load(path1).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                mapinterface.getBitMap(resource);
            }
        });
    }

    public interface BitMapinterface {
        void getBitMap(Bitmap resource);
    }

    public static class BlurTransformation extends BitmapTransformation {

        private static final int VERSION = 1;
        private static final String ID = "BlurTransformation." + VERSION;

        private static int MAX_RADIUS = 25;
        private static int DEFAULT_DOWN_SAMPLING = 1;

        private int radius;
        private int sampling;

        public BlurTransformation() {
            this(MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
        }

        public BlurTransformation(int radius) {
            this(radius, DEFAULT_DOWN_SAMPLING);
        }

        public BlurTransformation(int radius, int sampling) {
            this.radius = radius;
            this.sampling = sampling;
        }

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            int width = toTransform.getWidth();
            int height = toTransform.getHeight();
            int scaledWidth = width / sampling;
            int scaledHeight = height / sampling;

            Bitmap bitmap = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            canvas.scale(1 / (float) sampling, 1 / (float) sampling);
            Paint paint = new Paint();
            paint.setFlags(Paint.FILTER_BITMAP_FLAG);
            canvas.drawBitmap(toTransform, 0, 0, paint);
            bitmap = FastBlur.blur(bitmap, radius, true);

            return bitmap;
        }

        @Override
        public String toString() {
            return "BlurTransformation(radius=" + radius + ", sampling=" + sampling + ")";
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof BlurTransformation &&
                    ((BlurTransformation) o).radius == radius &&
                    ((BlurTransformation) o).sampling == sampling;
        }

        @Override
        public int hashCode() {
            return ID.hashCode() + radius * 1000 + sampling * 10;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            messageDigest.update((ID + radius + sampling).getBytes(CHARSET));
        }
    }

}
