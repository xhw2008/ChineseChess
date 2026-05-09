package com.example.chineschess;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ChessBoardView.OnBoardInteractionListener {
    private ChessBoard board;
    private ChessBoardView boardView;
    private HintManager hintManager;
    private TextView turnText;
    private TextView hintText;
    private CheckBox hintCheckBox;
    private CheckBox riskCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = new ChessBoard();
        hintManager = new HintManager(board);

        boardView = findViewById(R.id.chess_board);
        boardView.setBoard(board);
        boardView.setOnBoardInteractionListener(this);

        turnText = findViewById(R.id.turn_text);
        hintText = findViewById(R.id.hint_text);
        hintCheckBox = findViewById(R.id.hint_checkbox);
        riskCheckBox = findViewById(R.id.risk_checkbox);

        Button newGameBtn = findViewById(R.id.new_game_btn);
        Button undoBtn = findViewById(R.id.undo_btn);
        Button hintBtn = findViewById(R.id.hint_btn);

        newGameBtn.setOnClickListener(v -> resetGame());
        undoBtn.setOnClickListener(v -> undoMove());
        hintBtn.setOnClickListener(v -> showHint());

        hintCheckBox.setChecked(hintManager.isShowHints());
        riskCheckBox.setChecked(hintManager.isShowRiskWarnings());

        hintCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            hintManager.setShowHints(isChecked);
            boardView.hideSuggestion();
            updateHints();
        });

        riskCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            hintManager.setShowRiskWarnings(isChecked);
            boardView.invalidate();
        });

        updateTurnText();
        updateHints();
    }

    @Override
    public void onCellClicked(int x, int y) {
        if (board.isGameOver()) {
            Toast.makeText(this, R.string.hint_checkmate, Toast.LENGTH_SHORT).show();
            return;
        }

        ChessPiece selectedPiece = board.getSelectedPiece();
        
        if (selectedPiece != null) {
            if (selectedPiece.getX() == x && selectedPiece.getY() == y) {
                board.deselectPiece();
                boardView.clearSelection();
                boardView.hideSuggestion();
            } else {
                boolean moved = board.movePiece(x, y);
                if (moved) {
                    boardView.clearSelection();
                    boardView.hideSuggestion();
                    updateTurnText();
                    updateHints();
                    
                    if (board.isGameOver()) {
                        String winner = board.getWinner() == ChessPiece.PieceColor.RED ? "红方" : "黑方";
                        Toast.makeText(this, winner + "获胜！", Toast.LENGTH_LONG).show();
                    }
                } else {
                    ChessPiece targetPiece = board.getPiece(x, y);
                    if (targetPiece != null && targetPiece.getColor() == board.getCurrentTurn()) {
                        board.selectPiece(x, y);
                        boardView.setSelectedPosition(x, y);
                        updateValidMoves();
                        boardView.hideSuggestion();
                    } else {
                        Toast.makeText(this, R.string.hint_invalid_move, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else {
            ChessPiece piece = board.getPiece(x, y);
            if (piece != null && piece.getColor() == board.getCurrentTurn()) {
                board.selectPiece(x, y);
                boardView.setSelectedPosition(x, y);
                updateValidMoves();
                boardView.hideSuggestion();
            }
        }
    }

    private void updateValidMoves() {
        ChessPiece selectedPiece = board.getSelectedPiece();
        if (selectedPiece != null) {
            List<HintManager.Position> validMoves = hintManager.getValidMoves(selectedPiece);
            boardView.setValidMoves(validMoves);
        }
    }

    private void updateTurnText() {
        String turn = board.getCurrentTurn() == ChessPiece.PieceColor.RED ? 
            getString(R.string.red_turn) : getString(R.string.black_turn);
        turnText.setText(turn);
    }

    private void updateHints() {
        List<HintManager.Hint> hints = hintManager.getHints();
        List<HintManager.RiskWarning> warnings = hintManager.getRiskWarnings();
        
        StringBuilder hintMessage = new StringBuilder();
        
        for (HintManager.Hint hint : hints) {
            if (hintMessage.length() > 0) hintMessage.append("\n");
            hintMessage.append(hint.getMessage());
        }
        
        for (HintManager.RiskWarning warning : warnings) {
            if (hintMessage.length() > 0) hintMessage.append("\n");
            hintMessage.append(warning.getMessage());
        }
        
        hintText.setText(hintMessage.toString());
        hintText.setVisibility(hintMessage.length() > 0 ? View.VISIBLE : View.GONE);
    }

    private void showHint() {
        HintManager.HintSuggestion suggestion = hintManager.getBestMoveSuggestion();
        if (suggestion != null) {
            boardView.showSuggestion(suggestion);
            String pieceName = suggestion.getPiece().getTypeName();
            String moveStr = String.format("%s (%d,%d) -> (%d,%d)", 
                pieceName, 
                suggestion.getPiece().getX(), suggestion.getPiece().getY(),
                suggestion.getTarget().x, suggestion.getTarget().y);
            Toast.makeText(this, getString(R.string.hint_move_hint) + moveStr, Toast.LENGTH_LONG).show();
        }
    }

    private void undoMove() {
        board.undoLastMove();
        boardView.clearSelection();
        boardView.hideSuggestion();
        updateTurnText();
        updateHints();
        boardView.invalidate();
    }

    private void resetGame() {
        board.resetBoard();
        boardView.clearSelection();
        boardView.hideSuggestion();
        updateTurnText();
        updateHints();
        boardView.invalidate();
    }
}
