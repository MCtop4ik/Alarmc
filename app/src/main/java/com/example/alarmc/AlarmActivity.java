package com.example.alarmc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Random;

public class AlarmActivity extends AppCompatActivity {

    private static final String APP_PREFERENCES = "settings";
    private static final String APP_PREFERENCES_LIST = "ListArray";
    Ringtone ringtone;
    TextView task_label, random_task;
    Button check;
    EditText ans;

    public static boolean isTask = false;
    private static Object[] save_arr;
    public static boolean isNoAlarm = true;
    public static int cnt_was = 0;

    public static void setSave_arr(Object[] save_arr) {
        AlarmActivity.save_arr = save_arr;
    }

    public Object[] getSave_arr(){
        return save_arr;
    }

    SharedPreferences mSettings;

    String s;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alarm);
        setTitle("Tasks");

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(this, notificationUri);
        if (ringtone == null) {
            notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            ringtone = RingtoneManager.getRingtone(this, notificationUri);
        }
        if (ringtone != null && isNoAlarm) {
            ringtone.play();
            isNoAlarm = false;
        }

        if(mSettings.contains(APP_PREFERENCES_LIST)) {
            s = mSettings.getString(SettingsAlarmClock.APP_PREFERENCES_LIST, "");
            String sub_s = s.substring(1, s.length() - 1);
            String[] arr_sub_s = sub_s.split(", ");
            Integer[] arr_s = new Integer[arr_sub_s.length];
            for(int i = 0; i < arr_sub_s.length; i++) {
                arr_s[i] = Integer.parseInt(arr_sub_s[i]);
            }
            TaskFile.banned_themes = Arrays.asList(arr_s);
        }

        Random rnd = new Random();
        Object[] arr = TaskFile.task[rnd.nextInt(TaskFile.task.length)];
        int is_ban_num = (int) arr[3];
        System.out.println(TaskFile.banned_themes);
        while (TaskFile.banned_themes.contains(is_ban_num)){
            arr = TaskFile.task[rnd.nextInt(TaskFile.task.length)];
            is_ban_num = (int) arr[3];
        }

        if (!isTask){
            setSave_arr(arr);
        }else{
            arr = getSave_arr();
        }

        task_label = findViewById(R.id.task_label);
        task_label.append(TaskFile.task_l[(int)arr[1]]);

        random_task = findViewById(R.id.random_task);
        random_task.append((String)arr[0]);

        isTask = true;

        check = findViewById(R.id.check);
        check.setOnClickListener(check -> {
            ans = findViewById(R.id.answerEntry);
            if (ans.getText().toString().equals(save_arr[2].toString())){
                Toast.makeText(this, "Вы решили верно", Toast.LENGTH_SHORT).show();
                isTask = false;
                if ((ringtone != null && ringtone.isPlaying())) {
                    ringtone.stop();
                    isNoAlarm = true;
                }
                cnt_was++;
                if (TaskFile.cnt_tasks == cnt_was){
                    Intent intentSwitchActivity = new Intent(AlarmActivity.this, MainActivity.class);
                    startActivity(intentSwitchActivity);
                    cnt_was = 0;
                }else{
                    Intent reload = new Intent(AlarmActivity.this, AlarmActivity.class);
                    finish();
                    startActivity(reload);
                }
            }else{
                Toast.makeText(this, "Вы решили неверно", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy(){super.onDestroy();}

}
