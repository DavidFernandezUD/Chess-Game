package code;

import java.util.ArrayList;

public class Knight extends Piece{

    // constructors
    public Knight(boolean white, int x, int y) {
        super(white, x, y);
    }

    public Knight(Piece piece) {
        super(piece);
    }

    public Knight(boolean white) {
        super(white);
    }

    public Knight() {
        super();
    }

    // methods
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        int x = this.getPosition()[0];
        int y = this.getPosition()[1];

        for (int i=x-2; i<=x+2; i++) {
            for (int j=y-2; j<=y+2; j++) {
                int[] end = {i, j};
                int deltaX = Math.abs(i - x);
                int deltaY = Math.abs(j - y);

                // checking if the end square is out of bounds
                if (max(end) < 8 && min(end) >= 0) {
                    if (deltaX + deltaY == 3 && deltaX != 0 && deltaY != 0) {
                        if (Chessboard.isOpposite(this, end) || Chessboard.isEmpty(end)) {
                            moves.add(new Move(this, end));
                        }
                    }
                }
            }
        }
        return moves;
    }

    public void move(Move move) {
        int[] end = move.getEnd();
        Chessboard.chessboard[this.getPosition()[0]][this.getPosition()[1]] = new Empty();
        Chessboard.chessboard[end[0]][end[1]] = new Knight(this);
        Chessboard.chessboard[end[0]][end[1]].setPosition(end);
        Chessboard.clearDecoys();
    }
}
