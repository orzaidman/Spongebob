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

public class game extends AppCompatActivity {
    final int NONE = 0;
    final int JELLYFISH = 1;
    final int COIN = 2;


    moves m = new moves();
    int[] playerBoardGUI = m.getPlayerBoard();
    int[][] gameBoardGUI = m.getBoard();
    int firstGame = -1;
    static final Handler handler = new Handler();
    Runnable myRun;
    private Button game_BTN_left, game_BTN_right, game_BTN_pause, game_BTN_close;

    private ImageView[] playerBoard;
    private int  rand_placeJ,rand_placeC=-1, screenH, screenW, objectH, objectW;

    private ImageView[][] gameBoard;
    private int time = 400, countLives = 3;
    static int score = 0;
    private ImageView[] lives;
    private ImageView game_IMG_heart1, game_IMG_heart2, game_IMG_heart3;

    private boolean stop = false, first = false;
    private TextView game_LBL_score,game_LBL_plus;
    private MediaPlayer bomb, money;


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
        game_LBL_plus = findViewById(R.id.game_LBL_plus);
        bomb = MediaPlayer.create(getApplicationContext(), R.raw.electric_shock);
        money = MediaPlayer.create(getApplicationContext(), R.raw.money);


        playerBoard = new ImageView[]{
                findViewById(R.id.game_IMG_player1),
                findViewById(R.id.game_IMG_player2),
                findViewById(R.id.game_IMG_player3),
                findViewById(R.id.game_IMG_player4),
                findViewById(R.id.game_IMG_player5)};

