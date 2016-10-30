package com.i906.mpt.widget;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.i906.mpt.internal.Dagger;
import com.i906.mpt.internal.ServiceModule;
import com.i906.mpt.prayer.PrayerContext;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * @author Noorzaini Ilhami
 */
public class WidgetService extends Service implements WidgetHandler {

    public static final String ACTION_PRAYER_TIME_UPDATED = "com.i906.mpt.action.PRAYER_TIME_UPDATED";

    @Inject
    WidgetDelegate mPresenter;

    @Override
    public void onCreate() {
        super.onCreate();

        Dagger.getGraph(this)
                .serviceGraph(new ServiceModule(this))
                .inject(this);

        mPresenter.setHandler(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPresenter.refreshPrayerContext();
        return START_STICKY;
    }

    @Override
    public void handlePrayerContext(PrayerContext prayerContext) {
        Intent intent = new Intent(ACTION_PRAYER_TIME_UPDATED);
        intent.putExtra("prayer_context", (Parcelable) prayerContext);
        sendBroadcast(intent);
        stopSelf();
    }

    @Override
    public void handleError(Throwable throwable) {
        Timber.w(throwable);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.setHandler(null);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void start(Context context) {
        Intent alarm = new Intent(context, WidgetService.class);
        context.startService(alarm);
    }
}
