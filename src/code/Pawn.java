package code;

import java.util.ArrayList;

public class Pawn extends Piece {

    protected boolean starting;

    // promotion piece static variable
    public static enum PromotionPiece {QUEEN, ROOK, KNIGHT, BISHOP};
    public static PromotionPiece promotionPiece;

    // constructors
    public Pawn(boolean white, int x, int y) {
        super(white, x, y);
    }

    public Pawn(Piece piece) {
        super(piece);
    }

    public Pawn(boolean white) {
        super(white);
    }

    public Pawn() {
        super();
    }
    
    // methods
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();

        // white moves
        if (this.isWhite()) {
            // normal moves
            int[] end = {this.getPosition()[0] - 1, this.getPosition()[1]};
            // checking if it is inside bounds
            if (end[0] >= 0) {
                if (Chessboard.isEmpty(end)) {
                    moves.add(new Move(this, new int[] {this.getPosition()[0] - 1, this.getPosition()[1]}));
                    // 2 squares if it is starting
                    end[0] = this.getPosition()[0] - 2;
                    if (this.getPosition()[0] == 6 && Chessboard.isEmpty(end)) {
                        moves.add(new Move(this, new int[] {this.getPosition()[0] - 2, this.getPosition()[1]}));
                    }
                }
            }
            // diagonal captures
            // checking if there is a black piece or a decoy at diagonals
            end[0] = this.getPosition()[0] - 1;

            // checking if end square is out of bounds
            end[1] = this.getPosition()[1] - 1;
            if (max(end) < 8 && min(end) >= 0) {
                if (Chessboard.isBlack(end) || Chessboard.isDecoy(end)) {
                    moves.add(new Move(this, new int[] {this.getPosition()[0] - 1, this.getPosition()[1] - 1}));
                }
            }
            
            end[1] = this.getPosition()[1] + 1;
            if (max(end) < 8 && min(end) >= 0) {
                if (Chessboard.isBlack(end) || Chessboard.isDecoy(end)) {
                    moves.add(new Move(this, new int[] {this.getPosition()[0] - 1, this.getPosition()[1] + 1}));
                }
            } 
        }
        
        // black moves
        else {
            // normal moves
            int[] end = {this.getPosition()[0] + 1, this.getPosition()[1]};
            if (end[0] < 8) {
                if (Chessboard.isEmpty(end)) {
                    moves.add(new Move(this, new int[] {this.getPosition()[0] + 1, this.getPosition()[1]}));
                    // 2 squares if it is starting
                    end[0] = this.getPosition()[0] + 2;
                    if (this.getPosition()[0] == 1 && Chessboard.isEmpty(end)) {
                        moves.add(new Move(this, new int[] {this.getPosition()[0] + 2, this.getPosition()[1]}));
                    }
                }
            }
            // diagonal captures
            // checking if there is a white piece at diagonals
            end[0] = this.getPosition()[0] + 1;
            
            // checking if end square is out of bounds
            end[1] = this.getPosition()[1] - 1;
            if (max(end) < 8 && min(end) >= 0) {
                if (Chessboard.isWhite(end) || Chessboard.isDecoy(end)) {
                    moves.add(new Move(this, new int[] {this.getPosition()[0] + 1, this.getPosition()[1] - 1}));
                }
            }
            
            end[1] = this.getPosition()[1] + 1;
            if (max(end) < 8 && min(end) >= 0) {
                if (Chessboard.isWhite(end) || Chessboard.isDecoy(end)) {
                    moves.add(new Move(this, new int[] {this.getPosition()[0] + 1, this.getPosition()[1] + 1}));
                }
            }
        }
        return moves;
    }

    public boolean isPromoting(Move move) {
        if (this.isWhite()) {
            if (move.getEnd()[0] == 0) {
                return true;
            }
            return false;
        }
        else {
            if (move.getEnd()[0] == 7) {
                return true;
            }
            return false;
        }
    }

    public void move(Move move) {

        int[] end = move.getEnd();

        // en passant captures
        if (Chessboard.isDecoy(end)) {
            // white pieces
            if (move.getPiece().isWhite()) {
                Chessboard.chessboard[end[0] + 1][end[1]] = new Empty();
            }
            // black pieces
            else {
                Chessboard.chessboard[end[0] - 1][end[1]] = new Empty();
        }
        }

        Chessboard.chessboard[this.getPosition()[0]][this.getPosition()[1]] = new Empty();
        Chessboard.chessboard[end[0]][end[1]] = new Pawn(this);
        Chessboard.chessboard[end[0]][end[1]].setPosition(end);
        Chessboard.clearDecoys();
    }

    public static void promotionMove(Move move) {

        int[] end = move.getEnd();
        Chessboard.chessboard[move.getPiece().getPosition()[0]][move.getPiece().getPosition()[1]] = new Empty();
        switch (Pawn.promotionPiece) {
            case QUEEN:
                Chessboard.chessboard[end[0]][end[1]] = new Queen(move.getPiece());
                Pawn.promotionPiece = null;
                break;
            case ROOK:
                Chessboard.chessboard[end[0]][end[1]] = new Rook(move.getPiece());
                Pawn.promotionPiece = null;
                break;
            case KNIGHT:
                Chessboard.chessboard[end[0]][end[1]] = new Knight(move.getPiece());
                Pawn.promotionPiece = null;
                break;
            case BISHOP:
                Chessboard.chessboard[end[0]][end[1]] = new Bishop(move.getPiece());
                Pawn.promotionPiece = null;
                break;
        }
        Chessboard.chessboard[end[0]][end[1]].setPosition(end);
        Chessboard.clearDecoys();
    }

    // checking if it is a double move
    public static boolean isDoubleMove(Move move) {
        if (Math.abs(move.getPiece().getPosition()[0] - move.getEnd()[0]) == 2) {
            return true;
        }
        return false;
    }
}
