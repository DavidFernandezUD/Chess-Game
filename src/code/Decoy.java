package code;

import java.util.ArrayList;

// this class is used for en passant movements
public class Decoy extends Piece{

    // constructors
    public Decoy(int x, int y) {
        super(x, y);
    }

    public Decoy(Piece piece) {
        super(piece);
    }

    public Decoy(boolean white) {
        super(white);
    }

    public Decoy() {
        super();
    }

    // methods
    public ArrayList<Move> getMoves() {
        throw new UnsupportedOperationException("Unimplemented method 'getMoves'");
    }

    public void move(Move move) {
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }
}
