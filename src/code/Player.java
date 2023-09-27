package code;

import java.util.ArrayList;

public class Player {

    protected static ArrayList<Piece> whitePieces;
    protected static ArrayList<Piece> blackPieces;
    protected static ArrayList<Move> whiteMoves;
    protected static ArrayList<Move> blackMoves;

    // static variable for the turn (white starts by default)
    protected static boolean isWhiteTurn = true;

    // constructors
    public Player(ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces, ArrayList<Move> whiteMoves, ArrayList<Move> blackMoves) {
        whitePieces = new ArrayList<Piece>(whitePieces);
        blackPieces = new ArrayList<Piece>(blackPieces);
        whiteMoves = new ArrayList<Move>(whiteMoves);
        blackMoves = new ArrayList<Move>(blackMoves);
    }

    public Player() {
        whitePieces = new ArrayList<Piece>();
        blackPieces = new ArrayList<Piece>();
        whiteMoves = new ArrayList<Move>();
        blackMoves = new ArrayList<Move>();
    }

    // getters & setters
    public static ArrayList<Move> getWhiteMoves() {
        return whiteMoves;
    }

    public static void setWhiteMoves(ArrayList<Move> playerMoves) {
        whiteMoves = playerMoves;
    }

    public static ArrayList<Move> getBlackMoves() {
        return blackMoves;
    }

    public static void setBlackMoves(ArrayList<Move> playerMoves) {
        blackMoves = playerMoves;
    }

    public static ArrayList<Piece> getWhitePieces() {
        return whitePieces;
    }

    public static void setWhitePieces(ArrayList<Piece> pieces) {
        whitePieces = pieces;
    }

    public static ArrayList<Piece> getBlackPieces() {
        return blackPieces;
    }

    public static void setBlackPieces(ArrayList<Piece> pieces) {
        blackPieces = pieces;
    }

    public static boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public static void setWhiteTurn(boolean whiteTurn) {
        isWhiteTurn = whiteTurn;
    }

    // methods
    // set each player's pieces
    public static void setPlayersPieces() {
        ArrayList<Piece> whitePieces = new ArrayList<Piece>();
        ArrayList<Piece> blackPieces = new ArrayList<Piece>();
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                // white pieces
                if (Chessboard.isWhite(new int[] {i, j})) {
                    whitePieces.add(Chessboard.chessboard[i][j]);
                }
                // black pieces
                else if (Chessboard.isBlack(new int[] {i, j})) {
                    blackPieces.add(Chessboard.chessboard[i][j]);
                }
            }
        }
        setWhitePieces(whitePieces);
        setBlackPieces(blackPieces);
    }

    // set each players moves
    public static void setPlayersMoves() {
        // we call the setPlayersPieces method to update the player pieecs before getting moves
        setPlayersPieces();

        // white pieces
        // clearing the moves list before updating it
        whiteMoves.clear();
        for (Piece piece : whitePieces) {
            ArrayList<Move> moves = piece.getMoves();
            for (Move move : moves) {
                whiteMoves.add(move);
            }
        }

        // black pieces
        blackMoves.clear();
        for (Piece piece : blackPieces) {
            ArrayList<Move> moves = piece.getMoves();
            for (Move move : moves) {
                blackMoves.add(move);
            }
        }
    }

    // checking if the player is checked
    public static boolean isWhiteChecked() {
        for (Piece piece : whitePieces) {
            if (piece instanceof King) {
                return ((King) (piece)).isChecked();
            }
        }
        return false;
    }

    public static boolean isBlackChecked() {
        for (Piece piece : blackPieces) {
            if (piece instanceof King) {
                return ((King) (piece)).isChecked();
            }
        }
        return false;
    }

    // checking if a player is mated
    // if a player doesn't have legal moves, it's mated
    public static boolean isWhiteMated() {
        ArrayList<Move> moves = new ArrayList<Move>(Player.getWhiteMoves());
        for (Move move : moves) {
            if (move.isMoveLegal()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBlackMated() {
        ArrayList<Move> moves = new ArrayList<Move>(Player.getBlackMoves());
        for (Move move : moves) {
            if (move.isMoveLegal()) {
                return false;
            }
        }
        return true;
    }
}
