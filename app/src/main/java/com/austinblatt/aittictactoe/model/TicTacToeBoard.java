package com.austinblatt.aittictactoe.model;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeBoard {

    private static final int BOARD_SIDE_LENGTH = 3;

    public boolean isWon() {
        return !(winner == null);
    }

    public boolean isOver() {
        return (isWon() || isFilled());
    }

    public String getWinner() {
        if(winner == boardPiece.BOARD_X) {
            return "X";
        } else {
            return "O";
        }
    }

    public enum boardPiece {
        BOARD_X, BOARD_O
    }

    private boardPiece winner;
    private List<TicTacToePlay> plays = new ArrayList<TicTacToePlay>();
    private boardPiece[][] boardArray;

    public TicTacToeBoard() {
        clearGame();
    }

    public void clearGame() {
        plays.clear();
        boardArray = new boardPiece[BOARD_SIDE_LENGTH][BOARD_SIDE_LENGTH];
        winner = null;
    }

    private void play(boardPiece p, Pair<Integer, Integer> pos) {
        plays.add(new TicTacToePlay(p, pos));
        boardArray[pos.first][pos.second] = p;

        checkIfGameWasWon(p, pos);
    }

    public void playX(Pair<Integer, Integer> pos) {
        if(pos.first < 3 && pos.second < 3) {
            play(boardPiece.BOARD_X, pos);
        } else {
            throw new IllegalArgumentException("Attempted to play an X on a non-existent board location.");
        }
    }

    public void playO(Pair<Integer, Integer> pos) {
        if(pos.first < 3 && pos.second < 3) {
            play(boardPiece.BOARD_O, pos);
        } else {
            throw new IllegalArgumentException("Attempted to play a Y on a non-existent board location.");
        }
    }

    public boolean isEmpty(Pair<Integer, Integer> pos) {
        for (TicTacToePlay play : plays) {
            if(play.equals(pos)) {
                return false;
            }
        }
        return true;
    }

    public boolean isFilled() {
        return (plays.size() == 9);
    }

    public void checkIfGameWasWon(boardPiece p, Pair<Integer, Integer> pos) {
        checkRow(p, pos);
        checkColumn(p, pos);
        checkDiags(p, pos);
    }

    private void checkDiags(boardPiece p, Pair<Integer, Integer> pos) {
        int x = pos.first;
        int y = pos.second;

        //check diag
        if(x == y){
            //we're on a diagonal
            for(int i = 0; i < BOARD_SIDE_LENGTH; i++){
                if(boardArray[i][i] != p)
                    break;
                if(i == BOARD_SIDE_LENGTH - 1){
                    winner = p;
                }
            }
        }

        //check anti diag (thanks rampion)
        if(x + y == BOARD_SIDE_LENGTH - 1){
            for(int i = 0; i < BOARD_SIDE_LENGTH;i++){
                if(boardArray[i][(BOARD_SIDE_LENGTH - 1)-i] != p)
                    break;
                if(i == BOARD_SIDE_LENGTH - 1){
                    winner = p;
                }
            }
        }
    }

    private void checkRow(boardPiece p, Pair<Integer, Integer> pos) {
        int y = pos.second;

        for(int i = 0; i < BOARD_SIDE_LENGTH; i++){
            if(boardArray[i][y] != p) {
                break;
            }

            if(i == BOARD_SIDE_LENGTH - 1){
                winner = p;
            }
        }
    }

    private void checkColumn(boardPiece p, Pair<Integer, Integer> pos) {
        int x = pos.first;

        for(int i = 0; i < BOARD_SIDE_LENGTH; i++){
            if(boardArray[x][i] != p) {
                break;
            }

            if(i == BOARD_SIDE_LENGTH - 1){
                winner = p;
            }
        }

    }

    public List<TicTacToePlay> boardPlays() {
        return plays;
    }


}
