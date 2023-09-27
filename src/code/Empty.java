package code;

import java.util.ArrayList;

public class Empty extends Piece {

    // constructors
    public Empty(int x, int y) {
        super(x, y);
    }

    public Empty() {
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
