package com.example.newhw1;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.google.gson.Gson;


public class Game extends AppCompatActivity implements LocationListener {
    public static final String KEY_SCORE = "score20";
    public static final String KEY_NAME = "name";
    public static final String KEY_PERSON = "person";

    final int NONE = 0, JELLYFISH = 1, COIN = 3, PLAYER = 2;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Person p = null;

    Moves m = new Moves();
    int[] playerBoardGUI = m.getPlayerBoard();
    int[][] gameBoardGUI = m.getBoard();
    static final Handler handler = new Handler();
    Runnable myRun;
    private Button game_BTN_left, game_BTN_right, game_BTN_pause, game_BTN_close;

    private ImageView[] playerBoard;
    private ImageView[][] gameBoardIMG;
    private int time = 400, countLives = 3, score = 0;
    private ImageView[] lives;
    private ImageView game_IMG_heart1, game_IMG_heart2, game_IMG_heart3;
    private View view;
    private boolean stop = false, first = false;
    private TextView game_LBL_score, game_LBL_plus;
    private MediaPlayer bomb, money;
    private String personName;
    private LocationManager locationManager;
    private double longtitude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        findViews(view);
        personName = getIntent().getStringExtra(Game.KEY_NAME);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        bomb = MediaPlayer.create(getApplicationContext(), R.raw.electric_shock);
        money = MediaPlayer.create(getApplicationContext(), R.raw.money);

        playerBoard = new ImageView[]{
                findViewById(R.id.game_IMG_player1), findViewById(R.id.game_IMG_player2),
                findViewById(R.id.game_IMG_player3), findViewById(R.id.game_IMG_player4),
                findViewById(R.id.game_IMG_player5)};

