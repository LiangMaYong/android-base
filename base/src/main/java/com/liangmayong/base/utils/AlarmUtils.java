package com.liangmayong.base.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by liangmayong on 2016/11/15.
 */
public class AlarmUtils {

    private static AlarmReceiver alarmReceiver;

    private static void init(Context context) {
        if (alarmReceiver == null) {
            alarmReceiver = new AlarmReceiver();
            IntentFilter filter = new IntentFilter(ACTION_ALARM);
            context.getApplicationContext().registerReceiver(alarmReceiver, filter);
        }
    }

    /**
     * ACTION_ALARM
     */
    private static final String ACTION_ALARM = "com.liangmayong.alarm";

    /**
     * "Alignment" time threshold, defaults to 5 minutes
     */
    public static int ALIGNMENT_TIME_IN_MILLIS = 300000;
    /**
     * Open the "alignment" function
     */
    public static boolean IS_ALIGNMENT_MODE = true;
    private static final String ORIGIONAL_INTENT = "origional_alarm_intent";
    private static final String ORIGIONAL_TIMER = "origional_alarm_timer";
    private static final String ORIGIONAL_INTERVAL = "origional_alarm_interval";
    private static final String ORIGIONAL_ID = "origional_alarm_id";
    private static final String ORIGIONAL_TYPE = "origional_alarm_type";

