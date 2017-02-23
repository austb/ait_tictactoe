package com.austinblatt.aittictactoe.model;


import android.util.Pair;

public class TicTacToePlay {

    private TicTacToeBoard.boardPiece player;

    private Pair<Integer, Integer> boardLocation;

    public TicTacToePlay(TicTacToeBoard.boardPiece player, Pair<Integer, Integer> pos) {
        this.player = player;

        this.boardLocation = pos;
    }

    public Pair<Integer, Integer> getPosition() {
        return this.boardLocation;
    }

    public boolean isX() {
        return (player == TicTacToeBoard.boardPiece.BOARD_X);
    }

    public boolean equals(Pair<Integer, Integer> obj) {
        return boardLocation.equals(obj);
    }
}