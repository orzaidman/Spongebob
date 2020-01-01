package com.example.newhw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_NAME= "name";
    private Button main_BTN_start,main_BTN_exit,main_BTN_scores,main_BTN_setting;
    private ImageView[] allPlayers;
    private int pos = 0;

    private MediaPlayer startM;
    private ImageView main_IMG_title;
    private final Handler handler = new Handler();
    private Runnable myRun;
    private boolean  first = false;
    private EditText main_TXT_name;
    private View view;
private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        findViews(view);

        animationStart();
        requestPermission();

        allPlayers = new ImageView[]{
                findViewById(R.id.main_IMG_player1),
                findViewById(R.id.main_IMG_player2),
                findViewById(R.id.main_IMG_player3),
                findViewById(R.id.main_IMG_player4)
        };


        if (Settings.musicFlag)
            startM.start();

        startPlay();


        main_BTN_start.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            openNewActivityGame();
        }
    });

        main_BTN_setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openNewActivitySetting();
            }
        });


        main_BTN_scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivityScores();
            }
        });

    loopFunction();


    main_BTN_exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                MainActivity.this.finish();
                System.exit(0);
            }
        });
}

    private void openNewActivitySetting() {
        startM.stop();
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(myRun);
        super.onStop();
        startM.stop();
        first= true;
    }


    private  void startPlay(){
        for (int i = 0; i < allPlayers.length; i++)
            if (i == pos)
                allPlayers[i].setVisibility(View.VISIBLE);
            else
                allPlayers[i].setVisibility(View.INVISIBLE);
    }

    private void openNewActivityGame() {
        startM.stop();
        handler.removeCallbacks(myRun);
        finish();
        Intent intent = new Intent(this, Game.class);
        intent.putExtra(KEY_NAME, "" + main_TXT_name.getText());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Settings.musicFlag)
            startM.start();

        if (first == true) {
            handler.postDelayed(myRun, 400);
            first = false;
        }
    }
    private void loopFunction() {

            myRun = new Runnable() {
                @Override
                public void run() {
                    loopFunction();
                    showPlayer();
                }
            };
            handler.postDelayed(myRun, 400);
        }


    private void showPlayer() {
        pos++;
        if (pos >= allPlayers.length)
            pos = 0;

        for (int i = 0; i < allPlayers.length; i++)
            if (i == pos)
                allPlayers[i].setVisibility(View.VISIBLE);
            else
                allPlayers[i].setVisibility(View.INVISIBLE);
    }

    private void openNewActivityScores() {
        startM.stop();
        Intent intent = new Intent(this, Scores.class);
        startActivity(intent);
        finish();
    }
    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }

    private void findViews(View view){
        main_BTN_start = findViewById(R.id.main_BTN_start);
        main_BTN_setting = findViewById(R.id.main_BTN_setting);
        main_BTN_scores = findViewById(R.id.main_BTN_scores);
        main_BTN_exit = findViewById(R.id.main_BTN_exit);
        startM = MediaPlayer.create(getApplicationContext(), R.raw.spongbob_start);
        main_IMG_title = findViewById(R.id.main_IMG_title);
        main_TXT_name = findViewById(R.id.main_TXT_name);
    }

    private void animationStart() {
        MySignal.animatePop(main_BTN_start);
        MySignal.animatePop(main_IMG_title);
        MySignal.animatePop(main_BTN_exit);
        MySignal.animatePop(main_BTN_scores);
        MySignal.animatePop(main_BTN_setting);
    }
}
