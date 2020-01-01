package com.example.newhw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Settings extends AppCompatActivity {
    private Switch setting_SWITCH_sound,setting_SWITCH_buttons;
    public static boolean musicFlag = true;
    public static boolean buttonsFlag= true;
    private Button setting_BTN_main;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViews(view);
        Boolean switchStateSound = setting_SWITCH_sound.isChecked();
        Boolean switchStateButtons = setting_SWITCH_sound.isChecked();


        if (musicFlag)
            setting_SWITCH_sound.setChecked(true);
        else
            setting_SWITCH_sound.setChecked(false);

        if (buttonsFlag)
            setting_SWITCH_buttons.setChecked(true);
        else
            setting_SWITCH_buttons.setChecked(false);



        setting_SWITCH_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    musicFlag= true;
                else
                    musicFlag = false;
            }
        });

        setting_SWITCH_buttons.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    buttonsFlag= true;
                 else
                    buttonsFlag= false;
            }
        });

        setting_BTN_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivityMain();
            }
        });
    }

    private void openNewActivityMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void findViews(View view) {
        setting_SWITCH_sound = findViewById(R.id.setting_SWITCH_sound);
        setting_SWITCH_buttons = findViewById(R.id.main_SWITCH_buttons);
        setting_BTN_main = findViewById(R.id.setting_BTN_main);
    }
}
