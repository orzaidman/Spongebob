package com.example.newhw1;

import java.util.Random;

public class Moves {
    private final int NONE = 0;
    private final int JELLYFISH = 1;
    private final int COIN = 3;
    private final int PLAYER = 2;
    private final int NUM_ROW =8;
    private final int NUM_COL =5;
    private final int NUM_HEART = 3;

    private int[] playerBoard;
    private int[][] board;
    private  int playerPos;
    private int numOfHearts;
    private int numOfJellyfish;
    private Random rand = new Random();


    public Moves() {
        playerBoard = new int[NUM_COL];
        board = new int[NUM_ROW][NUM_COL];
        this.playerPos=2;
        this.numOfHearts = NUM_HEART;
        numOfJellyfish= 1;
    }

    public  void startBord() {
        for (int i =0; i< board.length; i++)
            for (int j =0; j< board[i].length; j++)
                board[i][j]=NONE;
    }

    public  void startPlayer() {
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


    public void moveForward() {
        for (int j = 0; j < board[0].length; j++)
           for (int i =0; i< board.length-1; i++) {
            {
                if (board[i][j] != NONE) {
                    board[i + 1][j] = board[i][j];
                    board[i][j] = NONE;
                    i++;
                }
            }
        }
    }

    public boolean checkAvailable(int posToAdd){
        for (int i =0; i<board.length;i++)
            if(board[i][posToAdd] != NONE)
                return false;
        return true;
    }

    public void randJellyfish(){
        int rand_colJ;
        do {
            rand_colJ = rand.nextInt(5);

        }while (!checkAvailable(rand_colJ));
        board[0][rand_colJ]= JELLYFISH;
    }


    //0: Game over, 1: crash, -1: not crash, 2: got img_coin
    public int isCrash() {
        int temp = board[NUM_ROW-1][playerPos];
        switch (temp){
            case (COIN):
            board[NUM_ROW-1][playerPos] = NONE;
            return 2;

            case (JELLYFISH):
            board[NUM_ROW - 1][playerPos] = NONE;
            numOfHearts--;
            if (numOfHearts == 0)
                return 0;
            return 1;

        default:
            for (int i = 0; i < NUM_COL; i++) {
                if (board[NUM_ROW - 1][i] != NONE)
                    board[NUM_ROW - 1][i] = NONE;
            }
            return -1;
        }
    }



    public boolean checkLastRow() {
        for (int i =0; i<board[0].length;i++) {
            if (board[NUM_ROW-1][i] != NONE)
                return true;
        }
        return false;
    }

    public void addJellyfish() {
        if (numOfJellyfish < 2) {
            addOneJellyfish();
            int new_jellyfishCol;
            do {
                new_jellyfishCol = rand.nextInt(5);

            } while (!checkAvailable(new_jellyfishCol));
            board[0][new_jellyfishCol] = JELLYFISH;
        }
    }

    public void addCoin() {
            int new_coinCol;
            do {
                new_coinCol = rand.nextInt(5);

            } while (!checkAvailable(new_coinCol));
            board[0][new_coinCol] = COIN;
    }

    public void addOneJellyfish() {
        numOfJellyfish++;
    }

    public  int[] getPlayerBoard() {
        return playerBoard;
    }


    public  int[][] getBoard() {
        return board;
    }

    public void setNumOfJellyfish(int numOfJellyfish) {
        this.numOfJellyfish = numOfJellyfish;
    }

    public int getNumOfJellyfish() {
        return numOfJellyfish;
    }

    public void setPlayerPos(int playerPosNew) {
        playerBoard[playerPos] = NONE;
        this.playerPos = playerPosNew;
        playerBoard[playerPos] = PLAYER;

    }

    public int getPlayerPos() {
        return playerPos;
    }


    public int getNumOfHearts() {
        return numOfHearts;
    }


}
