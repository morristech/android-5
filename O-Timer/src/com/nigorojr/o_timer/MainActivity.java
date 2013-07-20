package com.nigorojr.o_timer;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.TextView;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Timer.
 * @author Naoki Mizuno
 * TODO: Add JavaDoc.
 * TODO: Refactor to lessen code amount.
 *
 */

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    
    public static final int REQUEST_TIME = 0;
    
    private static OTimer timer;
    
    private Messenger messenger = null;
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            messenger = new Messenger(binder);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    
    // TODO: Do we really need to get the value back when we can get it by getSecondsToGoal()?
    private Messenger receive = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // Change the TextView to the elapsed time
            int[] value = timer.getDiff();
            if (value == null)
                return;
            int[] ids = {R.id.days, R.id.hours, R.id.minutes, R.id.seconds};
            for (int i = 0; i < value.length; i++) {
                TextView tv = (TextView)findViewById(ids[i]);
                tv.setText(String.valueOf(value[i]));
            }
            
            long timeToGoal = timer.getSecondsToGoal();
            ProgressBar pb = (ProgressBar)findViewById(R.id.progress);
            pb.setMax((int)timer.getInitialSecondsToGoal());
            pb.setProgress((int)(timer.getInitialSecondsToGoal() - timer.getSecondsToGoal()));
            if (timeToGoal == -1) {
                findViewById(R.id.goal_left_row).setVisibility(View.GONE);
                // TODO: Progress until next rank when no goal is set
                pb.setProgress(0);
            }
            else if (timeToGoal == 0)
                System.out.println("CONGRATULATIONS!!!!");
            else {
                findViewById(R.id.goal_left_row).setVisibility(View.VISIBLE);
                TextView tv = (TextView)findViewById(R.id.goal_left);
                tv.setText(String.valueOf(timeToGoal));
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();

        // Set OnClickListener to buttons
        findViewById(R.id.button_start_stop).setOnClickListener(this);
        findViewById(R.id.button_new).setOnClickListener(this);

        Timer t = new Timer();
        // Send request for time every second
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (messenger == null || !timer.isStarted())
                    return;

                Message msg = Message.obtain(null, REQUEST_TIME);
                msg.replyTo = receive;
                
                try {
                    messenger.send(msg);
                }
                catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
        t.scheduleAtFixedRate(task, 1, 1000);

        // Bind to service
        bindService(new Intent(this, OTimer.class), sc, Context.BIND_AUTO_CREATE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_set_start_date:
                setStartDate();
                break;
            case R.id.action_set_goal:
                setGoal();
                startTimer();
                break;
        }
        return true;
    }
    
    @Override
    /**
     * This method is called when the buttons are clicked.
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start_stop:
                if (timer.isStarted())
                    stopTimer();
                else
                    startTimer();
                break;
            case R.id.button_new:
                if (timer.isStarted())
                    stopTimer();
                startTimer();
                break;
        }
    }
    
    @Override
    /**
     * Unbind from service when Activity is destroyed
     */
    public void onDestroy() {
        super.onDestroy();
        unbindService(sc);
        
        // Save the start day only if the timer is started
        if (timer.isStarted())
            saveStartDateAndGoal();
    }
    
    public void init() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        long startDate = sp.getLong("startDate", -1);
        // If the user exited the program after stopping the timer
        if (startDate == -1)
            timer = new OTimer();
        else
            timer = new OTimer(startDate);

        long goalDate = sp.getLong("goalDate", -1);
        if (goalDate != -1)
            timer.setGoal((int)goalDate / (60 * 60 * 24));
        else
            findViewById(R.id.goal_left_row).setVisibility(View.GONE);
    }
    
    public void startTimer() {
        timer.start();
    }
    
    public void stopTimer() {
        // Reset the start date to default
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("startDate", -1);
        editor.putLong("goalDate", -1);
        editor.commit();
        
        int[] duration = timer.stop();
        String str = duration[0] + " days " + duration[1] + " hours " + duration[2]
                + " minutes " + duration[3] + " seconds!!!!";
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }
    
    public void setStartDate() {
        FragmentManager fm = getSupportFragmentManager();
        DatePickingDialog dp = new DatePickingDialog();
        dp.show(fm, "tag");
    }
    
    public void setGoal() {
        FragmentManager fm = getSupportFragmentManager();
        SetGoalDialog sgd = new SetGoalDialog();
        sgd.show(fm, "tag");
    }
    
    public void saveStartDateAndGoal() {
        // Add to SharedPreferences
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("startDate", timer.getStartDateInMilliSec());
        editor.putLong("goalDate", timer.getInitialSecondsToGoal());
        editor.commit();
    }
    
    public static class DatePickingDialog extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DATE));
            dpd.setTitle(this.getResources().getString(R.string.dialog_title));
            return dpd;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar toSet = Calendar.getInstance();
            toSet.set(year, monthOfYear, dayOfMonth);
            if (toSet.after(Calendar.getInstance())) {
                Toast.makeText(getActivity(), getResources().getString(R.string.set_to_future), Toast.LENGTH_SHORT).show();
                return;
            }
            timer.setStartingDate(year, monthOfYear, dayOfMonth);
        }
    }
    
    public static class SetGoalDialog extends DialogFragment implements DialogInterface.OnClickListener {
        EditText goalEditText;
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    if (goalEditText.getText().toString().equals(""))
                        return;
                    timer.setGoal(Integer.parseInt(goalEditText.getText().toString()));
                    break;
            }
        }
        
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog dialog = null;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setPositiveButton(getResources().getString(R.string.goal_dialog_ok), this);
            builder.setNegativeButton(getResources().getString(R.string.goal_dialog_cancel), this);
            goalEditText = new EditText(getActivity());
            goalEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            goalEditText.setHint(getResources().getString(R.string.goal_dialog_hint));
            builder.setView(goalEditText);
            
            dialog = builder.create();
            dialog.setTitle(getResources().getString(R.string.goal_dialog_title));
            return dialog;
        }
    }
}