package com.example.chineschess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class ChessBoardView extends View {
    private ChessBoard board;
    private HintManager hintManager;
    private float cellSize;
    private float padding;
    private Paint paint;
    private int selectedX = -1, selectedY = -1;
    private List<HintManager.Position> validMoves;
    private OnBoardInteractionListener listener;
    private boolean showSuggestion;
    private HintManager.HintSuggestion currentSuggestion;

    public ChessBoardView(Context context) {
        super(context);
        init();
    }

    public ChessBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChessBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        showSuggestion = false;
    }

    public void setBoard(ChessBoard board) {
        this.board = board;
        this.hintManager = new HintManager(board);
        invalidate();
    }

    public void setOnBoardInteractionListener(OnBoardInteractionListener listener) {
        this.listener = listener;
    }

    public void setValidMoves(List<HintManager.Position> moves) {
        this.validMoves = moves;
        invalidate();
    }

    public void clearValidMoves() {
        this.validMoves = null;
        invalidate();
    }

    public void setSelectedPosition(int x, int y) {
        this.selectedX = x;
        this.selectedY = y;
        invalidate();
    }

    public void clearSelection() {
        this.selectedX = -1;
        this.selectedY = -1;
        this.validMoves = null;
        invalidate();
    }

    public void showSuggestion(HintManager.HintSuggestion suggestion) {
        this.currentSuggestion = suggestion;
        this.showSuggestion = true;
        invalidate();
    }

    public void hideSuggestion() {
        this.showSuggestion = false;
        this.currentSuggestion = null;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        
        float boardWidth = (float) ChessBoard.BOARD_WIDTH / ChessBoard.BOARD_HEIGHT * height;
        
        if (boardWidth <= width) {
            setMeasuredDimension((int) boardWidth, height);
        } else {
            float boardHeight = (float) ChessBoard.BOARD_HEIGHT / ChessBoard.BOARD_WIDTH * width;
            setMeasuredDimension(width, (int) boardHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        if (board == null) return;
        
        int width = getWidth();
        int height = getHeight();
        
        cellSize = Math.min(width / (float) ChessBoard.BOARD_WIDTH, height / (float) ChessBoard.BOARD_HEIGHT);
        padding = (width - cellSize * ChessBoard.BOARD_WIDTH) / 2;
        
        drawBoard(canvas);
        drawGrid(canvas);
        drawCrossPoints(canvas);
        drawValidMoves(canvas);
        drawSuggestion(canvas);
        drawPieces(canvas);
        drawSelection(canvas);
    }

    private void drawBoard(Canvas canvas) {
        paint.setColor(Color.rgb(139, 90, 43));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }

    private void drawGrid(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        
        for (int i = 0; i <= ChessBoard.BOARD_WIDTH; i++) {
            float x = padding + i * cellSize;
            canvas.drawLine(x, 0, x, getHeight(), paint);
        }
        
        for (int i = 0; i <= ChessBoard.BOARD_HEIGHT; i++) {
            float y = i * cellSize;
            canvas.drawLine(padding, y, padding + ChessBoard.BOARD_WIDTH * cellSize, y, paint);
        }
    }

    private void drawCrossPoints(Canvas canvas) {
        paint.setColor(Color.BLACK);
        
        for (int x = 0; x < ChessBoard.BOARD_WIDTH; x++) {
            for (int y = 0; y < ChessBoard.BOARD_HEIGHT; y++) {
                if ((x == 2 || x == 6) && (y == 1 || y == 7)) {
                    float centerX = padding + x * cellSize + cellSize / 2;
                    float centerY = y * cellSize + cellSize / 2;
                    canvas.drawCircle(centerX, centerY, 4, paint);
                }
                if ((x == 4) && (y == 3 || y == 5)) {
                    float centerX = padding + x * cellSize + cellSize / 2;
                    float centerY = y * cellSize + cellSize / 2;
                    canvas.drawCircle(centerX, centerY, 4, paint);
                }
            }
        }
        
        for (int x = 3; x <= 5; x++) {
            for (int y = 0; y <= 2; y++) {
                float centerX = padding + x * cellSize + cellSize / 2;
                float centerY = y * cellSize + cellSize / 2;
                canvas.drawCircle(centerX, centerY, 3, paint);
            }
            for (int y = 7; y <= 9; y++) {
                float centerX = padding + x * cellSize + cellSize / 2;
                float centerY = y * cellSize + cellSize / 2;
                canvas.drawCircle(centerX, centerY, 3, paint);
            }
        }
    }

    private void drawValidMoves(Canvas canvas) {
        if (validMoves == null) return;
        
        paint.setColor(Color.GREEN);
        paint.setAlpha(128);
        
        for (HintManager.Position pos : validMoves) {
            float centerX = padding + pos.x * cellSize + cellSize / 2;
            float centerY = pos.y * cellSize + cellSize / 2;
            canvas.drawCircle(centerX, centerY, cellSize / 4, paint);
        }
    }

    private void drawSuggestion(Canvas canvas) {
        if (!showSuggestion || currentSuggestion == null) return;
        
        paint.setColor(Color.BLUE);
        paint.setAlpha(150);
        
        float fromX = padding + currentSuggestion.getPiece().getX() * cellSize + cellSize / 2;
        float fromY = currentSuggestion.getPiece().getY() * cellSize + cellSize / 2;
        float toX = padding + currentSuggestion.getTarget().x * cellSize + cellSize / 2;
        float toY = currentSuggestion.getTarget().y * cellSize + cellSize / 2;
        
        paint.setStrokeWidth(3);
        canvas.drawLine(fromX, fromY, toX, toY, paint);
        
        paint.setAlpha(128);
        canvas.drawCircle(toX, toY, cellSize / 3, paint);
    }

    private void drawPieces(Canvas canvas) {
        for (int x = 0; x < ChessBoard.BOARD_WIDTH; x++) {
            for (int y = 0; y < ChessBoard.BOARD_HEIGHT; y++) {
                ChessPiece piece = board.getPiece(x, y);
                if (piece != null && !piece.isCaptured()) {
                    drawPiece(canvas, piece, x, y);
                }
            }
        }
    }

    private void drawPiece(Canvas canvas, ChessPiece piece, int x, int y) {
        float centerX = padding + x * cellSize + cellSize / 2;
        float centerY = y * cellSize + cellSize / 2;
        float radius = cellSize / 2 - 4;
        
        if (hintManager.isShowRiskWarnings() && hintManager.isPieceThreatened(piece)) {
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(centerX, centerY, radius + 2, paint);
        }
        
        if (piece.getColor() == ChessPiece.PieceColor.RED) {
            paint.setColor(Color.rgb(238, 238, 238));
        } else {
            paint.setColor(Color.rgb(60, 60, 60));
        }
        
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius, paint);
        
        paint.setColor(piece.getColor() == ChessPiece.PieceColor.RED ? Color.RED : Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawCircle(centerX, centerY, radius, paint);
        
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(cellSize * 0.6f);
        paint.setTextAlign(Paint.Align.CENTER);
        
        String symbol = piece.getSymbol();
        Rect bounds = new Rect();
        paint.getTextBounds(symbol, 0, symbol.length(), bounds);
        float textY = centerY - bounds.exactCenterY();
        
        canvas.drawText(symbol, centerX, textY, paint);
    }

    private void drawSelection(Canvas canvas) {
        if (selectedX < 0 || selectedY < 0) return;
        
        float left = padding + selectedX * cellSize + 2;
        float top = selectedY * cellSize + 2;
        float right = left + cellSize - 4;
        float bottom = top + cellSize - 4;
        
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        canvas.drawRect(left, top, right, bottom, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) ((event.getX() - padding) / cellSize);
            int y = (int) (event.getY() / cellSize);
            
            if (x >= 0 && x < ChessBoard.BOARD_WIDTH && y >= 0 && y < ChessBoard.BOARD_HEIGHT) {
                if (listener != null) {
                    listener.onCellClicked(x, y);
                }
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    public interface OnBoardInteractionListener {
        void onCellClicked(int x, int y);
    }
}
