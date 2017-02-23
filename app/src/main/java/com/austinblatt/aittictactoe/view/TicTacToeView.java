package com.austinblatt.aittictactoe.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.austinblatt.aittictactoe.model.TicTacToeBoard;
import com.austinblatt.aittictactoe.model.TicTacToePlay;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.height;
import static android.R.attr.y;


public class TicTacToeView extends View {

    private Paint paintLine;
    private Paint paintBg;

    private TicTacToeBoard board;

    private OnPlayMadeListener listener;

    public interface OnPlayMadeListener {
        void onPlayMade(View v, Pair<Float, Float> screenLocation);
    }

    public void setOnPlayMadeListener(OnPlayMadeListener l) {
        listener = l;
    }

    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        createPaints();
    }

    private void createPaints() {
        paintBg = new Paint();
        paintBg.setColor(Color.BLACK);
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);

        drawBoardLines(canvas);
        drawBoardPieces(canvas);
    }

    private void drawBoardPieces(Canvas canvas) {
        int scale = getWidth() / 3;

        int xPos = 0;
        int yPos = 0;

        if(board != null) {
            for (TicTacToePlay p : board.boardPlays()) {
                drawSingleBoardPiece(canvas, scale, p);
            }
        }
    }

    private void drawSingleBoardPiece(Canvas canvas, int scale, TicTacToePlay p) {
        Pair<Integer, Integer> pos = p.getPosition();

        if (p.isX()) {
            drawBoardXAtPosition(canvas, scale, pos);
        } else {
            drawBoardOAtPosition(canvas, scale, pos);
        }
    }

    private void drawBoardOAtPosition(Canvas canvas, int scale, Pair<Integer, Integer> pos) {
        int xPos = pos.first * scale + scale / 2;
        int yPos = pos.second * scale + scale / 2;
        canvas.drawCircle(xPos, yPos, scale / 4, paintLine);
    }

    private void drawBoardXAtPosition(Canvas canvas, int scale, Pair<Integer, Integer> pos) {
        int xPos = pos.first * scale + scale / 4;
        int yPos = pos.second * scale + scale / 4;

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

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Pair<Float, Float> pos = new Pair<>(event.getX(), event.getY());

            if(listener != null) {
                listener.onPlayMade(this, pos);
                invalidate();
            } else {
                throw new RuntimeException("TicTacToeView onPlayMade Listener is unset");
            }

        }
        return super.onTouchEvent(event);
    }

    public void setBoard(TicTacToeBoard board) {
        this.board = board;
    }
}
