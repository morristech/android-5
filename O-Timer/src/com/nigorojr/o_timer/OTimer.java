package com.nigorojr.o_timer;

import java.util.Calendar;

import android.app.Service;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class OTimer extends Service {
    private Calendar startDate;
    private Calendar goalDate;
    private long diff = -1;
    private boolean started = false;
    
    public OTimer() {
    }
    
    // If the program last exited while timer was working
    public OTimer(long startDate) {
        this.startDate = Calendar.getInstance();
        this.startDate.setTimeInMillis(startDate);
        started = true;
    }
    
    private Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MainActivity.REQUEST_TIME:
                    Message replyMessage = Message.obtain(null, MainActivity.REQUEST_TIME, getSecondsToGoal());
                    try {
                        msg.replyTo.send(replyMessage);
                    }
                    catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    });

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
    
    public void start() {
        // Reset the variable "diff" for a new start
        diff = -1;
        
        startDate = Calendar.getInstance();
        started = true;
    }
    
    /**
     * Stops the timer and returns the duration in milliseconds.
     * @return The length between start and end in milliseconds.
     */
    public int[] stop() {
        int[] ret = getDiff();
        started = false;
        return ret;
    }
    
    public long getStartDateInMilliSec() {
        if (!started)
            return -1;
        return startDate.getTimeInMillis();
    }
    
    public long getSecondsToGoal() {
        Calendar current = Calendar.getInstance();
        if (goalDate == null)
            return -1;
        long seconds = (goalDate.getTimeInMillis() - current.getTimeInMillis()) / 1000;
        
        return seconds;
    }
    
    public int[] getDiff() {
        if (!started)
            return null;

        Calendar current = Calendar.getInstance();
        diff = current.getTimeInMillis() - startDate.getTimeInMillis();

        long days = (diff / 1000) / (60 * 60 * 24);
        diff = (diff / 1000) % (60 * 60 * 24);
        long hours = diff / (60 * 60);
        diff = diff % (60 * 60);
        long minutes = diff / 60;
        diff = diff % 60;
        long seconds = diff;

        int[] ret = {(int)days, (int)hours, (int)minutes, (int)seconds};
        return ret;
    }
    
    public void setStartingDate(int year, int month, int day) {
        startDate.set(year, month, day);
    }

    public boolean isStarted() {
        return started;
    }
}