        lives = new ImageView[]{
                findViewById(R.id.game_IMG_heart1), findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3)
        };

        gameBoardIMG = new ImageView[][]{
                {findViewById(R.id.game_IMG_asteroid1), findViewById(R.id.game_IMG_asteroid2), findViewById(R.id.game_IMG_asteroid3), findViewById(R.id.game_IMG_asteroid4), findViewById(R.id.game_IMG_asteroid5)},
                {findViewById(R.id.game_IMG_asteroid6), findViewById(R.id.game_IMG_asteroid7), findViewById(R.id.game_IMG_asteroid8), findViewById(R.id.game_IMG_asteroid9), findViewById(R.id.game_IMG_asteroid10)},
                {findViewById(R.id.game_IMG_asteroid11), findViewById(R.id.game_IMG_asteroid12), findViewById(R.id.game_IMG_asteroid13), findViewById(R.id.game_IMG_asteroid14), findViewById(R.id.game_IMG_asteroid15)},
                {findViewById(R.id.game_IMG_asteroid16), findViewById(R.id.game_IMG_asteroid17), findViewById(R.id.game_IMG_asteroid18), findViewById(R.id.game_IMG_asteroid19), findViewById(R.id.game_IMG_asteroid20)},
                {findViewById(R.id.game_IMG_asteroid21), findViewById(R.id.game_IMG_asteroid22), findViewById(R.id.game_IMG_asteroid23), findViewById(R.id.game_IMG_asteroid24), findViewById(R.id.game_IMG_asteroid25)},
                {findViewById(R.id.game_IMG_asteroid26), findViewById(R.id.game_IMG_asteroid27), findViewById(R.id.game_IMG_asteroid28), findViewById(R.id.game_IMG_asteroid29), findViewById(R.id.game_IMG_asteroid30)},
                {findViewById(R.id.game_IMG_asteroid31), findViewById(R.id.game_IMG_asteroid32), findViewById(R.id.game_IMG_asteroid33), findViewById(R.id.game_IMG_asteroid34), findViewById(R.id.game_IMG_asteroid35)},
                {findViewById(R.id.game_IMG_asteroid36), findViewById(R.id.game_IMG_asteroid37), findViewById(R.id.game_IMG_asteroid38), findViewById(R.id.game_IMG_asteroid39), findViewById(R.id.game_IMG_asteroid40)}
        };

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (checkSelfPermission(permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && checkSelfPermission(permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        onLocationChanged(location);

        buildPlayer();
        buildBoard();
        startLives();
        loopFunction();
        if (Settings.buttonsFlag) {
            sensorManager.unregisterListener(sensorEventListener);
            game_BTN_right.setVisibility(View.VISIBLE);
            game_BTN_left.setVisibility(View.VISIBLE);
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
        } else {
            sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            game_BTN_right.setVisibility(View.INVISIBLE);
            game_BTN_left.setVisibility(View.INVISIBLE);
        }

        game_BTN_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score = 0;
                openNewActivityMenu();
            }
        });

        game_BTN_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stop == false) {
                    handler.removeCallbacks(myRun);
                    game_BTN_pause.setBackgroundResource(R.drawable.img_play);
                    game_BTN_left.setEnabled(false);
                    game_BTN_right.setEnabled(false);
                    stop = true;
                } else {
                    stop = false;
                    game_BTN_pause.setBackgroundResource(R.drawable.img_pause);
                    game_BTN_left.setEnabled(true);
                    game_BTN_right.setEnabled(true);
                    loopFunction();
                }
            }
        });
    }


    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }


    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            updatePlayerGui();
            if (!Settings.buttonsFlag) {
                if ((event.values[0] <= 10) && (event.values[0] > 7))
                    m.setPlayerPos(0);

                if ((event.values[0] <= 7) && (event.values[0] > 2))
                    m.setPlayerPos(1);

                if ((event.values[0] <= 2) && (event.values[0] > -2))
                    m.setPlayerPos(2);

                if ((event.values[0] <= -2) && (event.values[0] > -6))
                    m.setPlayerPos(3);

                if ((event.values[0] <= -6) && (event.values[0] >= -10))
                    m.setPlayerPos(4);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

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


    private void sensorChanged(boolean faceOn) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        if (first == true) {
            handler.postDelayed(myRun, time);
            first = true;
            stop = false;
        }
    }


    private void startLives() {
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
                playGame();
            }
        };
        handler.postDelayed(myRun, time);
        time -= 2;
        if (time <= 250)
            time = 250;

    }


    //0: Game over, 1: crash, -1: not crash, 2: got img_coin
    private void playGame() {
        updateGui();
        m.moveForward();

        if (score % 170 == 0)
            m.addJellyfish();

        if (score % 280 == 0)
            m.addCoin();


        boolean flagLastRow = m.checkLastRow();
        updateGui();
        if (flagLastRow) {
            int res = m.isCrash();

            if (res == 0) { //Game over
                if (Settings.musicFlag)
                    bomb.start();
                openNewActivityGameOver();
            }

            if (res == 1) { //crash
                m.randJellyfish();
                int heartCount = m.getNumOfHearts();
                MySignal.vibrate(this, 500);
                MySignal.animatePlayer(playerBoard[m.getPlayerPos()]);
                if (Settings.musicFlag)
                    bomb.start();

                if (heartCount == 2)
                    MySignal.animateHeart(game_IMG_heart3);
                else
                    MySignal.animateHeart(game_IMG_heart2);
            }


            if (res == 2) {//got coin
                MySignal.vibrate(this, 100);
                if (Settings.musicFlag)
                    money.start();
                game_LBL_plus.setText("+50");
                MySignal.animatePlus(game_LBL_plus);
                score += 50;
            }
            if (res == -1)//-1: not crash
                m.randJellyfish();
        }

    }

    private void updateGui() {
        for (int i = 0; i < gameBoardIMG.length; i++) {
            for (int j = 0; j < gameBoardIMG[i].length; j++) {
                if (gameBoardGUI[i][j] == NONE)
                    gameBoardIMG[i][j].setVisibility(View.INVISIBLE);

                if (gameBoardGUI[i][j] == COIN) {
                    gameBoardIMG[i][j].setVisibility(View.VISIBLE);
                    gameBoardIMG[i][j].setImageResource(R.drawable.img_coin);
                }
                if (gameBoardGUI[i][j] == JELLYFISH) {
                    gameBoardIMG[i][j].setVisibility(View.VISIBLE);
                    gameBoardIMG[i][j].setImageResource(R.drawable.img_game_jellyfish);
                }
            }
        }
    }

    private void updatePlayerGui() {
        for (int i = 0; i < playerBoard.length; i++) {
            if (playerBoardGUI[i] == NONE)
                playerBoard[i].setVisibility(View.INVISIBLE);

            if (playerBoardGUI[i] == PLAYER) {
                playerBoard[i].setVisibility(View.VISIBLE);

                if (playerBoardGUI[i] == COIN)
                    playerBoard[i].setVisibility(View.VISIBLE);
            }
        }
    }

    private void openNewActivityGameOver() {
        buildPerson();
        MySignal.animateHeart(game_IMG_heart1);
        MySignal.vibrate(this, 500);
        handler.removeCallbacks(myRun);
        Intent intent = new Intent(this, GameOver.class);
        String s = new Gson().toJson(p);
        intent.putExtra("person", s);

        score = 0;
        finish();
        startActivity(intent);
    }

    private void buildPerson() {
        String currentDate = new SimpleDateFormat("dd-MM", Locale.getDefault()).format(new Date());
        p = new Person(personName, "" + score, latitude, longtitude, currentDate);
    }


    private void buildPlayer() {
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

    private void buildBoard() {
        m.startBord();
        m.randJellyfish();
        for (int i = 0; i < gameBoardIMG.length; i++) {
            for (int j = 0; j < gameBoardIMG[i].length; j++) {
                if (gameBoardGUI[i][j] == NONE)
                    gameBoardIMG[i][j].setVisibility(View.INVISIBLE);

                if (gameBoardGUI[i][j] == COIN) {
                    gameBoardIMG[i][j].setVisibility(View.VISIBLE);
                    gameBoardIMG[i][j].setImageResource(R.drawable.img_coin);
                }

                if (gameBoardGUI[i][j] == JELLYFISH) {
                    gameBoardIMG[i][j].setVisibility(View.VISIBLE);
                    gameBoardIMG[i][j].setImageResource(R.drawable.img_game_jellyfish);
                }
            }
        }
    }

    private void findViews(View view) {
        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_right = findViewById(R.id.game_BTN_right);
        game_LBL_score = findViewById(R.id.game_LBL_score);
        game_BTN_pause = findViewById(R.id.game_BTN_pause);
        game_BTN_close = findViewById(R.id.game_BTN_close);
        game_IMG_heart1 = findViewById(R.id.game_IMG_heart1);
        game_IMG_heart2 = findViewById(R.id.game_IMG_heart2);
        game_IMG_heart3 = findViewById(R.id.game_IMG_heart3);
        game_LBL_plus = findViewById(R.id.game_LBL_plus);
    }


    @Override
    public void onLocationChanged(Location location) {
            longtitude = location.getLongitude();
            latitude = location.getLatitude();

    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }
}