        lives = new ImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3)
        };

        gameBoard = new ImageView[][]{
                {findViewById(R.id.game_IMG_asteroid11), findViewById(R.id.game_IMG_asteroid12), findViewById(R.id.game_IMG_asteroid13), findViewById(R.id.game_IMG_asteroid14), findViewById(R.id.game_IMG_asteroid15)},
                {findViewById(R.id.game_IMG_asteroid16), findViewById(R.id.game_IMG_asteroid17), findViewById(R.id.game_IMG_asteroid18), findViewById(R.id.game_IMG_asteroid19), findViewById(R.id.game_IMG_asteroid20)},
                {findViewById(R.id.game_IMG_asteroid21), findViewById(R.id.game_IMG_asteroid22), findViewById(R.id.game_IMG_asteroid23), findViewById(R.id.game_IMG_asteroid24), findViewById(R.id.game_IMG_asteroid25)},
                {findViewById(R.id.game_IMG_asteroid26), findViewById(R.id.game_IMG_asteroid27), findViewById(R.id.game_IMG_asteroid28), findViewById(R.id.game_IMG_asteroid29), findViewById(R.id.game_IMG_asteroid30)},
                {findViewById(R.id.game_IMG_asteroid31), findViewById(R.id.game_IMG_asteroid32), findViewById(R.id.game_IMG_asteroid33), findViewById(R.id.game_IMG_asteroid34), findViewById(R.id.game_IMG_asteroid35)},
                {findViewById(R.id.game_IMG_asteroid36), findViewById(R.id.game_IMG_asteroid37), findViewById(R.id.game_IMG_asteroid38), findViewById(R.id.game_IMG_asteroid39), findViewById(R.id.game_IMG_asteroid40)},
                {findViewById(R.id.game_IMG_asteroid41), findViewById(R.id.game_IMG_asteroid42), findViewById(R.id.game_IMG_asteroid43), findViewById(R.id.game_IMG_asteroid44), findViewById(R.id.game_IMG_asteroid45)},
                {findViewById(R.id.game_IMG_asteroid46), findViewById(R.id.game_IMG_asteroid47), findViewById(R.id.game_IMG_asteroid48), findViewById(R.id.game_IMG_asteroid49), findViewById(R.id.game_IMG_asteroid50)},
                {findViewById(R.id.game_IMG_asteroid51), findViewById(R.id.game_IMG_asteroid52), findViewById(R.id.game_IMG_asteroid53), findViewById(R.id.game_IMG_asteroid54), findViewById(R.id.game_IMG_asteroid55)}
        };

        setDimensions();
        startPlayerNew();
        startActionNew();
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
                score =0;
                openNewActivityMenu();
            }
        });

        game_BTN_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stop == false) {
                    handler.removeCallbacks(myRun);
                    game_BTN_pause.setBackgroundResource(R.drawable.play_new);
                    game_BTN_left.setEnabled(false);
                    game_BTN_right.setEnabled(false);
                    stop = true;
                } else {
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
        handler.removeCallbacks(myRun);
        super.onStop();
        first = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (first == true) {
            handler.postDelayed(myRun, time);
            first = true;
            stop = false;
        }
    }


    private void startLives() {
        countLives = 3;
        for (int i = 0; i < countLives; i++)
            lives[i].setVisibility(View.VISIBLE);
    }



    private void loopFunction() {
        myRun = new Runnable() {
            @Override
            public void run() {
                score = score + 10;
                game_LBL_score.setText("SCORE:" + score);
                loopFunction();
                playGameNew();
            }
        };
        handler.postDelayed(myRun, time);
        time -= 3;
        if (time <= 200)
            time = 200;
    }




    //0: game over, 1: crash, -1: not crash
    private void playGameNew() {
        if(firstGame == -1){
            m.randJellyfish();
            m.randCoins();
            rand_placeJ = m.getRand_colJ();
            rand_placeC = m.getRand_colC();
            firstGame++;
        }

        int posObjects = m.getObjectPos();
        posObjects++;
        m.setObjectsPos(posObjects);

        int posCoins = m.getCoinPos();
        posCoins++;
        m.setCoinPos(posCoins);
        m.updateBoard();


        if (posObjects == gameBoard.length) {
            firstGame = -1;
            int res = m.isCrash();
            if(res == 0) {
                if(MainActivity.main_sound.isChecked())
                      bomb.start();
                mySignal.animateHeart(game_IMG_heart1);
                openNewActivityGameOver();
            }
           if(res ==1)
            {
                mySignal.vibrate(this, 500);
                mySignal.animatePlayer(playerBoard[rand_placeJ]);
                if(MainActivity.main_sound.isChecked())
                    bomb.start();

                if (m.getNumOfHearts() == 2) {
                    mySignal.animateHeart(game_IMG_heart3);
                }
                if (m.getNumOfHearts() == 1) {
                    mySignal.animateHeart(game_IMG_heart2);
                }
            }
        }

        if (posCoins == gameBoard.length)
        {
            if(m.isCoins() == true) {
                mySignal.vibrate(this, 500);
                if(MainActivity.main_sound.isChecked())
                    money.start();
                game_LBL_plus.setText("+50");
                mySignal.animatePlus(game_LBL_plus);
                score += 50;
            }
            gameBoard[gameBoard.length-1][m.getRand_colC()].setVisibility(View.INVISIBLE);
            m.randCoins();
        }

            for (int i = 0; i < gameBoard.length; i++) {
            if (i == posObjects) {
                gameBoard[i][rand_placeJ].setImageResource(R.drawable.jellyfish);
                gameBoard[i][rand_placeJ].setVisibility(View.VISIBLE);
            }else
                gameBoard[i][rand_placeJ].setVisibility(View.INVISIBLE);

                if (i == posCoins) {
                    gameBoard[i][rand_placeC].setImageResource(R.drawable.coin);
                    gameBoard[i][rand_placeC].setVisibility(View.VISIBLE);
                }
                else
                    gameBoard[i][rand_placeC].setVisibility(View.INVISIBLE);
        }
    }



    private void openNewActivityGameOver() {
        handler.removeCallbacks(myRun);
        mySignal.vibrate(this, 500);
        Intent intent = new Intent(this, gameOver.class);
        intent.putExtra("Score", "" + score);
        finish();
        startActivity(intent);
    }


    private void setDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenH = displayMetrics.heightPixels - game_BTN_left.getLayoutParams().height -game_LBL_score.getLayoutParams().height- game_IMG_heart1.getLayoutParams().height;
        screenW = displayMetrics.widthPixels;
        objectH = screenH / 11;
        objectW = screenW / 5;
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                gameBoard[i][j].requestLayout();
                gameBoard[i][j].getLayoutParams().height = objectH;
                gameBoard[i][j].getLayoutParams().width = objectW;
            }
        }
        for (int k = 0; k < playerBoard.length; k++) {
            playerBoard[k].requestLayout();
            playerBoard[k].getLayoutParams().height = objectH;
            playerBoard[k].getLayoutParams().width = objectW;
        }
    }

    private void startPlayerNew() {
        m.startPlayer();

        for (int i = 0; i < playerBoard.length; i++) {
            if (playerBoardGUI[i] == NONE)
                playerBoard[i].setVisibility(View.INVISIBLE);
            else
                playerBoard[i].setVisibility(View.VISIBLE);

        }
    }

    private void moveRight() {
        m.turnRight();
        for (int i = 0; i < playerBoard.length; i++) {
            if (playerBoardGUI[i] == NONE)
                playerBoard[i].setVisibility(View.INVISIBLE);
            else
                playerBoard[i].setVisibility(View.VISIBLE);
        }
    }

    private void moveLeft() {
        m.turnLeft();
        for (int i = 0; i < playerBoard.length; i++) {
            if (playerBoardGUI[i] == NONE)
                playerBoard[i].setVisibility(View.INVISIBLE);
            else
                playerBoard[i].setVisibility(View.VISIBLE);

        }
    }
    private void startActionNew() {
        m.startBord();
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if (gameBoardGUI[i][j] == NONE)
                    gameBoard[i][j].setVisibility(View.INVISIBLE);
                else if (gameBoardGUI[i][j] ==COIN)
                    gameBoard[i][j].setVisibility(View.INVISIBLE);
                else
                    gameBoard[i][j].setVisibility(View.VISIBLE);
            }
        }
    }



}



