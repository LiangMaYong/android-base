package com.liangmayong.base.support.database;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * DataUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class DataUtils {


    // application
    private static WeakReference<Application> application = null;
    private static Class<?> activityThreadClass = null;
    private static Method currentActivityThreadMethod = null;
    private static Method getApplicationMethod = null;

    /**
     * getApplication
     *
     * @return application
     */
    public static Application getApplication() {
        if (application == null || application.get() == null) {
            synchronized (DataUtils.class) {
                if (application == null) {
                    try {
                        if (activityThreadClass == null) {
                            activityThreadClass = Class.forName("android.app.ActivityThread");
                        }
                        if (currentActivityThreadMethod == null) {
                            currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
                        }
                        if (getApplicationMethod == null) {
                            getApplicationMethod = activityThreadClass.getDeclaredMethod("getApplication");
                        }
                        if (currentActivityThreadMethod != null && getApplicationMethod != null) {
                            Object object = currentActivityThreadMethod.invoke(null);
                            if (object != null) {
                                if (getApplicationMethod != null) {
                                    application = new WeakReference<Application>((Application) getApplicationMethod.invoke(object));
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return application.get();
    }

    /**
     * delete Database
     *
     * @param context      context
     * @param databaseName databaseName
     * @return true or flase
     */
    public static boolean deleteDatabase(Context context, String databaseName) {
        String dbFilesDir = getDatabaseDir(context);
        String dbFileName = dbFilesDir + databaseName;
        return deleteFile(new File(dbFileName));
    }

    /**
     * getDatabaseDir
     *
     * @param context context
     * @return database dir
     */
    private static String getDatabaseDir(Context context) {
        return context.getApplicationInfo().dataDir + "/databases/";
    }

    /**
     * copy database
     *
     * @param context     context
     * @param inputStream inputStream
     * @param saveName    saveName
     * @param coverage    coverage
     * @return true or flase
     */
    public static boolean copyDatabase(Context context, InputStream inputStream, String saveName, boolean coverage) {
        String dbFilesDir = getDatabaseDir(context);
        String dbFileName = dbFilesDir + saveName;
        boolean isDbExist = false;
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(dbFileName, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        } catch (SQLiteException e) {
        } finally {
            if (checkDB != null) {
                isDbExist = true;
                checkDB.close();
            }
        }
        if (!isDbExist || coverage) {
            File dir = new File(dbFilesDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            FileOutputStream os = null;
            try {
                os = new FileOutputStream(dbFileName);
            } catch (FileNotFoundException e) {
            }
            byte[] buffer = new byte[8192];
            int count = 0;
            try {
                while ((count = inputStream.read(buffer)) > 0) {
                    os.write(buffer, 0, count);
                    os.flush();
                }
            } catch (IOException e) {
            }
            try {
                inputStream.close();
                os.close();
            } catch (IOException e) {
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * delete file
     *
     * @param file file
     * @return true or flase
     */
    private static boolean deleteFile(File file) {
        try {
            if (file.exists()) {
                if (file.isFile()) {
                    file.delete();
                } else if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFile(files[i]);
                    }
                }
                file.delete();
            }
        } catch (Exception e) {
        }
        if (file.exists()) {
            return false;
        } else {
            return true;
        }
    }

}
