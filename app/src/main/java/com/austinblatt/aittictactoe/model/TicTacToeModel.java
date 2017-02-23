package com.austinblatt.aittictactoe.model;

public class TicTacToeModel {

    private static TicTacToeModel instance = null;

    private TicTacToeModel() {
    }

    public static  TicTacToeModel getInstance() {
        if(instance == null) {
            instance = new TicTacToeModel();
        }

        return instance;
    }
}
