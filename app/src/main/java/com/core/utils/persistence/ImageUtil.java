package com.core.utils.persistence;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class ImageUtil {


    private static final String TAG = "ImageUtil";

    /**
     * Display image with uri
     *
     * @param context   context
     * @param uri       uri
     * @param imageView target imageView
     */
    public static void displayImage(Context context, @NonNull String uri, ImageView imageView) {
        log(uri);
        Glide.with(context)
                .load(uri)
                .thumbnail(0.05f)
                .crossFade()
                .centerCrop()
                .error(android.R.mipmap.sym_def_app_icon)
                .into(imageView);
    }

/*    public static void displayImageResize(@NonNull String uri, ImageView imageView, int resizeWidth) {
        log(uri);
        Glide.with(imageView.getContext())
                .load(uri)
                .thumbnail(0.05f)
                .crossFade()
                .centerCrop()
                .error(android.R.mipmap.sym_def_app_icon)
                .into(imageView);
    }

    *//**
     * display image without context
     *
     * @param uri       uri
     * @param imageView image view
     *//*
    public static void displayImage(@NonNull String uri, ImageView imageView) {
        log(uri);
        Glide.with(imageView.getContext())
                .load(uri)
                .thumbnail(0.05f)
                .crossFade()
                .centerCrop()
                .error(R.mipmap.aaaaa_h)
                .into(imageView);
    }
    *//**
     * display image without context
     *
     * @param uri       uri
     * @param imageView image view
     *//*
    public static void displayImageRectF(@NonNull String uri, ImageView imageView,int dp) {
        log(uri);
        Glide.with(imageView.getContext())
                .using(new OssFixedParamsModel(imageView.getContext()))
                .load(uri)
                .thumbnail(0.05f)
                .crossFade()
                .centerCrop()
                .transform(new GlideRoundTransform(imageView.getContext(),dp))
                .error(R.mipmap.aaaaa_h)
                .into(imageView);
    }


    *//**
     * display image with default resource placeholder
     * @param context context
     * @param uri uri
     * @param imageView image view
     * @param placeholderResource place holder resource id
     *//*
    public static void displayImage(Context context, @NonNull String uri, ImageView imageView, @DrawableRes int placeholderResource) {
        log(uri);
        Glide.with(context)
                .using(new OssFixedParamsModel(imageView.getContext()))
                .load(uri)
                .thumbnail(0.05f)
                .placeholder(placeholderResource)
                .error(placeholderResource)
                .crossFade()
                .centerCrop()
                .into(imageView);
    }

    *//**
     * display image without context
     * @param uri uri
     *//*
    public static void displayImage(@NonNull String uri, ImageView imageView, @DrawableRes int placeholderResource) {
        log(uri);
        Glide.with(imageView.getContext())
                .using(new OssFixedParamsModel(imageView.getContext()))
                .load(uri)
                .thumbnail(0.05f)
                .placeholder(placeholderResource)
                .error(placeholderResource)
                .crossFade()
                .centerCrop()
                .into(imageView);
    }
*/
    private static void log(String uri) {
        if (!TextUtils.isEmpty(uri)) {
            Log.d(TAG, uri);
        }
    }

/*    private static String processUri(String uri, int width) {
        if (BuildConfig.DEBUG) {
            Assert.assertTrue("width must be > 0 .", width > 0);
        }
        return uri + "?x-oss-process=image/resize,m_fixed,w_" + width;
    }*/
}
