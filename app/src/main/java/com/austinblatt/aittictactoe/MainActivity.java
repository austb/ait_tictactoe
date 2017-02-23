package com.austinblatt.aittictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.austinblatt.aittictactoe.model.TicTacToeBoard;
import com.austinblatt.aittictactoe.view.TicTacToeView;

public class MainActivity extends AppCompatActivity implements TicTacToeView.OnPlayMadeListener {


    private TicTacToeView tttView;
    private Button clearBoardBtn;
    private TicTacToeBoard board;
    private TextView gameOverMsg;

    private boolean isXTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tttView = (TicTacToeView) findViewById(R.id.TTTView);
        clearBoardBtn = (Button) findViewById(R.id.clear_board_btn);
        gameOverMsg = (TextView) findViewById(R.id.gameOverMsg);

        connectModelToView();

        initNewGameBtn();

    }

    private void initNewGameBtn() {
        clearBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.clearGame();
                tttView.invalidate();
                isXTurn = true;
                gameOverMsg.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void connectModelToView() {
        board = new TicTacToeBoard();

        tttView.setBoard(board);
        tttView.setOnPlayMadeListener(this);
    }

    @Override
    public void onPlayMade(View v, Pair<Float, Float> screenLocation) {
        Pair<Integer, Integer> pos = calculateBoardLocation(v, screenLocation);

        if(board.isEmpty(pos) && !board.isOver()) {
            playPieceOnBoard(pos);
        } else if (!board.isOver()) {
            displayPositionFilledMsg(pos);
        }

        if(board.isWon()) {
            displayGameOverMsg(getString(R.string.playerWonMsg, board.getWinner()));
        } else if(board.isOver()) {
            displayGameOverMsg(getString(R.string.gameEndInTie));
        }
    }

    private void displayGameOverMsg(String str) {
        gameOverMsg.setText(str);
        gameOverMsg.setVisibility(View.VISIBLE);
    }

    private void displayPositionFilledMsg(Pair<Integer, Integer> pos) {
        String str = getString(R.string.boardPositionFilled, pos.first+1, pos.second+1);
        Toast.makeText(getApplicationContext(), str,
                Toast.LENGTH_SHORT).show();
    }

    private void playPieceOnBoard(Pair<Integer, Integer> pos) {
        if(isXTurn) {
            board.playX(pos);
        } else {
            board.playO(pos);
        }
        isXTurn = !isXTurn;
    }


    private Pair<Integer, Integer> calculateBoardLocation(View v, Pair<Float, Float> screenLocation) {
        int boardColumnWidth = v.getWidth() / 3;
        int boardColumnHeight = v.getHeight() / 3;

        return new Pair<>((int) (screenLocation.first / boardColumnWidth),
                (int) (screenLocation.second / boardColumnHeight));
    }
}
