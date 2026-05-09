package com.example.chineschess;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    public static final int BOARD_WIDTH = 9;
    public static final int BOARD_HEIGHT = 10;

    private ChessPiece[][] board;
    private ChessPiece.PieceColor currentTurn;
    private ChessPiece selectedPiece;
    private List<Move> moveHistory;
    private boolean gameOver;
    private ChessPiece.PieceColor winner;

    public ChessBoard() {
        board = new ChessPiece[BOARD_WIDTH][BOARD_HEIGHT];
        moveHistory = new ArrayList<>();
        initBoard();
        currentTurn = ChessPiece.PieceColor.RED;
        gameOver = false;
        winner = null;
    }

    private void initBoard() {
        board[0][0] = new ChessPiece(ChessPiece.PieceType.CHARIOT, ChessPiece.PieceColor.BLACK, 0, 0);
        board[1][0] = new ChessPiece(ChessPiece.PieceType.HORSE, ChessPiece.PieceColor.BLACK, 1, 0);
        board[2][0] = new ChessPiece(ChessPiece.PieceType.ELEPHANT, ChessPiece.PieceColor.BLACK, 2, 0);
        board[3][0] = new ChessPiece(ChessPiece.PieceType.GUARD, ChessPiece.PieceColor.BLACK, 3, 0);
        board[4][0] = new ChessPiece(ChessPiece.PieceType.KING, ChessPiece.PieceColor.BLACK, 4, 0);
        board[5][0] = new ChessPiece(ChessPiece.PieceType.GUARD, ChessPiece.PieceColor.BLACK, 5, 0);
        board[6][0] = new ChessPiece(ChessPiece.PieceType.ELEPHANT, ChessPiece.PieceColor.BLACK, 6, 0);
        board[7][0] = new ChessPiece(ChessPiece.PieceType.HORSE, ChessPiece.PieceColor.BLACK, 7, 0);
        board[8][0] = new ChessPiece(ChessPiece.PieceType.CHARIOT, ChessPiece.PieceColor.BLACK, 8, 0);
        
        board[1][2] = new ChessPiece(ChessPiece.PieceType.CANNON, ChessPiece.PieceColor.BLACK, 1, 2);
        board[7][2] = new ChessPiece(ChessPiece.PieceType.CANNON, ChessPiece.PieceColor.BLACK, 7, 2);
        
        board[0][3] = new ChessPiece(ChessPiece.PieceType.PAWN, ChessPiece.PieceColor.BLACK, 0, 3);
        board[2][3] = new ChessPiece(ChessPiece.PieceType.PAWN, ChessPiece.PieceColor.BLACK, 2, 3);
        board[4][3] = new ChessPiece(ChessPiece.PieceType.PAWN, ChessPiece.PieceColor.BLACK, 4, 3);
        board[6][3] = new ChessPiece(ChessPiece.PieceType.PAWN, ChessPiece.PieceColor.BLACK, 6, 3);
        board[8][3] = new ChessPiece(ChessPiece.PieceType.PAWN, ChessPiece.PieceColor.BLACK, 8, 3);
        
        board[0][6] = new ChessPiece(ChessPiece.PieceType.PAWN, ChessPiece.PieceColor.RED, 0, 6);
        board[2][6] = new ChessPiece(ChessPiece.PieceType.PAWN, ChessPiece.PieceColor.RED, 2, 6);
        board[4][6] = new ChessPiece(ChessPiece.PieceType.PAWN, ChessPiece.PieceColor.RED, 4, 6);
        board[6][6] = new ChessPiece(ChessPiece.PieceType.PAWN, ChessPiece.PieceColor.RED, 6, 6);
        board[8][6] = new ChessPiece(ChessPiece.PieceType.PAWN, ChessPiece.PieceColor.RED, 8, 6);
        
        board[1][7] = new ChessPiece(ChessPiece.PieceType.CANNON, ChessPiece.PieceColor.RED, 1, 7);
        board[7][7] = new ChessPiece(ChessPiece.PieceType.CANNON, ChessPiece.PieceColor.RED, 7, 7);
        
        board[0][9] = new ChessPiece(ChessPiece.PieceType.CHARIOT, ChessPiece.PieceColor.RED, 0, 9);
        board[1][9] = new ChessPiece(ChessPiece.PieceType.HORSE, ChessPiece.PieceColor.RED, 1, 9);
        board[2][9] = new ChessPiece(ChessPiece.PieceType.ELEPHANT, ChessPiece.PieceColor.RED, 2, 9);
        board[3][9] = new ChessPiece(ChessPiece.PieceType.GUARD, ChessPiece.PieceColor.RED, 3, 9);
        board[4][9] = new ChessPiece(ChessPiece.PieceType.KING, ChessPiece.PieceColor.RED, 4, 9);
        board[5][9] = new ChessPiece(ChessPiece.PieceType.GUARD, ChessPiece.PieceColor.RED, 5, 9);
        board[6][9] = new ChessPiece(ChessPiece.PieceType.ELEPHANT, ChessPiece.PieceColor.RED, 6, 9);
        board[7][9] = new ChessPiece(ChessPiece.PieceType.HORSE, ChessPiece.PieceColor.RED, 7, 9);
        board[8][9] = new ChessPiece(ChessPiece.PieceType.CHARIOT, ChessPiece.PieceColor.RED, 8, 9);
    }

    public ChessPiece getPiece(int x, int y) {
        if (x >= 0 && x < BOARD_WIDTH && y >= 0 && y < BOARD_HEIGHT) {
            return board[x][y];
        }
        return null;
    }

    public ChessPiece.PieceColor getCurrentTurn() {
        return currentTurn;
    }

    public ChessPiece getSelectedPiece() {
        return selectedPiece;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public ChessPiece.PieceColor getWinner() {
        return winner;
    }

    public void selectPiece(int x, int y) {
        ChessPiece piece = getPiece(x, y);
        if (piece != null && piece.getColor() == currentTurn) {
            selectedPiece = piece;
        }
    }

    public void deselectPiece() {
        selectedPiece = null;
    }

    public boolean movePiece(int targetX, int targetY) {
        if (selectedPiece == null) return false;
        if (gameOver) return false;
        
        int fromX = selectedPiece.getX();
        int fromY = selectedPiece.getY();
        
        if (!isValidMove(selectedPiece, targetX, targetY)) {
            return false;
        }
        
        ChessPiece targetPiece = getPiece(targetX, targetY);
        ChessPiece capturedPiece = targetPiece != null && targetPiece.getColor() != currentTurn ? targetPiece : null;
        
        Move move = new Move(selectedPiece, fromX, fromY, targetX, targetY, capturedPiece);
        moveHistory.add(move);
        
        if (targetPiece != null) {
            targetPiece.setCaptured(true);
        }
        
        board[fromX][fromY] = null;
        board[targetX][targetY] = selectedPiece;
        selectedPiece.setPosition(targetX, targetY);
        
        if (isCheck(currentTurn)) {
            undoLastMove();
            return false;
        }
        
        if (isCheckmate(getOpponentColor(currentTurn))) {
            gameOver = true;
            winner = currentTurn;
        }
        
        currentTurn = getOpponentColor(currentTurn);
        selectedPiece = null;
        
        return true;
    }

    public boolean isValidMove(ChessPiece piece, int targetX, int targetY) {
        if (piece == null) return false;
        if (targetX < 0 || targetX >= BOARD_WIDTH || targetY < 0 || targetY >= BOARD_HEIGHT) {
            return false;
        }
        
        ChessPiece targetPiece = getPiece(targetX, targetY);
        if (targetPiece != null && targetPiece.getColor() == piece.getColor()) {
            return false;
        }
        
        int dx = Math.abs(targetX - piece.getX());
        int dy = Math.abs(targetY - piece.getY());
        
        switch (piece.getType()) {
            case KING:
                return isValidKingMove(piece, targetX, targetY);
            case GUARD:
                return isValidGuardMove(piece, targetX, targetY);
            case ELEPHANT:
                return isValidElephantMove(piece, targetX, targetY);
            case HORSE:
                return isValidHorseMove(piece, targetX, targetY);
            case CHARIOT:
                return isValidChariotMove(piece, targetX, targetY);
            case CANNON:
                return isValidCannonMove(piece, targetX, targetY);
            case PAWN:
                return isValidPawnMove(piece, targetX, targetY);
            default:
                return false;
        }
    }

    private boolean isValidKingMove(ChessPiece piece, int targetX, int targetY) {
        int dx = Math.abs(targetX - piece.getX());
        int dy = Math.abs(targetY - piece.getY());
        
        if (dx > 1 || dy > 1) return false;
        
        boolean isRed = piece.getColor() == ChessPiece.PieceColor.RED;
        int minY = isRed ? 7 : 0;
        int maxY = isRed ? 9 : 2;
        int minX = 3;
        int maxX = 5;
        
        if (targetY < minY || targetY > maxY || targetX < minX || targetX > maxX) {
            return false;
        }
        
        return true;
    }

    private boolean isValidGuardMove(ChessPiece piece, int targetX, int targetY) {
        int dx = Math.abs(targetX - piece.getX());
        int dy = Math.abs(targetY - piece.getY());
        
        if (dx != 1 || dy != 1) return false;
        
        boolean isRed = piece.getColor() == ChessPiece.PieceColor.RED;
        int minY = isRed ? 7 : 0;
        int maxY = isRed ? 9 : 2;
        int minX = 3;
        int maxX = 5;
        
        if (targetY < minY || targetY > maxY || targetX < minX || targetX > maxX) {
            return false;
        }
        
        return true;
    }

    private boolean isValidElephantMove(ChessPiece piece, int targetX, int targetY) {
        int dx = Math.abs(targetX - piece.getX());
        int dy = Math.abs(targetY - piece.getY());
        
        if (dx != 2 || dy != 2) return false;
        
        boolean isRed = piece.getColor() == ChessPiece.PieceColor.RED;
        int riverY = 4;
        
        if ((isRed && targetY <= riverY) || (!isRed && targetY >= riverY)) {
            return false;
        }
        
        int midX = (piece.getX() + targetX) / 2;
        int midY = (piece.getY() + targetY) / 2;
        
        return getPiece(midX, midY) == null;
    }

    private boolean isValidHorseMove(ChessPiece piece, int targetX, int targetY) {
        int dx = Math.abs(targetX - piece.getX());
        int dy = Math.abs(targetY - piece.getY());
        
        if (!((dx == 2 && dy == 1) || (dx == 1 && dy == 2))) {
            return false;
        }
        
        int blockX = piece.getX();
        int blockY = piece.getY();
        
        if (dx == 2) {
            blockX += (targetX - piece.getX()) / 2;
        } else {
            blockY += (targetY - piece.getY()) / 2;
        }
        
        return getPiece(blockX, blockY) == null;
    }

    private boolean isValidChariotMove(ChessPiece piece, int targetX, int targetY) {
        int dx = Math.abs(targetX - piece.getX());
        int dy = Math.abs(targetY - piece.getY());
        
        if (dx != 0 && dy != 0) return false;
        
        int stepX = dx == 0 ? 0 : (targetX - piece.getX()) / dx;
        int stepY = dy == 0 ? 0 : (targetY - piece.getY()) / dy;
        
        int x = piece.getX() + stepX;
        int y = piece.getY() + stepY;
        
        while (x != targetX || y != targetY) {
            if (getPiece(x, y) != null) return false;
            x += stepX;
            y += stepY;
        }
        
        return true;
    }

    private boolean isValidCannonMove(ChessPiece piece, int targetX, int targetY) {
        int dx = Math.abs(targetX - piece.getX());
        int dy = Math.abs(targetY - piece.getY());
        
        if (dx != 0 && dy != 0) return false;
        
        int stepX = dx == 0 ? 0 : (targetX - piece.getX()) / dx;
        int stepY = dy == 0 ? 0 : (targetY - piece.getY()) / dy;
        
        int x = piece.getX() + stepX;
        int y = piece.getY() + stepY;
        int obstacles = 0;
        
        while (x != targetX || y != targetY) {
            ChessPiece obstacle = getPiece(x, y);
            if (obstacle != null) {
                obstacles++;
                if (obstacles > 1) return false;
            }
            x += stepX;
            y += stepY;
        }
        
        ChessPiece targetPiece = getPiece(targetX, targetY);
        
        if (obstacles == 0) {
            return targetPiece == null;
        } else {
            return targetPiece != null && targetPiece.getColor() != piece.getColor();
        }
    }

    private boolean isValidPawnMove(ChessPiece piece, int targetX, int targetY) {
        int dx = Math.abs(targetX - piece.getX());
        int dy = targetY - piece.getY();
        
        boolean isRed = piece.getColor() == ChessPiece.PieceColor.RED;
        int direction = isRed ? -1 : 1;
        
        boolean hasCrossedRiver = isRed ? piece.getY() <= 4 : piece.getY() >= 5;
        
        if (!hasCrossedRiver) {
            return dx == 0 && dy == direction;
        } else {
            if (dy == direction && dx == 0) return true;
            if (dy == 0 && dx == 1) return true;
            return false;
        }
    }

    public boolean isCheck(ChessPiece.PieceColor color) {
        ChessPiece king = findKing(color);
        if (king == null) return false;
        
        ChessPiece.PieceColor opponent = getOpponentColor(color);
        
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                ChessPiece piece = getPiece(x, y);
                if (piece != null && piece.getColor() == opponent) {
                    if (isValidMove(piece, king.getX(), king.getY())) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

    public boolean isCheckmate(ChessPiece.PieceColor color) {
        if (!isCheck(color)) return false;
        
        List<ChessPiece> pieces = getAllPieces(color);
        
        for (ChessPiece piece : pieces) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                for (int y = 0; y < BOARD_HEIGHT; y++) {
                    if (isValidMove(piece, x, y)) {
                        Move tempMove = simulateMove(piece, x, y);
                        if (!isCheck(color)) {
                            undoSimulatedMove(tempMove);
                            return false;
                        }
                        undoSimulatedMove(tempMove);
                    }
                }
            }
        }
        
        return true;
    }

    private ChessPiece findKing(ChessPiece.PieceColor color) {
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                ChessPiece piece = getPiece(x, y);
                if (piece != null && piece.getType() == ChessPiece.PieceType.KING && piece.getColor() == color) {
                    return piece;
                }
            }
        }
        return null;
    }

    private ChessPiece.PieceColor getOpponentColor(ChessPiece.PieceColor color) {
        return color == ChessPiece.PieceColor.RED ? ChessPiece.PieceColor.BLACK : ChessPiece.PieceColor.RED;
    }

    private List<ChessPiece> getAllPieces(ChessPiece.PieceColor color) {
        List<ChessPiece> pieces = new ArrayList<>();
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                ChessPiece piece = getPiece(x, y);
                if (piece != null && piece.getColor() == color) {
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }

    private Move simulateMove(ChessPiece piece, int targetX, int targetY) {
        int fromX = piece.getX();
        int fromY = piece.getY();
        ChessPiece targetPiece = getPiece(targetX, targetY);
        
        Move move = new Move(piece, fromX, fromY, targetX, targetY, targetPiece);
        
        board[fromX][fromY] = null;
        board[targetX][targetY] = piece;
        piece.setPosition(targetX, targetY);
        
        if (targetPiece != null) {
            targetPiece.setCaptured(true);
        }
        
        return move;
    }

    private void undoSimulatedMove(Move move) {
        ChessPiece piece = move.getPiece();
        
        board[move.getFromX()][move.getFromY()] = piece;
        board[move.getToX()][move.getToY()] = move.getCapturedPiece();
        
        piece.setPosition(move.getFromX(), move.getFromY());
        
        if (move.getCapturedPiece() != null) {
            move.getCapturedPiece().setCaptured(false);
        }
    }

    public void undoLastMove() {
        if (moveHistory.isEmpty()) return;
        
        Move move = moveHistory.remove(moveHistory.size() - 1);
        ChessPiece piece = move.getPiece();
        
        board[move.getFromX()][move.getFromY()] = piece;
        board[move.getToX()][move.getToY()] = move.getCapturedPiece();
        
        piece.setPosition(move.getFromX(), move.getFromY());
        
        if (move.getCapturedPiece() != null) {
            move.getCapturedPiece().setCaptured(false);
        }
        
        currentTurn = getOpponentColor(currentTurn);
        selectedPiece = null;
        gameOver = false;
        winner = null;
    }

    public void resetBoard() {
        board = new ChessPiece[BOARD_WIDTH][BOARD_HEIGHT];
        moveHistory.clear();
        initBoard();
        currentTurn = ChessPiece.PieceColor.RED;
        selectedPiece = null;
        gameOver = false;
        winner = null;
    }

    public List<Move> getMoveHistory() {
        return moveHistory;
    }

    public class Move {
        private ChessPiece piece;
        private int fromX, fromY, toX, toY;
        private ChessPiece capturedPiece;

        public Move(ChessPiece piece, int fromX, int fromY, int toX, int toY, ChessPiece capturedPiece) {
            this.piece = piece;
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
            this.capturedPiece = capturedPiece;
        }

        public ChessPiece getPiece() { return piece; }
        public int getFromX() { return fromX; }
        public int getFromY() { return fromY; }
        public int getToX() { return toX; }
        public int getToY() { return toY; }
        public ChessPiece getCapturedPiece() { return capturedPiece; }
    }
}
