package code;

import java.util.ArrayList;
import java.util.Arrays;

public class Move {
    protected Piece piece;
    protected int[] end;

    // constructors
    public Move(Piece piece, int[] end) {
        this.piece = piece;
        this.end = end;
    }

    public Move() {
    }

    // getters and setters
    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int[] getEnd() {
        return end;
    }

    public void setEnd(int[] end) {
        this.end = end;
    }

    // methods
    // checking if a move is legal
    public boolean isMoveLegal() {

        // setting the current position on the tempt chessboard
        Chessboard.copyChessboardToTemp();

        // white player
        if (Player.isWhiteTurn) {
            // doing the move
            Chessboard.chessboard[this.getPiece().getPosition()[0]][this.getPiece().getPosition()[1]].move(this);

            // updating each players available moves
            Player.setPlayersMoves();

            // checking if the player that did the move is checked (in that case it's not legal and the position resets)
            if (Player.isWhiteChecked()) {
                Chessboard.copyTempToChessboard();
                Player.setPlayersMoves();
                return false;
            }
            Chessboard.copyTempToChessboard();
            Player.setPlayersMoves();
            return true;
        }
        // black player
        else {
            Chessboard.chessboard[this.getPiece().getPosition()[0]][this.getPiece().getPosition()[1]].move(this);
            Player.setPlayersMoves();

            if (Player.isBlackChecked()) {
                Chessboard.copyTempToChessboard();
                Player.setPlayersMoves();
                return false;
            }
            Chessboard.copyTempToChessboard();
            Player.setPlayersMoves();
            return true;
        }
    }

    // checking if a move is in a list of moves
    public boolean isMoveIn(ArrayList<Move> moves) {
        for (Move move : moves) {
            int[] endSquare = move.getEnd();
            if (Arrays.equals(this.end, endSquare)) {
                return true;
            }
        }
        return false;
    }
}
