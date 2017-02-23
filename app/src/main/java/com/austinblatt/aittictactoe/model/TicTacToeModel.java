package com.austinblatt.aittictactoe.model;

public class TicTacToeModel {

    private static final int BOARD_SIDE_LENGTH = 3;
    private static TicTacToeModel instance = null;

    private TicTacToeModel() {
    }

    public static  TicTacToeModel getInstance() {
        if(instance == null) {
            instance = new TicTacToeModel();
        }

        return instance;
    }

    public static final short EMPTY = 0;
    public static final short CIRCLE = 1;
    public static final short CROSS = 2;

    private short[][] model = {
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
    };

    public short getNextPlayer() {
        return nextPlayer;
    }

    private short nextPlayer = CIRCLE;
    private short winner = 0;

    public short checkWinner() {
        return winner;
    }

    public void resetModel() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                model[i][j] = EMPTY;
            }
        }

        nextPlayer = CIRCLE;
        winner = 0;
    }

    public void makeMove(int x, int y, short player) {
        model[y][x] = player;

        checkIfGameWasWon(player, x, y);
    }

    public short getFieldContent(int x, int y) {
        return model[y][x];
    }

    public void changeNextPlayer() {
        if(nextPlayer == CIRCLE) {
            nextPlayer = CROSS;
        } else {
            nextPlayer = CIRCLE;
        }
    }

    public void checkIfGameWasWon(short p, int x, int y) {
        checkRow(p, x, y);
        checkColumn(p, x, y);
        checkDiags(p, x, y);
    }

    private void checkDiags(short p, int x, int y) {

        //check diag
        if(x == y){
            //we're on a diagonal
            for(int i = 0; i < BOARD_SIDE_LENGTH; i++){
                if(model[i][i] != p)
                    break;
                if(i == BOARD_SIDE_LENGTH - 1){
                    winner = p;
                }
            }
        }

        //check anti diag (thanks rampion)
        if(x + y == BOARD_SIDE_LENGTH - 1){
            for(int i = 0; i < BOARD_SIDE_LENGTH;i++){
                if(model[i][(BOARD_SIDE_LENGTH - 1)-i] != p)
                    break;
                if(i == BOARD_SIDE_LENGTH - 1){
                    winner = p;
                }
            }
        }
    }

    private void checkRow(short p, int x, int y) {

        for(int i = 0; i < BOARD_SIDE_LENGTH; i++){
            if(model[y][i] != p) {
                break;
            }

            if(i == BOARD_SIDE_LENGTH - 1){
                winner = p;
            }
        }
    }

    private void checkColumn(short p, int x, int y) {

        for(int i = 0; i < BOARD_SIDE_LENGTH; i++){
            if(model[i][x] != p) {
                break;
            }

            if(i == BOARD_SIDE_LENGTH - 1){
                winner = p;
            }
        }

    }

}
