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

import com.google.gson.Gson;

public class GameOver extends AppCompatActivity {
    public static final String KEY_JSON= "json20";
    public static final String KEY_NAME = "name";

    private  View view;
    private Button over_BTN_new, over_BTN_menu,over_BTN_exit;
    private TextView over_LBL_score,over_LBL,over_LBL_high;
    private MediaPlayer game_over;
    private MySheredP msp;
    private String personToAddString,personName;
    private Person personToAddObj;
    private int newHighScore =0;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);


         personToAddString = getIntent().getStringExtra(Game.KEY_PERSON);
         personToAddObj =  new Gson().fromJson(personToAddString,  Person.class);
        personName = personToAddObj.getName();
        findViews(view );

        game_over = MediaPlayer.create(getApplicationContext(), R.raw.spongbob_end);

        animationStart();

        msp = new MySheredP(this);
        String data  = msp.getString(KEY_JSON, "NA");
        AllPlayers allP = new AllPlayers(data);
        checkHighScore(allP);
        allP.addPlayer(personToAddObj);
        String json = gson.toJson(allP);
        msp.putString(KEY_JSON,json);

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



        over_LBL_score.setText("Your Score: " + personToAddObj.getScore());
        if (Settings.musicFlag)
            game_over.start();

        over_BTN_exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                System.exit(0);
            }
        });
    }

    private void checkHighScore(AllPlayers allP) {
        if(allP.getNumOfPlayers()>0) {
            if (Integer.parseInt(allP.getAllPlayers().get(0).getScore()) < Integer.parseInt(personToAddObj.getScore()))

                over_LBL_high.setText("New Record!!");
            else
                over_LBL_high.setText("");
        }
        else
            over_LBL_high.setText("New Record!!");

    }

    private void animationStart() {
        MySignal.animatePop(over_LBL_score);
        MySignal.animatePop(over_LBL);
        MySignal.animatePop(over_BTN_new);
        MySignal.animatePop(over_BTN_menu);
        MySignal.animatePop(over_BTN_exit);
        MySignal.animatePop(over_LBL_high);
        MySignal.animatePop(over_LBL_high);
    }


    @Override
    protected void onStop() {
        super.onStop();
        game_over.stop();
    }

    private void openNewActivityNew() {
        game_over.stop();
        Intent intent = new Intent(this, Game.class);
        intent.putExtra(KEY_NAME, "" + personName);
        startActivity(intent);
        finish();
    }

    private void openNewActivityMain() {
        game_over.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void findViews(View view) {
        over_BTN_new = findViewById(R.id.over_BTN_new);
        over_BTN_menu = findViewById(R.id.over_BTN_menu);
        over_LBL_score = findViewById(R.id.over_LBL_score);
        over_BTN_exit = findViewById(R.id.over_BTN_exit);
        over_LBL_high = findViewById(R.id.over_LBL_high);
        over_LBL = findViewById(R.id.over_LBL);
    }


}

