package example.sanjeev.com.quizapptrial.Services;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import example.sanjeev.com.quizapptrial.AppConstants.CustomIntents;

public class TimerService extends Service {
    private int timeAllowedInSecs = 60;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        CountDownTimer timer = new CountDownTimer(1000 * timeAllowedInSecs, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                int secs = (int) (millisUntilFinished/1000);
                                Log.e("TimeCompleted", " time is "+ secs);
                            }

                            @Override
                            public void onFinish() {

                                Log.e("TimeCompleted", " time + completed");
                                Intent intent = new Intent();
                                intent.setAction(CustomIntents.QUIZ_TIMER);
                                sendBroadcast(intent);

                                stopSelf();
                            }
                        };
                        timer.start();
                    }
                }
        );




        return START_STICKY;
    }
}
