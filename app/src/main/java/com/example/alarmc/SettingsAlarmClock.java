package com.example.alarmc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class SettingsAlarmClock extends AppCompatActivity {

    private CheckBox mNormalEquationsCheckBox, mLogarithmsCheckBox, mNormalSamplesCheckBox;
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_LIST = "ListArray";
    SharedPreferences mSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        mNormalEquationsCheckBox = findViewById(R.id.checkBoxSettings1);
        mLogarithmsCheckBox = findViewById(R.id.checkBoxSettings2);
        mNormalSamplesCheckBox = findViewById(R.id.checkBoxSettings3);

        Button SaveParamsButton = findViewById(R.id.saveSettings);
        Button BackButton = findViewById(R.id.backToMain);

        SaveParamsButton.setOnClickListener(settings -> {
                if (TaskFile.banned_themes.size() > 0){
                    TaskFile.banned_themes.clear();
                }
                if(!mLogarithmsCheckBox.isChecked())
                    TaskFile.banned_themes.add(1);
                if(!mNormalEquationsCheckBox.isChecked())
                    TaskFile.banned_themes.add(2);
                if(!mNormalSamplesCheckBox.isChecked())
                    TaskFile.banned_themes.add(30);
                if (TaskFile.banned_themes.size() > 2){
                    TaskFile.banned_themes.clear();
                    Toast.makeText(this,
                            "Вы отменили максимальное кол-во параметров." +
                                    " Нельзя отменять все параметры",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,
                            "Все сохранено успешно",
                            Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(APP_PREFERENCES_LIST, String.valueOf(TaskFile.banned_themes));
                    editor.apply();
                }
        });
        BackButton.setOnClickListener(back -> {
            Intent backMain = new Intent(SettingsAlarmClock.this, MainActivity.class);
            startActivity(backMain);
        });
    }
}