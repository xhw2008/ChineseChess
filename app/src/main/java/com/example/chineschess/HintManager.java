package com.example.chineschess;

import java.util.ArrayList;
import java.util.List;

public class HintManager {
    private ChessBoard board;
    private boolean showHints;
    private boolean showRiskWarnings;

    public HintManager(ChessBoard board) {
        this.board = board;
        this.showHints = true;
        this.showRiskWarnings = true;
    }

    public void setShowHints(boolean showHints) {
        this.showHints = showHints;
    }

    public void setShowRiskWarnings(boolean showRiskWarnings) {
        this.showRiskWarnings = showRiskWarnings;
    }

    public boolean isShowHints() {
        return showHints;
    }

    public boolean isShowRiskWarnings() {
        return showRiskWarnings;
    }

    public List<Position> getValidMoves(ChessPiece piece) {
        List<Position> moves = new ArrayList<>();
        if (piece == null) return moves;
        
        for (int x = 0; x < ChessBoard.BOARD_WIDTH; x++) {
            for (int y = 0; y < ChessBoard.BOARD_HEIGHT; y++) {
                if (board.isValidMove(piece, x, y)) {
                    moves.add(new Position(x, y));
                }
            }
        }
        return moves;
    }

    public List<Hint> getHints() {
        List<Hint> hints = new ArrayList<>();
        
        if (!showHints) return hints;
        
        ChessPiece.PieceColor currentTurn = board.getCurrentTurn();
        
        if (board.isCheck(currentTurn)) {
            hints.add(new Hint(HintType.CHECK, "将军！请保护你的帅/将"));
        }
        
        return hints;
    }

    public List<RiskWarning> getRiskWarnings() {
        List<RiskWarning> warnings = new ArrayList<>();
        
        if (!showRiskWarnings) return warnings;
        
        ChessPiece.PieceColor currentTurn = board.getCurrentTurn();
        ChessPiece.PieceColor opponent = currentTurn == ChessPiece.PieceColor.RED ? 
            ChessPiece.PieceColor.BLACK : ChessPiece.PieceColor.RED;
        
        for (int x = 0; x < ChessBoard.BOARD_WIDTH; x++) {
            for (int y = 0; y < ChessBoard.BOARD_HEIGHT; y++) {
                ChessPiece piece = board.getPiece(x, y);
                if (piece != null && piece.getColor() == currentTurn) {
                    if (isPieceThreatened(piece)) {
                        warnings.add(new RiskWarning(piece, RiskType.BEING_ATTACKED, 
                            piece.getSymbol() + " 正在被威胁！"));
                    }
                }
            }
        }
        
        return warnings;
    }

    public boolean isPieceThreatened(ChessPiece piece) {
        ChessPiece.PieceColor opponent = piece.getColor() == ChessPiece.PieceColor.RED ? 
            ChessPiece.PieceColor.BLACK : ChessPiece.PieceColor.RED;
        
        for (int x = 0; x < ChessBoard.BOARD_WIDTH; x++) {
            for (int y = 0; y < ChessBoard.BOARD_HEIGHT; y++) {
                ChessPiece attacker = board.getPiece(x, y);
                if (attacker != null && attacker.getColor() == opponent) {
                    if (board.isValidMove(attacker, piece.getX(), piece.getY())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public HintSuggestion getBestMoveSuggestion() {
        if (!showHints) return null;
        
        ChessPiece.PieceColor currentTurn = board.getCurrentTurn();
        List<HintSuggestion> suggestions = new ArrayList<>();
        
        for (int x = 0; x < ChessBoard.BOARD_WIDTH; x++) {
            for (int y = 0; y < ChessBoard.BOARD_HEIGHT; y++) {
                ChessPiece piece = board.getPiece(x, y);
                if (piece != null && piece.getColor() == currentTurn) {
                    List<Position> validMoves = getValidMoves(piece);
                    for (Position pos : validMoves) {
                        int score = evaluateMove(piece, pos);
                        suggestions.add(new HintSuggestion(piece, pos, score));
                    }
                }
            }
        }
        
        if (suggestions.isEmpty()) return null;
        
        HintSuggestion best = suggestions.get(0);
        for (HintSuggestion s : suggestions) {
            if (s.getScore() > best.getScore()) {
                best = s;
            }
        }
        
        return best;
    }

    private int evaluateMove(ChessPiece piece, Position target) {
        int score = 0;
        
        ChessPiece targetPiece = board.getPiece(target.x, target.y);
        
        if (targetPiece != null) {
            score += getPieceValue(targetPiece) * 10;
        }
        
        if (piece.getType() == ChessPiece.PieceType.KING) {
            score += isNearOpponentKing(target) ? 5 : 0;
        }
        
        if (isPieceThreatened(piece)) {
            score += 5;
        }
        
        if (board.isCheck(board.getOpponentColor(piece.getColor()))) {
            score += 20;
        }
        
        return score;
    }

    private int getPieceValue(ChessPiece piece) {
        switch (piece.getType()) {
            case KING: return 100;
            case CHARIOT: return 10;
            case CANNON: return 6;
            case HORSE: return 4;
            case ELEPHANT: return 2;
            case GUARD: return 2;
            case PAWN: return 1;
            default: return 0;
        }
    }

    private boolean isNearOpponentKing(Position pos) {
        ChessPiece.PieceColor opponent = board.getCurrentTurn() == ChessPiece.PieceColor.RED ? 
            ChessPiece.PieceColor.BLACK : ChessPiece.PieceColor.RED;
        
        for (int x = 3; x <= 5; x++) {
            int minY = opponent == ChessPiece.PieceColor.RED ? 7 : 0;
            int maxY = opponent == ChessPiece.PieceColor.RED ? 9 : 2;
            for (int y = minY; y <= maxY; y++) {
                ChessPiece piece = board.getPiece(x, y);
                if (piece != null && piece.getType() == ChessPiece.PieceType.KING && piece.getColor() == opponent) {
                    int dx = Math.abs(pos.x - x);
                    int dy = Math.abs(pos.y - y);
                    return dx <= 2 && dy <= 2;
                }
            }
        }
        return false;
    }

    public static class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public enum HintType {
        CHECK, CAPTURE, MOVE_SUGGESTION
    }

    public enum RiskType {
        BEING_ATTACKED, CHECK, AMBUSH
    }

    public static class Hint {
        private HintType type;
        private String message;

        public Hint(HintType type, String message) {
            this.type = type;
            this.message = message;
        }

        public HintType getType() { return type; }
        public String getMessage() { return message; }
    }

    public static class RiskWarning {
        private ChessPiece piece;
        private RiskType type;
        private String message;

        public RiskWarning(ChessPiece piece, RiskType type, String message) {
            this.piece = piece;
            this.type = type;
            this.message = message;
        }

        public ChessPiece getPiece() { return piece; }
        public RiskType getType() { return type; }
        public String getMessage() { return message; }
    }

    public static class HintSuggestion {
        private ChessPiece piece;
        private Position target;
        private int score;

        public HintSuggestion(ChessPiece piece, Position target, int score) {
            this.piece = piece;
            this.target = target;
            this.score = score;
        }

        public ChessPiece getPiece() { return piece; }
        public Position getTarget() { return target; }
        public int getScore() { return score; }
    }
}
