package com.liangmayong.base.support.permission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;

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
public final class FragmentPermission {

    private FragmentPermission() {
    }

    /**
     * OnPermissionListener
     *
     * @author LiangMaYong
     * @version 1.0
     */
    public interface OnPermissionListener {

        boolean showDialog(Fragment fragment, Request request);

        void gotPermissions();

        void rejectPermissions(List<String> rejects);
    }

    // request map
    @SuppressLint("UseSparseArrays")
    private static Map<Integer, Request> integerRequestMap = new HashMap<Integer, Request>();


    /**
     * readPhoneStatePermissions
     *
     * @param fragment fragment
     * @param id       id
     * @param listener listener
     */
    public static void readPhoneStatePermissions(Fragment fragment, int id, OnPermissionListener listener) {
        String[] permissionsNeeded = {Manifest.permission.READ_PHONE_STATE};
        requestPermissions(fragment, id, permissionsNeeded, listener);
    }

    /**
     * cameraPermissions
     *
     * @param fragment fragment
     * @param id       id
     * @param listener listener
     */
    public static void cameraPermissions(Fragment fragment, int id, OnPermissionListener listener) {
        String[] permissionsNeeded = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(fragment, id, permissionsNeeded, listener);
    }

    /**
     * recordAudioPermissions
     *
     * @param fragment fragment
     * @param id       id
     * @param listener listener
     */
    public static void recordAudioPermissions(Fragment fragment, int id, OnPermissionListener listener) {
        String[] permissionsNeeded = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(fragment, id, permissionsNeeded, listener);
    }

    /**
     * filePermissions
     *
     * @param fragment fragment
     * @param id       id
     * @param listener listener
     */
    public static void filePermissions(Fragment fragment, int id, OnPermissionListener listener) {
        String[] permissionsNeeded = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(fragment, id, permissionsNeeded, listener);
    }

    /**
     * locationPermissions
     *
     * @param fragment fragment
     * @param id       id
     * @param listener listener
     */
    public static void locationPermissions(Fragment fragment, int id, OnPermissionListener listener) {
        String[] permissionsNeeded = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        requestPermissions(fragment, id, permissionsNeeded, listener);
    }

    /**
     * request
     *
     * @param fragment           fragment
     * @param requestId          requestId
     * @param permissionsNeeded  permissionsNeeded
     * @param permissionListener permissionListener
     */
    public static void requestPermissions(final Fragment fragment, int requestId, String[] permissionsNeeded,
                                          OnPermissionListener permissionListener) {
        request(fragment, new Request(fragment, requestId, permissionsNeeded, permissionListener));
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

    ////////////////////////////////////////////////////////////////////////////////
    /////// Private
    ////////////////////////////////////////////////////////////////////////////////

    /**
     * request
     *
     * @param fragment fragment
     */
    @SuppressLint("NewApi")
    private static void request(final Fragment fragment, final Request request) {
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
        boolean notHasPermissions = notHasPermissions(fragment, request);
        if (notHasPermissions) {
            boolean shouldShowRequest = false;
            List<String> rejectPermissions = new ArrayList<String>();
            for (int i = 0; i < request.getPermissionsNeeded().length; i++) {
                if (Build.VERSION.SDK_INT >= 23) {
                    boolean should = !fragment.shouldShowRequestPermissionRationale(request.getPermissionsNeeded()[i]);
                    shouldShowRequest = should || shouldShowRequest;
                    if (!should) {
                        if ((fragment.getActivity().checkSelfPermission(
                                request.getPermissionsNeeded()[i]) != PackageManager.PERMISSION_GRANTED)) {
                            rejectPermissions.add(request.getPermissionsNeeded()[i]);
                        }
                    }
                }
            }
            if (shouldShowRequest) {
                integerRequestMap.put(request.getRequestId(), request);
                boolean flag = request.getPermissionListener().showDialog(fragment, request);
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
     * @param fragment          fragment
     * @param permissionsNeeded permissionsNeeded
     * @param requestId         requestId
     */
    @SuppressLint("NewApi")
    private static void requestPermissions(final Fragment fragment, String[] permissionsNeeded, int requestId) {
        if (Build.VERSION.SDK_INT >= 23) {
            fragment.requestPermissions(permissionsNeeded, requestId);
        }
    }

    /**
     * notHasPermissions
     *
     * @param fragment fragment
     * @return true or false
     */
    @SuppressLint("NewApi")
    private static boolean notHasPermissions(Fragment fragment, Request request) {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean hasPermission = false;
            for (int i = 0; i < request.getPermissionsNeeded().length; i++) {
                hasPermission = (fragment.getActivity().checkSelfPermission(request.getPermissionsNeeded()[i]) != PackageManager.PERMISSION_GRANTED)
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
    private static class Request {
        private Fragment fragment;
        private int requestId;
        private String[] permissionsNeeded;
        private OnPermissionListener permissionListener;

        private Request(Fragment fragment, int requestId, String[] permissionsNeeded, OnPermissionListener permissionListener) {
            this.fragment = fragment;
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
            requestPermissions(fragment, getPermissionsNeeded(), getRequestId());
        }
    }
}
