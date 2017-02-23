package com.austinblatt.aittictactoe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.austinblatt.aittictactoe.MainActivity;
import com.austinblatt.aittictactoe.R;
import com.austinblatt.aittictactoe.model.TicTacToeModel;

public class TicTacToeView extends View {

    private final TicTacToeModel board;
    private Paint paintBg;
    private Paint paintLine;


    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBg = new Paint();
        paintBg.setColor(Color.BLACK);
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        board = TicTacToeModel.getInstance();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);

        drawBoardLines(canvas);
        drawBoardPieces(canvas);
    }

    private void drawBoardPieces(Canvas canvas) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                short piece = board.getFieldContent(i, j);
                if(piece != TicTacToeModel.EMPTY) {
                    drawSingleBoardPiece(canvas, piece, i, j);
                }
            }
        }
    }

    private void drawSingleBoardPiece(Canvas canvas, short piece, int x, int y) {
        if (piece == TicTacToeModel.CIRCLE) {
            drawCircleAtPosition(canvas, x, y);
        } else {
            drawCrossAtPosition(canvas, x, y);
        }
    }


    private void drawCircleAtPosition(Canvas canvas, int x, int y) {
        int scale = getWidth() / 3;
        
        int xPos = x * scale + scale / 2;
        int yPos = y * scale + scale / 2;
        canvas.drawCircle(xPos, yPos, scale / 4, paintLine);
    }

    private void drawCrossAtPosition(Canvas canvas, int x, int y) {
        int scale = getWidth() / 3;

        int xPos = x * scale + scale / 4;
        int yPos = y * scale + scale / 4;

        canvas.drawLine(xPos, yPos, xPos + scale / 2, yPos + scale / 2, paintLine);
        canvas.drawLine(xPos + scale / 2, yPos, xPos, yPos + scale / 2, paintLine);
    }


    private void drawBoardLines(Canvas canvas) {
        canvas.drawLine(getWidth()/3, 0, getWidth()/3, getHeight(), paintLine);
        canvas.drawLine(getWidth()*2/3, 0, getWidth()*2/3, getHeight(), paintLine);

        canvas.drawLine(0, getHeight()/3, getWidth(), getHeight()/3, paintLine);
        canvas.drawLine(0, getHeight()*2/3, getWidth(), getHeight()*2/3, paintLine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            int x = calculateBoardLocation(event.getX());
            int y = calculateBoardLocation(event.getY());

            if(board.getFieldContent(x,y) == TicTacToeModel.EMPTY && board.checkWinner() == 0) {
                board.makeMove(x, y, board.getNextPlayer());
                board.changeNextPlayer();
            }

            MainActivity activity = (MainActivity) getContext();
            if(board.checkWinner() == TicTacToeModel.CIRCLE) {
                activity.setStatusText("O's win");
            } else if(board.checkWinner() == TicTacToeModel.CROSS) {
                activity.setStatusText("X's win");
            } else {
                activity.setStatusText(board.getNextPlayer() + "'s turn");
            }

            invalidate();
        }

        return true;
    }

    private int calculateBoardLocation(float screen) {
        int boardWidth = getWidth() / 3;

        return (int) screen / boardWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }
}
