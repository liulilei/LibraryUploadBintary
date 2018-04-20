package test.library.libraryuploadbintrary;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppUtils {

    /**
     * @return 获取应用版本号。
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 1;

        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 1).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    /**
     * Get version name for application.
     *
     * @param context
     * @return 获取应用版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "1.0";

        try {
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 1).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

//    /**
//     * @param context
//     * @return 获取手机的imei值，手机的唯一标示
//     */
//    public static String getDeviceImei(Context context) {
//        return ((TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE)).getDeviceId();
//    }

    /**
     * @return 获取手机当前版本号
     */
    public static String getDeviceVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机机型
     *
     * @return
     */
    public static String getDeviceName() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context context, String key) {
        if (TextUtils.isEmpty(key)) {
            return "";
        }
        String resultData = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key) + "";
                    }
                }
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        return resultData;
    }

    /**
     * 打开一个apk的安装包
     */
    public static void installApk(Context context, String dir, String fileName) {
        if (TextUtils.isEmpty(dir) || TextUtils.isEmpty(fileName)) {
            return;
        }
        File fileDir = new File(dir);
        File file = new File(dir, fileName);
        if (!fileDir.exists()) {
            return;
        }
        if (file.exists()) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
    }

    /**
     * 获取应用所有的包名
     *
     * @param context 上下文
     * @return
     */
    public final static List<String> getAllPackName(Context context) {
        PackageManager packManager = context.getPackageManager();
        List<PackageInfo> packInfos = packManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        if (packInfos == null || packInfos.size() == 0) {
            return null;
        }
        List<String> pkList = new ArrayList<String>();
        for (PackageInfo packInfo : packInfos) {
            String packageName = packInfo.packageName;
            pkList.add(packageName);
        }
        return pkList;
    }

    /**
     * whether application is in background
     * <ul>
     * <li>need use permission android.permission.GET_TASKS in Manifest.xml</li>
     * </ul>
     *
     * @param context 上下文
     * @return if application is in background return true, otherwise return
     * false
     */
    public static boolean isApplicationInBackground(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty()) {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null
                    && !topActivity.getPackageName().equals(
                    context.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    public static boolean isActivityLunch(Context context, String clz) {
        boolean isActive = false;
        if (!TextUtils.isEmpty(clz)) {
            ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.getClassName().equals(clz)) { // 说明它已经启动了
                    isActive = true;
                    break;
                }
            }
        }
        return isActive;
    }

    /**
     * 检测当前是否为主线程
     *
     * @return true 是
     */
    public static boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

}