    /**
     * At a certain time every intervalMillis milliseconds (triggerAtMillis)
     * start the Broadcast, wake up the device when a dormant
     *
     * @param context         context
     * @param id              id
     * @param triggerAtMillis triggerAtMillis
     * @param intervalMillis  intervalMillis
     * @param intent          intent
     */
    public static void setBroadcastRepeatingWakeUp(Context context, int id, long triggerAtMillis, long intervalMillis,
                                                   Intent intent) {
        init(context);
        Intent newIntent = new Intent(ACTION_ALARM);
        newIntent.putExtra(ORIGIONAL_INTENT, intent);
        newIntent.putExtra(ORIGIONAL_TIMER, triggerAtMillis);
        newIntent.putExtra(ORIGIONAL_TYPE, 11);
        newIntent.putExtra(ORIGIONAL_INTERVAL, intervalMillis);
        newIntent.putExtra(ORIGIONAL_ID, id);
        if (Math.abs(System.currentTimeMillis() - triggerAtMillis) < ALIGNMENT_TIME_IN_MILLIS && IS_ALIGNMENT_MODE) {
            triggerAtMillis -= ALIGNMENT_TIME_IN_MILLIS;
        }
        intent = newIntent;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent);
    }

    /**
     * (triggerAtMillis) to start the Broadcast in a specific time, wake up the
     * device when a dormant, when triggerAtMillis is less than the current
     * time, immediately executed
     *
     * @param context         context
     * @param id              id
     * @param triggerAtMillis triggerAtMillis
     * @param intent          intent
     */
    public static void setBroadcastWakeUp(Context context, int id, long triggerAtMillis, Intent intent) {
        init(context);
        Intent newIntent = new Intent(ACTION_ALARM);
        newIntent.putExtra(ORIGIONAL_INTENT, intent);
        newIntent.putExtra(ORIGIONAL_TIMER, triggerAtMillis);
        newIntent.putExtra(ORIGIONAL_TYPE, 1);
        newIntent.putExtra(ORIGIONAL_ID, id);
        if (Math.abs(System.currentTimeMillis() - triggerAtMillis) < ALIGNMENT_TIME_IN_MILLIS && IS_ALIGNMENT_MODE) {
            triggerAtMillis -= ALIGNMENT_TIME_IN_MILLIS;
        }
        intent = newIntent;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
    }

    /**
     * At a certain time (triggerAtMillis) start the Broadcast, sleep does not
     * perform, when triggerAtMillis is less than the current time, immediately
     * executed
     *
     * @param context         context
     * @param id              id
     * @param triggerAtMillis triggerAtMillis
     * @param intent          intent
     */
    public static void setBroadcast(Context context, int id, long triggerAtMillis, Intent intent) {
        init(context);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.set(AlarmManager.RTC, triggerAtMillis, pendingIntent);
    }

    /**
     * At a certain time (triggerAtMillis) every intervalMillis milliseconds
     * after the start Broadcast, sleep does not perform
     *
     * @param context         context
     * @param id              id
     * @param triggerAtMillis triggerAtMillis
     * @param intervalMillis  intervalMillis
     * @param intent          intent
     */
    public static void setBroadcastRepeating(Context context, int id, long triggerAtMillis, long intervalMillis,
                                             Intent intent) {
        init(context);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtMillis, intervalMillis, pendingIntent);
    }

    /**
     * At a certain time every intervalMillis milliseconds (triggerAtMillis)
     * start the Service, wake up the device when a dormant
     *
     * @param context         context
     * @param id              id
     * @param triggerAtMillis triggerAtMillis
     * @param intervalMillis  intervalMillis
     * @param intent          intent
     */
    public static void setServiceRepeatingWakeUp(Context context, int id, long triggerAtMillis, long intervalMillis,
                                                 Intent intent) {
        init(context);
        Intent newIntent = new Intent(ACTION_ALARM);
        newIntent.putExtra(ORIGIONAL_INTENT, intent);
        newIntent.putExtra(ORIGIONAL_TIMER, triggerAtMillis);
        newIntent.putExtra(ORIGIONAL_TYPE, 12);
        newIntent.putExtra(ORIGIONAL_INTERVAL, intervalMillis);
        newIntent.putExtra(ORIGIONAL_ID, id);
        if (Math.abs(System.currentTimeMillis() - triggerAtMillis) < ALIGNMENT_TIME_IN_MILLIS && IS_ALIGNMENT_MODE) {
            triggerAtMillis -= ALIGNMENT_TIME_IN_MILLIS;
        }
        intent = newIntent;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent);
    }

    /**
     * (triggerAtMillis) to start the Service in a specific time, wake up the
     * device when a dormant, when triggerAtMillis is less than the current
     * time, immediately executed
     *
     * @param context         context
     * @param id              id
     * @param triggerAtMillis triggerAtMillis
     * @param intent          intent
     */
    public static void setServiceWakeUp(Context context, int id, long triggerAtMillis, Intent intent) {
        init(context);
        Intent newIntent = new Intent(ACTION_ALARM);
        newIntent.putExtra(ORIGIONAL_INTENT, intent);
        newIntent.putExtra(ORIGIONAL_TIMER, triggerAtMillis);
        newIntent.putExtra(ORIGIONAL_TYPE, 2);
        newIntent.putExtra(ORIGIONAL_ID, id);
        if (Math.abs(System.currentTimeMillis() - triggerAtMillis) < ALIGNMENT_TIME_IN_MILLIS && IS_ALIGNMENT_MODE) {
            triggerAtMillis -= ALIGNMENT_TIME_IN_MILLIS;
        }
        intent = newIntent;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
    }

    /**
     * At a certain time (triggerAtMillis) start the Service, sleep does not
     * perform, when triggerAtMillis is less than the current time, immediately
     * executed
     *
     * @param context         context
     * @param id              id
     * @param triggerAtMillis triggerAtMillis
     * @param intent          intent
     */
    public static void setService(Context context, int id, long triggerAtMillis, Intent intent) {
        init(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.set(AlarmManager.RTC, triggerAtMillis, pendingIntent);
    }

    /**
     * At a certain time (triggerAtMillis) every intervalMillis milliseconds
     * after the start Service, sleep does not perform
     *
     * @param context         context
     * @param id              id
     * @param triggerAtMillis triggerAtMillis
     * @param intervalMillis  intervalMillis
     * @param intent          intent
     */
    public static void setServiceRepeating(Context context, int id, long triggerAtMillis, long intervalMillis,
                                           Intent intent) {
        init(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtMillis, intervalMillis, pendingIntent);
    }

    /**
     * At a certain time every intervalMillis milliseconds (triggerAtMillis)
     * start the Activity, wake up the device when a dormant
     *
     * @param context         context
     * @param id              id
     * @param triggerAtMillis triggerAtMillis
     * @param intervalMillis  intervalMillis
     * @param intent          intent
     */
    public static void setActivityRepeatingWakeUp(Context context, int id, long triggerAtMillis, long intervalMillis,
                                                  Intent intent) {
        init(context);
        Intent newIntent = new Intent(ACTION_ALARM);
        newIntent.putExtra(ORIGIONAL_INTENT, intent);
        newIntent.putExtra(ORIGIONAL_TIMER, triggerAtMillis);
        newIntent.putExtra(ORIGIONAL_INTERVAL, intervalMillis);
        newIntent.putExtra(ORIGIONAL_TYPE, 10);
        newIntent.putExtra(ORIGIONAL_ID, id);
        if (Math.abs(System.currentTimeMillis() - triggerAtMillis) < ALIGNMENT_TIME_IN_MILLIS && IS_ALIGNMENT_MODE) {
            triggerAtMillis -= ALIGNMENT_TIME_IN_MILLIS;
        }
        intent = newIntent;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pendingIntent);
    }

    /**
     * (triggerAtMillis) to start the Activity in a specific time, wake up the
     * device when a dormant, when triggerAtMillis is less than the current
     * time, immediately executed
     *
     * @param context         context
     * @param id              id
     * @param triggerAtMillis triggerAtMillis
     * @param intent          intent
     */
    public static void setActivityWakeUp(Context context, int id, long triggerAtMillis, Intent intent) {
        init(context);
        Intent newIntent = new Intent(ACTION_ALARM);
        newIntent.putExtra(ORIGIONAL_INTENT, intent);
        newIntent.putExtra(ORIGIONAL_TIMER, triggerAtMillis);
        newIntent.putExtra(ORIGIONAL_TYPE, 0);
        newIntent.putExtra(ORIGIONAL_ID, id);
        if (Math.abs(System.currentTimeMillis() - triggerAtMillis) < ALIGNMENT_TIME_IN_MILLIS && IS_ALIGNMENT_MODE) {
            triggerAtMillis -= ALIGNMENT_TIME_IN_MILLIS;
        }
        intent = newIntent;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
    }

    /**
     * At a certain time (triggerAtMillis) start the Activity, sleep does not
     * perform, when triggerAtMillis is less than the current time, immediately
     * executed
     *
     * @param context         context
     * @param id              id
     * @param triggerAtMillis triggerAtMillis
     * @param intent          intent
     */
    public static void setActivity(Context context, int id, long triggerAtMillis, Intent intent) {
        init(context);
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.set(AlarmManager.RTC, triggerAtMillis, pendingIntent);
    }

    /**
     * At a certain time (triggerAtMillis) every intervalMillis milliseconds
     * after the start Activity, sleep does not perform
     *
     * @param context         context
     * @param id              id
     * @param triggerAtMillis triggerAtMillis
     * @param intervalMillis  intervalMillis
     * @param intent          intent
     */
    public static void setActivityRepeating(Context context, int id, long triggerAtMillis, long intervalMillis,
                                            Intent intent) {

        init(context);
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtMillis, intervalMillis, pendingIntent);
    }

    /**
     * Cancel the Broadcast regularly
     *
     * @param context context
     * @param id      id
     * @param intent  intent
     */
    public static void cancelBroadcast(Context context, int id, Intent intent) {
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.cancel(pendingIntent);
    }

    /**
     * Cancel the Service regularly
     *
     * @param context context
     * @param id      id
     * @param intent  intent
     */
    public static void cancelService(Context context, int id, Intent intent) {
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        PendingIntent pendingIntent = PendingIntent.getService(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.cancel(pendingIntent);
    }

    /**
     * Cancel the Activity regularly
     *
     * @param context context
     * @param id      id
     * @param intent  intent
     */
    public static void cancelActivity(Context context, int id, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarms.cancel(pendingIntent);
    }

    private static class AlarmReceiver extends BroadcastReceiver {
        private static final String ORIGIONAL_INTENT = "origional_alarm_intent";
        private static final String ORIGIONAL_TIMER = "origional_alarm_timer";
        private static final String ORIGIONAL_INTERVAL = "origional_alarm_interval";
        private static final String ORIGIONAL_ID = "origional_alarm_id";
        private static final String ORIGIONAL_TYPE = "origional_alarm_type";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_ALARM.equals(intent.getAction())) {
                final Intent origionalIntent = intent.getParcelableExtra(ORIGIONAL_INTENT);
                if (origionalIntent == null) {
                    return;
                }
                final int id = intent.getIntExtra(ORIGIONAL_ID, 0);
                final int type = intent.getIntExtra(ORIGIONAL_TYPE, -1);
                final long triggerAtMillis = intent.getLongExtra(ORIGIONAL_TIMER, System.currentTimeMillis());
                final long intervalMillis = intent.getLongExtra(ORIGIONAL_INTERVAL, System.currentTimeMillis());
                long datae = triggerAtMillis - System.currentTimeMillis();
                if (datae <= 0) {
                    if (type == 0) {
                        AlarmUtils.setActivity(context, id, triggerAtMillis, origionalIntent);
                    } else if (type == 1) {
                        AlarmUtils.setBroadcast(context, id, triggerAtMillis, origionalIntent);
                    } else if (type == 2) {
                        AlarmUtils.setService(context, id, triggerAtMillis, origionalIntent);
                    } else if (type == 10) {
                        AlarmUtils.setActivity(context, id, triggerAtMillis, origionalIntent);
                        AlarmUtils.setActivityRepeatingWakeUp(context, id, triggerAtMillis + intervalMillis,
                                intervalMillis, origionalIntent);
                    } else if (type == 11) {
                        AlarmUtils.setBroadcast(context, id, triggerAtMillis, origionalIntent);
                        AlarmUtils.setBroadcastRepeatingWakeUp(context, id, triggerAtMillis + intervalMillis,
                                intervalMillis, origionalIntent);
                    } else if (type == 12) {
                        AlarmUtils.setService(context, id, triggerAtMillis, origionalIntent);
                        AlarmUtils.setServiceRepeatingWakeUp(context, id, triggerAtMillis + intervalMillis,
                                intervalMillis, origionalIntent);
                    }
                } else {
                    if (type == 0) {
                        AlarmUtils.setActivityWakeUp(context, id, triggerAtMillis, origionalIntent);
                    } else if (type == 1) {
                        AlarmUtils.setBroadcastWakeUp(context, id, triggerAtMillis, origionalIntent);
                    } else if (type == 2) {
                        AlarmUtils.setServiceWakeUp(context, id, triggerAtMillis, origionalIntent);
                    } else if (type == 10) {
                        AlarmUtils.setActivityRepeatingWakeUp(context, id, triggerAtMillis, intervalMillis,
                                origionalIntent);
                    } else if (type == 11) {
                        AlarmUtils.setBroadcastRepeatingWakeUp(context, id, triggerAtMillis, intervalMillis,
                                origionalIntent);
                    } else if (type == 12) {
                        AlarmUtils.setServiceRepeatingWakeUp(context, id, triggerAtMillis, intervalMillis,
                                origionalIntent);
                    }
                }
            }
        }
    }
}
