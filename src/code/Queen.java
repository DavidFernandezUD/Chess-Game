package code;

import java.util.ArrayList;

public class Queen extends Piece{

    // constructors
    public Queen(boolean white, int x, int y) {
        super(white, x, y);
    }

    public Queen(Piece piece) {
        super(piece);
    }

    public Queen(boolean white) {
        super(white);
    }

    public Queen() {
        super();
    }

    // methods
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        ArrayList<ArrayList<Move>> movesets = this.getRows();
        movesets.addAll(this.getDiagonals());

        for (ArrayList<Move> moveset : movesets) {
            for (Move move : moveset) {
                if (Chessboard.isEmpty(move.getEnd())) {
                    moves.add(move);
                }
                else if (Chessboard.isOpposite(this, move.getEnd())) {
                    moves.add(move);
                    break;
                }
                else {
                    break;
                }
            }
        }
        return moves;
    }

    public void move(Move move) {
        int[] end = move.getEnd();
        Chessboard.chessboard[this.getPosition()[0]][this.getPosition()[1]] = new Empty();
        Chessboard.chessboard[end[0]][end[1]] = new Queen(this);
        Chessboard.chessboard[end[0]][end[1]].setPosition(end);
        Chessboard.clearDecoys();
    }
}
