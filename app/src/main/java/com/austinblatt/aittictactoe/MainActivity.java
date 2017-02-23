package com.austinblatt.aittictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.austinblatt.aittictactoe.model.TicTacToeModel;
import com.austinblatt.aittictactoe.view.TicTacToeView;
import com.facebook.shimmer.ShimmerFrameLayout;

public class MainActivity extends AppCompatActivity {

    private TicTacToeView tttView;
    private Button clearBoardBtn;
    private TicTacToeModel board;
    private TextView gameStatusMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tttView = (TicTacToeView) findViewById(R.id.TTTView);
        clearBoardBtn = (Button) findViewById(R.id.clear_board_btn);
        board = TicTacToeModel.getInstance();

        gameStatusMsg = (TextView) findViewById(R.id.gameStatus);
        gameStatusMsg.setText("1's turn");

        ShimmerFrameLayout shimmer = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        shimmer.startShimmerAnimation();

        initNewGameBtn();

    }

    private void initNewGameBtn() {
        clearBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.resetModel();
                gameStatusMsg.setText("1's turn");
                tttView.invalidate();
            }
        });
    }

    public void setStatusText(String text) {
        gameStatusMsg.setText(text);
    }
}
