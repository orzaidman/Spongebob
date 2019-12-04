package com.example.newhw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class game extends AppCompatActivity {
    private Random rand = new Random();
    private Button game_BTN_left,game_BTN_right,game_BTN_pause,game_BTN_close;

    private ImageView[] playerBoard;
    private int posPlayer = 1, posRowBoard = -1,rand_place,screenH,screenW,objectH,objectW;

    private ImageView[][] gameBoard;
    private int score = 0,time = 400, countLives = 3;

    private ImageView[] lives;
    private ImageView game_IMG_heart1,game_IMG_heart2,game_IMG_heart3;

    private boolean stop = false;
    private TextView game_LBL_score;
    private MediaPlayer bomb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);
        game_LBL_score = findViewById(R.id.game_LBL_score);
        game_BTN_pause = findViewById(R.id.game_BTN_pause);
        game_BTN_close = findViewById(R.id.game_BTN_close);
        game_IMG_heart1 = findViewById(R.id.game_IMG_heart1);
        game_IMG_heart2 = findViewById(R.id.game_IMG_heart2);
        game_IMG_heart3 = findViewById(R.id.game_IMG_heart3);
        bomb = MediaPlayer.create(getApplicationContext(), R.raw.electric_shock);


        playerBoard = new ImageView[]{
                findViewById(R.id.game_IMG_player1),
                findViewById(R.id.game_IMG_player2),
                findViewById(R.id.game_IMG_player3)};

        lives = new ImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3)
        };

        gameBoard = new ImageView[][]{{
                findViewById(R.id.game_IMG_asteroid1), findViewById(R.id.game_IMG_asteroid2), findViewById(R.id.game_IMG_asteroid3)},
                {findViewById(R.id.game_IMG_asteroid4), findViewById(R.id.game_IMG_asteroid5), findViewById(R.id.game_IMG_asteroid6)},
                {findViewById(R.id.game_IMG_asteroid7), findViewById(R.id.game_IMG_asteroid8), findViewById(R.id.game_IMG_asteroid9)},
                {findViewById(R.id.game_IMG_asteroid10), findViewById(R.id.game_IMG_asteroid11), findViewById(R.id.game_IMG_asteroid12)},
                {findViewById(R.id.game_IMG_asteroid13), findViewById(R.id.game_IMG_asteroid14), findViewById(R.id.game_IMG_asteroid15)}};

        setDimensions();
        startPlayer();
        startAction();
        startLives();
        loopFunction();

        game_BTN_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveRight();
            }
        });

        game_BTN_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveLeft();
            }
        });

        game_BTN_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewActivityMenu();
            }
        });

        game_BTN_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stop == false) {
                    game_BTN_pause.setBackgroundResource(R.drawable.play_new);
                    game_BTN_left.setEnabled(false);
                    game_BTN_right.setEnabled(false);
                    stop = true;
                }
                else{
                    stop = false;
                    game_BTN_pause.setBackgroundResource(R.drawable.pause_new);
                    game_BTN_left.setEnabled(true);
                    game_BTN_right.setEnabled(true);
                    loopFunction();
                }
            }
        });

    }

    private void openNewActivityMenu() {
        stop = true;
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        stop = true;
        super.onStop();
    }

    private void startLives() {
        countLives = 3;
        for (int i = 0; i < countLives; i++)
            lives[i].setVisibility(View.VISIBLE);
    }


    private void startAction() {
        for (int i = 0; i < gameBoard.length; i++)
            for (int j = 0; j < gameBoard[i].length; j++)
                gameBoard[i][j].setVisibility(View.INVISIBLE);
    }

    private void startPlayer() {
        for (int i = 0; i < playerBoard.length; i++)
            if (i == posPlayer)
                playerBoard[i].setVisibility(View.VISIBLE);
            else
                playerBoard[i].setVisibility(View.INVISIBLE);
    }

    private void moveRight() {
        posPlayer++;
        if (posPlayer >= playerBoard.length)
            posPlayer = 0;

        for (int i = 0; i < playerBoard.length; i++)
            if (i == posPlayer)
                playerBoard[i].setVisibility(View.VISIBLE);
            else
                playerBoard[i].setVisibility(View.INVISIBLE);
    }


    private void moveLeft() {
        posPlayer--;
        if (posPlayer < 0)
            posPlayer = playerBoard.length - 1;

        for (int i = 0; i < playerBoard.length; i++)
            if (i == posPlayer)
                playerBoard[i].setVisibility(View.VISIBLE);
            else
                playerBoard[i].setVisibility(View.INVISIBLE);
    }

    private void loopFunction() {

        if (stop == false) {
            final Handler handler = new Handler();
            Runnable myRun = new Runnable() {
                @Override
                public void run() {
                    score = score + 10;
                    game_LBL_score.setText("SCORE:" + score);
                    loopFunction();
                    playGame();
                }
            };

            handler.postDelayed(myRun, time);
            time -= 3;
            if (time <= 200)
                time = 200;
        }
    }

    private void playGame() {
        if (posRowBoard == -1)
            rand_place = rand.nextInt(3);

        posRowBoard++;
        if (posRowBoard == gameBoard.length) {
            if (playerBoard[rand_place].getVisibility() == View.VISIBLE) {
                checkLose();
                if(MainActivity.main_sound.isChecked())
                    bomb.start();
            }
            else {
                gameBoard[posRowBoard - 1][rand_place].setVisibility(View.INVISIBLE);
                posRowBoard = 0;
                rand_place = rand.nextInt(3);
            }
        }

        for (int i = 0; i < gameBoard.length; i++) { //row
            if (i == posRowBoard)
                gameBoard[i][rand_place].setVisibility(View.VISIBLE);
            else
                gameBoard[i][rand_place].setVisibility(View.INVISIBLE);
        }

    }

    private void openNewActivity() {
        stop = true;
        mySignal.vibrate(this, 500);
        Intent intent = new Intent(this, gameOver.class);
        intent.putExtra("Score", "" + score);
        finish();
        startActivity(intent);
    }

    private void checkLose() {
        if (countLives == 1) {
            if(MainActivity.main_sound.isChecked())
                bomb.start();
            mySignal.animateHeart(game_IMG_heart1);
            openNewActivity();
        }
        else {
            mySignal.vibrate(this, 200);
        }
        if (countLives==3) {
            mySignal.animatePlayer(playerBoard[rand_place]);
            mySignal.animateHeart(game_IMG_heart3);
        }
        if (countLives==2) {
            mySignal.animateHeart(game_IMG_heart2);
            mySignal.animatePlayer(playerBoard[rand_place]);
        }
        countLives--;
        gameBoard[posRowBoard - 1][rand_place].setVisibility(View.INVISIBLE);
        posRowBoard = 0;
        rand_place = rand.nextInt(3);
    }

    private void setDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenH = displayMetrics.heightPixels - game_BTN_left.getLayoutParams().height - lives[0].getLayoutParams().height;
        screenW = displayMetrics.widthPixels;
        objectH = screenH/6 -5;
        objectW = screenW/3 -90;
        for (int i=0; i<gameBoard.length; i++){
            for (int j=0; j<gameBoard[0].length; j++){
                gameBoard[i][j].requestLayout();
                gameBoard[i][j].getLayoutParams().height = objectH;
                gameBoard[i][j].getLayoutParams().width = objectW;
            }
        }
        for (int k=0; k<playerBoard.length; k++){
            playerBoard[k].requestLayout();
            playerBoard[k].getLayoutParams().height = objectH;
            playerBoard[k].getLayoutParams().width = objectW;
        }
    }
}



