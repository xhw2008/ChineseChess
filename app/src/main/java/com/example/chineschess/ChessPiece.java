package com.example.chineschess;

public class ChessPiece {
    public enum PieceType {
        KING, GUARD, ELEPHANT, HORSE, CHARIOT, CANNON, PAWN
    }

    public enum PieceColor {
        RED, BLACK
    }

    private PieceType type;
    private PieceColor color;
    private int x;
    private int y;
    private boolean captured;

    public ChessPiece(PieceType type, PieceColor color, int x, int y) {
        this.type = type;
        this.color = color;
        this.x = x;
        this.y = y;
        this.captured = false;
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isCaptured() {
        return captured;
    }

    public void setCaptured(boolean captured) {
        this.captured = captured;
    }

    public String getSymbol() {
        switch (type) {
            case KING: return color == PieceColor.RED ? "帅" : "将";
            case GUARD: return color == PieceColor.RED ? "仕" : "士";
            case ELEPHANT: return color == PieceColor.RED ? "相" : "象";
            case HORSE: return "马";
            case CHARIOT: return "车";
            case CANNON: return "炮";
            case PAWN: return color == PieceColor.RED ? "兵" : "卒";
            default: return "";
        }
    }

    public String getTypeName() {
        switch (type) {
            case KING: return "帅/将";
            case GUARD: return "仕/士";
            case ELEPHANT: return "相/象";
            case HORSE: return "马";
            case CHARIOT: return "车";
            case CANNON: return "炮";
            case PAWN: return "兵/卒";
            default: return "";
        }
    }
}
