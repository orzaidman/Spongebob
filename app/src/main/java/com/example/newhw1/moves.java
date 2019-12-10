package com.example.newhw1;

import java.util.Random;

public class moves  {
    final int NONE = 0;
    final int JELLYFISH = 1;
    final int COIN = 3;
    final int PLAYER = 2;
    final int NUM_ROW =9;
    final int NUM_COL =5;
    final int NUM_HEART = 3;


    private int[] playerBoard;
    private int[][] board;
    private  int playerPos;
    private int objectPos;
    private int coinPos;
    private int numOfHearts;
    private int rand_colJ;
    private int rand_colC;

    private Random rand = new Random();


    public moves() {
        playerBoard = new int[NUM_COL];
        board = new int[NUM_ROW][NUM_COL];
        this.playerPos=2;
        this.numOfHearts = NUM_HEART;
        this.objectPos = -1;
    }

    public  void startBord()
    {
        for (int i =0; i< board.length; i++)
            for (int j =0; j< board[i].length; j++)
                board[i][j]=NONE;
    }

    public  void startPlayer()
    {
        for (int i = 0; i< NUM_COL; i++)
            if(i==playerPos)
                playerBoard[i]=PLAYER;
            else
                playerBoard[i]=NONE;
    }



    public void turnRight(){
        playerBoard[playerPos]=NONE;
        playerPos++;
        if (playerPos >= playerBoard.length)
            playerPos = 0;

        playerBoard[playerPos]=PLAYER;
    }


    public void turnLeft() {
        playerBoard[playerPos]=NONE;
        playerPos--;
        if (playerPos < 0)
            playerPos = playerBoard.length - 1;
        playerBoard[playerPos]=PLAYER;

    }


    public void updateBoard() {
        for (int i =0; i< board.length; i++)
            for (int j =0; j< board[i].length; j++)
                if (i == objectPos && rand_colJ == j)
                    board[i][j]= JELLYFISH;
                else if(i == coinPos && rand_colC == j)
                    board[i][j]= COIN;
                else
                    board[i][j]= NONE;
    }



    public void randJellyfish(){
         rand_colJ = rand.nextInt(5);
        board[0][rand_colJ]= JELLYFISH;
        objectPos = 0;
    }

    public void randCoins(){
        do {
            rand_colC = rand.nextInt(5);
        }while(rand_colC == rand_colJ);
        board[0][rand_colC]= COIN;
        coinPos = 0;
    }

    //0- game over
    //1 - crash
    //-1 - not crash
    public int isCrash() {
                if ((board[NUM_ROW - 1][rand_colJ] == JELLYFISH) && (playerBoard[rand_colJ] == PLAYER)) {
                    numOfHearts--;
                    if (numOfHearts == 0)
                        return 0;
                    else {
                        board[NUM_ROW - 1][rand_colJ] = NONE;
                        objectPos = 0;
                        return 1;
                    }
                } else {
                    board[NUM_ROW - 1][rand_colJ] = NONE;
                    objectPos = 0;
                    return -1;
                }
    }

    public boolean isCoins()
    {
        if ((board[NUM_ROW - 1][rand_colC] == COIN) && (playerBoard[rand_colC] == PLAYER))
        {
           board[NUM_ROW - 1][rand_colC] = NONE;
           coinPos = 0;
           return true;
        }
        else {
            board[NUM_ROW - 1][rand_colC] = NONE;
            coinPos = 0;
            return false;
         }
    }



    public  int[] getPlayerBoard() {
        return playerBoard;
    }

    public int getRand_colJ() {
        return rand_colJ;
    }

    public int getRand_colC() {
        return rand_colC;
    }

    public  int[][] getBoard() {
        return board;
    }

    public void setPlayerPos(int playerPos) {
        this.playerPos = playerPos;
    }

    public void setRand_colJ(int rand_colJ) {
        this.rand_colJ = rand_colJ;
    }

    public void setObjectsPos(int objectPos) {
        if (objectPos==NUM_ROW)
            objectPos = 0;
        else
        this.objectPos = objectPos;
    }

    public int getPlayerPos() {
        return playerPos;
    }

    public int getObjectPos() {
        return objectPos;
    }

    public int getCoinPos() {
        return coinPos;
    }

    public void setCoinPos(int coinPos) {
        if (coinPos==NUM_ROW)
            coinPos =0;
        else
        this.coinPos = coinPos;
    }

    public int getNumOfHearts() {
        return numOfHearts;
    }
}
