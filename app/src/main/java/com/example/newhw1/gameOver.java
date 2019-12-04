package com.example.newhw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class gameOver extends AppCompatActivity {

    private Button over_BTN_new, over_BTN_menu,over_BTN_exit;
    private TextView over_LBL_score,over_LBL;
    private MediaPlayer game_over;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);

        String score = getIntent().getStringExtra("Score");
        over_BTN_new = findViewById(R.id.over_BTN_new);
        over_BTN_menu = findViewById(R.id.over_BTN_menu);
        over_LBL_score = findViewById(R.id.over_LBL_score);
        over_BTN_exit = findViewById(R.id.over_BTN_exit);
        over_LBL = findViewById(R.id.over_LBL);
        game_over = MediaPlayer.create(getApplicationContext(), R.raw.spongbob_end);

        mySignal.animatePop(over_LBL_score);
        mySignal.animatePop(over_LBL);
        mySignal.animatePop(over_BTN_new);
        mySignal.animatePop(over_BTN_menu);
        mySignal.animatePop(over_BTN_exit);



        over_BTN_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivityNew();
            }
        });
        over_BTN_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivityMain();
            }
        });


        over_LBL_score.setText("Score: " + score);
        if(MainActivity.main_sound.isChecked())
             game_over.start();

        over_BTN_exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                finish();
                System.exit(0);

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        game_over.stop();
    }

    private void openNewActivityNew() {
        game_over.stop();
        Intent intent = new Intent(this, game.class);
        startActivity(intent);
        finish();
    }

    private void openNewActivityMain() {
        game_over.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }



}

