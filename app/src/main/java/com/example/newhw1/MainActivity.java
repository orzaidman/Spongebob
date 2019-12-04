package com.example.newhw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private Button main_BTN_start,main_BTN_exit;
    private ImageView[] allPlayers;
    private int pos = 0;
    static Switch main_sound;
    private MediaPlayer startM;
    private ImageView main_IMG_title;
    private final Handler handler = new Handler();
    private Runnable myRun;
    private boolean  first = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        main_BTN_start = findViewById(R.id.main_BTN_start);
        main_BTN_exit = findViewById(R.id.main_BTN_exit);
        startM = MediaPlayer.create(getApplicationContext(), R.raw.spongbob_start);
        main_IMG_title = findViewById(R.id.main_IMG_title);
        main_sound = findViewById(R.id.main_sound);
        Boolean switchState = main_sound.isChecked();


        allPlayers = new ImageView[]{
                findViewById(R.id.main_IMG_player1),
                findViewById(R.id.main_IMG_player2),
                findViewById(R.id.main_IMG_player3),
                findViewById(R.id.main_IMG_player4)
        };
        mySignal.animatePop(main_BTN_start);
        mySignal.animatePop(main_IMG_title);
        mySignal.animatePop(main_BTN_exit);
        startM.start();

        startPlay();


        main_BTN_start.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            openNewActivityGame();
        }
    });

        main_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startM.start();

                } else {
                    startM.pause();
                }
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
        Intent intent = new Intent(this,game.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

}
