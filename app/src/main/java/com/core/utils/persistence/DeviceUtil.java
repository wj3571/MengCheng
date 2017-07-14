package com.core.utils.persistence;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.ViewConfiguration;

import com.google.gson.Gson;
import com.mengcheng.application.MengChengApp;

import java.io.File;
import java.util.Locale;

/**
 * 设备相关
 *
 * @author Guojie
 */
public class DeviceUtil {
    public static String getPhoneNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        String number = tm.getLine1Number();
        return number;
    }

    public static String getLanguageInfo() {
        String language = MengChengApp.getInstanse()
                .getResources()
                .getConfiguration().locale.getLanguage();
        if (language.toLowerCase(Locale.getDefault()).contains("zh")) {
            return "CN";
        } else if (language.toLowerCase(Locale.getDefault()).contains("tw")) {
            return "TW";
        } else {
            return "US";
        }
    }

    public static int getStatusBarHeight(Context context) {
        Rect rect = new Rect();
        ((Activity) context).getWindow()
                .getDecorView()
                .getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    public static int getActionBarHeight(Context context) {

        final TypedArray ta = context.getTheme().obtainStyledAttributes(
                new int[] {android.R.attr.actionBarSize});
        int actionBarHeight = (int) ta.getDimension(0, 0);
        return actionBarHeight;
    }

    public static int getScreenWidth(Activity context) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        context.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(mDisplayMetrics);
        return mDisplayMetrics.widthPixels;
    }

    //该方法获取高度  包括 状态栏   但是不包括底部虚拟按键
    public static int getScreenHeight(Activity context) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        context.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(mDisplayMetrics);
        return mDisplayMetrics.heightPixels;
    }

    //获取导航栏高度
    public static int getNavigationBarHeight(Activity context) {
        if (!checkDeviceHasNavigationBar(context)) {
            return 0;
        }
        Resources resources = context.getResources();
        int resourceId =
                resources.getIdentifier("navigation_bar_height", "dimen",
                        "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dpToPx(Resources res, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                res.getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     */
    public static final int spToPx(Context context, float sp) {
        final float scaledDensity =
                context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scaledDensity + 0.5f);
    }

    /**
     * 判断有无navigationbar
     */
    public static boolean checkDeviceHasNavigationBar(Context activity) {

        //Emulator
        if (Build.FINGERPRINT.startsWith("generic"))
            return true;

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(activity)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }

    /**
     * 是否为横屏
     *
     * @param context
     * @return
     */
    public static boolean isLandscape(Context context) {
        Configuration configuration = context.getResources().getConfiguration(); //获取设置的配置信息
        int ori = configuration.orientation; //获取屏幕方向

        return ori == configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 屏幕高宽比
     *
     * @param context
     * @return
     */
    public static float getScreenRate(Activity context) {
        int w = getScreenWidth(context);
        int h = getScreenHeight(context);
        return h / w;
    }

    public static void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    /**
     * 扫描、刷新相册,通知媒体库更新单个文件状态
     */
    public static void scanPhotos(String filePath, Context context)
            throws NullPointerException {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(filePath));
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) MengChengApp.getInstanse()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return current.isAvailable();
    }

    public static String getPushAlias() {
        String tel = FastData.getPhone();
        return tel + "_" + DeviceUtil.getIMEI(MengChengApp.getInstanse());
    }

    /**
     * 获取手机IMEI号
     */
    public static String getIMEI(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            return null;
        }
        TelephonyManager telephonyManager = (TelephonyManager)
                context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    public static String getMobilNumber() {
        TelephonyManager tm = (TelephonyManager)MengChengApp.getInstanse()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }

    public static String getDeviceInfo() {

        DeviceInfo info = new DeviceInfo();
        info.Model = Build.MODEL;
        info.pushAlias = getPushAlias();
        info.osVersionCode = Build.VERSION.SDK_INT;
        info.osVersionName = Build.VERSION.RELEASE;
        info.mobileNumbers = getMobilNumber();
        return new Gson().toJson(info);
    }

    private static class DeviceInfo {

        /**
         * 推送别名
         */
        String pushAlias;
        /**
         * 手机型号
         */
        String Model;
        /**
         * 系统版本号
         */
        int osVersionCode;
        /**
         * 版本号别名
         */
        String osVersionName;

        /**
         * 当前设备手机号
         */
        String mobileNumbers;
    }
}