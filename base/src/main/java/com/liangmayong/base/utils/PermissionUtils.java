package com.liangmayong.base.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PermissionUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
@TargetApi(Build.VERSION_CODES.DONUT)
public final class PermissionUtils {

    private PermissionUtils() {
    }


    /**
     * cameraPermissions
     *
     * @param activity activity
     * @param id       id
     * @param listener listener
     */
    public static void cameraPermissions(Activity activity, int id, OnPermissionListener listener) {
        String[] permissionsNeeded = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(activity, id, permissionsNeeded, listener);
    }

    /**
     * recordAudioPermissions
     *
     * @param activity activity
     * @param id       id
     * @param listener listener
     */
    public static void recordAudioPermissions(Activity activity, int id, OnPermissionListener listener) {
        String[] permissionsNeeded = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(activity, id, permissionsNeeded, listener);
    }

    /**
     * filePermissions
     *
     * @param activity activity
     * @param id       id
     * @param listener listener
     */
    public static void filePermissions(Activity activity, int id, OnPermissionListener listener) {
        String[] permissionsNeeded = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(activity, id, permissionsNeeded, listener);
    }

    /**
     * locationPermissions
     *
     * @param activity activity
     * @param id       id
     * @param listener listener
     */
    public static void locationPermissions(Activity activity, int id, OnPermissionListener listener) {
        String[] permissionsNeeded = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        requestPermissions(activity, id, permissionsNeeded, listener);
    }

    // request map
    @SuppressLint("UseSparseArrays")
    private static Map<Integer, Request> integerRequestMap = new HashMap<Integer, Request>();

    /**
     * OnPermissionListener
     *
     * @author LiangMaYong
     * @version 1.0
     */
    public interface OnPermissionListener {

        boolean showDialog(Activity activity, Request request);

        void gotPermissions();

        void rejectPermissions(List<String> rejects);
    }

    /**
     * request
     *
     * @param activity           activity
     * @param requestId          requestId
     * @param permissionsNeeded  permissionsNeeded
     * @param permissionListener permissionListener
     */
    public static void requestPermissions(final Activity activity, int requestId, String[] permissionsNeeded,
                                          OnPermissionListener permissionListener) {
        request(activity, new Request(activity, requestId, permissionsNeeded, permissionListener));
    }

    /**
     * request
     *
     * @param activity activity
     */
    @SuppressLint("NewApi")
    private static void request(final Activity activity, final Request request) {
        if (request == null) {
            return;
        }
        if (request.getPermissionListener() == null) {
            return;
        }
        if (request.getPermissionsNeeded() == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < 23) {
            request.getPermissionListener().gotPermissions();
            return;
        }
        boolean notHasPermissions = notHasPermissions(activity, request);
        if (notHasPermissions) {
            boolean shouldShowRequest = false;
            List<String> rejectPermissions = new ArrayList<String>();
            for (int i = 0; i < request.getPermissionsNeeded().length; i++) {
                if (Build.VERSION.SDK_INT >= 23) {
                    boolean should = !activity.shouldShowRequestPermissionRationale(request.getPermissionsNeeded()[i]);
                    shouldShowRequest = should || shouldShowRequest;
                    if (!should) {
                        if ((activity.checkSelfPermission(
                                request.getPermissionsNeeded()[i]) != PackageManager.PERMISSION_GRANTED)) {
                            rejectPermissions.add(request.getPermissionsNeeded()[i]);
                        }
                    }
                }
            }
            if (shouldShowRequest) {
                integerRequestMap.put(request.getRequestId(), request);
                boolean flag = request.getPermissionListener().showDialog(activity, request);
                if (!flag) {
                    request.request();
                }
            } else {
                request.getPermissionListener().rejectPermissions(rejectPermissions);
            }
        } else {
            request.getPermissionListener().gotPermissions();
        }

    }

    /**
     * requestPermissions
     *
     * @param activity          activity
     * @param permissionsNeeded permissionsNeeded
     * @param requestId         requestId
     */
    @SuppressLint("NewApi")
    private static void requestPermissions(final Activity activity, String[] permissionsNeeded, int requestId) {
        if (Build.VERSION.SDK_INT >= 23) {
            activity.requestPermissions(permissionsNeeded, requestId);
        }
    }

    /**
     * handleResult
     *
     * @param requestCode  requestCode
     * @param permissions  permissions
     * @param grantResults grantResults
     */
    public static void handleResult(int requestCode, String[] permissions, int[] grantResults) {
        if (integerRequestMap.containsKey(requestCode)) {
            Request request = integerRequestMap.get(requestCode);
            if (request.getPermissionListener() != null) {
                boolean gotPermissions = true;
                List<String> rejectPermissions = new ArrayList<String>();
                for (int i = 0; i < permissions.length; i++) {
                    gotPermissions = (grantResults[i] == PackageManager.PERMISSION_GRANTED) && gotPermissions;
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        rejectPermissions.add(permissions[i]);
                    }
                }
                if (gotPermissions) {
                    request.getPermissionListener().gotPermissions();
                } else {
                    request.getPermissionListener().rejectPermissions(rejectPermissions);
                }
            }
            integerRequestMap.remove(request);
        }
    }

    /**
     * notHasPermissions
     *
     * @param activity activity
     * @return true or false
     */
    @SuppressLint("NewApi")
    private static boolean notHasPermissions(Activity activity, Request request) {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean hasPermission = false;
            for (int i = 0; i < request.getPermissionsNeeded().length; i++) {
                hasPermission = (activity
                        .checkSelfPermission(request.getPermissionsNeeded()[i]) != PackageManager.PERMISSION_GRANTED)
                        || hasPermission;
            }
            return hasPermission;
        }
        return false;
    }

    /**
     * Request
     *
     * @author LiangMaYong
     * @version 1.0
     */
    public static class Request {

        private Activity activity;
        private int requestId;
        private String[] permissionsNeeded;
        private OnPermissionListener permissionListener;

        private Request(Activity activity, int requestId, String[] permissionsNeeded, OnPermissionListener permissionListener) {
            this.activity = activity;
            this.requestId = requestId;
            this.permissionsNeeded = permissionsNeeded;
            this.permissionListener = permissionListener;
        }

        private String[] getPermissionsNeeded() {
            return permissionsNeeded;
        }

        private int getRequestId() {
            return requestId;
        }

        private OnPermissionListener getPermissionListener() {
            return permissionListener;
        }

        public void request() {
            requestPermissions(activity, getPermissionsNeeded(), getRequestId());
        }
    }
}
