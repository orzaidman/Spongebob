package com.example.newhw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Scores extends AppCompatActivity {
    public static final String KEY_JSON= "json20";

    private MySheredP msp;
    private Button score_BTN_main,score_ROW_1B,score_ROW_2B,score_ROW_3B,
            score_ROW_4B,score_ROW_5B,score_ROW_6B,score_ROW_7B,score_ROW_8B,score_ROW_9B,score_ROW_10B;
    private TextView[][] scoresList;
    private Button[] allBTN;
    private AllPlayers tempP;
    private  View view;
    private BlankFragment blankFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        msp = new MySheredP(this);
        findViews(view);
         blankFragment = new BlankFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.score_FREGMENT,blankFragment);
        transaction.commit();



        String score = getIntent().getStringExtra(Game.KEY_SCORE);

        String result = msp.getString(KEY_JSON, "NA");
            tempP = new AllPlayers(result);
            tempP.sortPlayers();

        score_BTN_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivityMain();
            }
        });



        scoresList = new TextView[][]{
                {findViewById(R.id.score_ROW_1R), findViewById(R.id.score_ROW_1N), findViewById(R.id.score_ROW_1S), findViewById(R.id.score_ROW_1T)},
                {findViewById(R.id.score_ROW_2R), findViewById(R.id.score_ROW_2N), findViewById(R.id.score_ROW_2S), findViewById(R.id.score_ROW_2T)},
                {findViewById(R.id.score_ROW_3R), findViewById(R.id.score_ROW_3N), findViewById(R.id.score_ROW_3S), findViewById(R.id.score_ROW_3T)},
                {findViewById(R.id.score_ROW_4R), findViewById(R.id.score_ROW_4N), findViewById(R.id.score_ROW_4S), findViewById(R.id.score_ROW_4T)},
                {findViewById(R.id.score_ROW_5R), findViewById(R.id.score_ROW_5N), findViewById(R.id.score_ROW_5S), findViewById(R.id.score_ROW_5T)},
                {findViewById(R.id.score_ROW_6R), findViewById(R.id.score_ROW_6N), findViewById(R.id.score_ROW_6S),findViewById(R.id.score_ROW_6T)},
                {findViewById(R.id.score_ROW_7R), findViewById(R.id.score_ROW_7N), findViewById(R.id.score_ROW_7S),findViewById(R.id.score_ROW_7T)},
                {findViewById(R.id.score_ROW_8R), findViewById(R.id.score_ROW_8N), findViewById(R.id.score_ROW_8S),findViewById(R.id.score_ROW_8T)},
                {findViewById(R.id.score_ROW_9R), findViewById(R.id.score_ROW_9N), findViewById(R.id.score_ROW_9S),findViewById(R.id.score_ROW_9T)},
                {findViewById(R.id.score_ROW_10R), findViewById(R.id.score_ROW_10N), findViewById(R.id.score_ROW_10S),findViewById(R.id.score_ROW_10T)}};

        allBTN = new Button[]{
                findViewById(R.id.score_ROW_1B), findViewById(R.id.score_ROW_2B),
                findViewById(R.id.score_ROW_3B), findViewById(R.id.score_ROW_4B),
                findViewById(R.id.score_ROW_5B), findViewById(R.id.score_ROW_6B),
                findViewById(R.id.score_ROW_7B), findViewById(R.id.score_ROW_9B),
                findViewById(R.id.score_ROW_8B), findViewById(R.id.score_ROW_10B)};


        startLocation();

        score_ROW_1B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               click(1);
            }
        });
        score_ROW_2B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(2);
            }
        });
        score_ROW_3B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(3);
            }
        });
        score_ROW_4B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(4);
            }
        });
        score_ROW_5B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(5);
            }
        });
        score_ROW_6B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(6);
            }
        });
        score_ROW_7B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(7);
            }
        });
        score_ROW_8B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(8);
            }
        });
        score_ROW_9B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(9);
            }
        });
        score_ROW_10B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(10);
            }
        });

        if(result != "NA" )
            updateScores();

    }

    private void updateScores()
    {
            for (int i = 0; i < tempP.getAllPlayers().size(); i++) {
                scoresList[i][0].setText((i+1) + "");
                scoresList[i][1].setText(tempP.getAllPlayers().get(i).getName());
                scoresList[i][2].setText(tempP.getAllPlayers().get(i).getScore());
                scoresList[i][3].setText(tempP.getAllPlayers().get(i).getTime());
            }
        }

    private void click(int i) {
            blankFragment.update(tempP.getAllPlayers().get(i - 1).getLocationX(),
                    tempP.getAllPlayers().get(i - 1).getLocationY());
     }
private void startLocation()
{
    int i;
    for( i = 0; i< tempP.getAllPlayers().size(); i++)
        allBTN[i].setVisibility(View.VISIBLE);

    while(i < allBTN.length)
    {
        allBTN[i].setVisibility(View.INVISIBLE);
        i++;
    }
}



    private void openNewActivityMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void findViews(View view) {
        score_BTN_main = findViewById(R.id.score_BTN_main);
        score_ROW_1B = findViewById(R.id.score_ROW_1B);
        score_ROW_2B = findViewById(R.id.score_ROW_2B);
        score_ROW_3B = findViewById(R.id.score_ROW_3B);
        score_ROW_4B = findViewById(R.id.score_ROW_4B);
        score_ROW_5B = findViewById(R.id.score_ROW_5B);
        score_ROW_6B = findViewById(R.id.score_ROW_6B);
        score_ROW_7B = findViewById(R.id.score_ROW_7B);
        score_ROW_8B = findViewById(R.id.score_ROW_8B);
        score_ROW_9B = findViewById(R.id.score_ROW_9B);
        score_ROW_10B = findViewById(R.id.score_ROW_10B);
    }
}
