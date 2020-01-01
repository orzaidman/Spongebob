package com.example.newhw1;

import android.os.Build;
import com.google.gson.Gson;
import java.util.ArrayList;

public class AllPlayers {
    private final int max = 10;
    private ArrayList<Person> allPlayers;
    private int numOfPlayers;


    public AllPlayers(AllPlayers other) {
        this.allPlayers = other.allPlayers;
        this.numOfPlayers = other.numOfPlayers;
    }

    public AllPlayers(String data) {
       this(createPlayersFromString(data));
    }

    public AllPlayers() {
        this.allPlayers = new ArrayList<Person>();
        this.numOfPlayers = 0;
    }

    private static AllPlayers createPlayersFromString(String data) {
        AllPlayers tempP;
        if (data == "NA") {
            tempP = new AllPlayers();
        }
        else {
            tempP = new Gson().fromJson(data, AllPlayers.class);
        }
        return tempP;
    }

    public void addPlayer(Person p)
    {
        if(allPlayers.size()<=max) {
                allPlayers.add(p);
                numOfPlayers++;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                this.allPlayers.sort(null);
//            }
            sortPlayers();
            if(numOfPlayers==max+1) {
                allPlayers.remove(allPlayers.size() - 1);
                numOfPlayers--;
            }
        }
    }


    public void sortPlayers() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.allPlayers.sort(null);
        }
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public ArrayList<Person> getAllPlayers() {
        return allPlayers;
    }




    @Override
    public String toString() {
        String s= "";
        for (int i = 0; i <numOfPlayers; i++) {
            s += allPlayers.get(i) +"\n";
        }
        return s;
    }
}
