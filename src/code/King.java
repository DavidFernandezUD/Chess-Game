package code;

import java.util.ArrayList;

public class King extends Piece {

    protected boolean hasMoved = false;

    // constructors
    public King(boolean white, int x, int y) {
        super(white, x, y);
    }

    public King(Piece piece) {
        super(piece);
    }

    public King(boolean white) {
        super(white);
    }

    public King() {
        super();
    }

    // methods
    // castling
    public boolean canCastleLong() {
        // checking if the king hasn't moved and isn't checked
        if (!this.hasMoved() && !this.isChecked()) {
            // white pieces
            if (this.isWhite()) {
                if (Chessboard.chessboard[7][0] instanceof Rook) {
                    // checking if the rook hasn't moved
                    Rook rook = (Rook) Chessboard.chessboard[7][0];
                    if (!rook.hasMoved()) {
                        // checking if the squares are empty ADN NOT attacked by opposite team
                        for (int i=1; i<4; i++) {
                            // (+1 to the is attacked in long castle because king doesn't pass through the 2nd square)
                            if (!Chessboard.isEmpty(new int[] {7, i}) || Chessboard.isAttackedByBlack(new int[] {7, i+1})) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
            // black pieces
            else {
                if (Chessboard.chessboard[0][0] instanceof Rook) {
                    Rook rook = (Rook) Chessboard.chessboard[0][0];
                    if (!rook.hasMoved()) {
                        for (int i=1; i<4; i++) {
                            if (!Chessboard.isEmpty(new int[] {0, i}) || Chessboard.isAttackedByWhite(new int[] {0, i+1})) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean canCastleShort() {
        if (!this.hasMoved() && !this.isChecked()) {
            if (this.isWhite()) {
                if (Chessboard.chessboard[7][7] instanceof Rook) {
                    Rook rook = (Rook) Chessboard.chessboard[7][7];
                    if (!rook.hasMoved()) {
                        for (int i=5; i<7; i++) {
                            if (!Chessboard.isEmpty(new int[] {7, i}) || Chessboard.isAttackedByBlack(new int[] {7, i})) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
            else {
                if (Chessboard.chessboard[0][7] instanceof Rook) {
                    Rook rook = (Rook) Chessboard.chessboard[0][7];
                    if (!rook.hasMoved()) {
                        for (int i=5; i<7; i++) {
                            if (!Chessboard.isEmpty(new int[] {0, i}) || Chessboard.isAttackedByWhite(new int[] {0, i})) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    // CHECKS
    public boolean isChecked() {
        // white king
        if (this.isWhite()) {
            if (Chessboard.isAttackedByBlack(this.getPosition())) {
                return true;
            }
        }
        // black king
        else {
            if (Chessboard.isAttackedByWhite(this.getPosition())) {
                return true;
            }
        }
        return false;
    }

    // moves
    public ArrayList<Move> getMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        int x = this.getPosition()[0];
        int y = this.getPosition()[1];

        for (int i=x-1; i<=x+1; i++) {
            for (int j=y-1; j<=y+1; j++) {
                int[] end = {i, j};
                // checking if it is inside the bounds
                if (max(end) < 8 && min(end) >= 0) {
                    // if there is a opposite color piece at the end square or it is empty, it can move there
                    if (Chessboard.isOpposite(this, end) || Chessboard.isEmpty(end)) {
                        moves.add(new Move(this, end));
                    }
                }
            }
        }

        // adding castling moves
        if (this.canCastleLong()) {
            if(this.isWhite()) {
                moves.add(new Move(this, new int[] {7, 2}));
            }
            else {
                moves.add(new Move(this, new int[] {0, 2}));
            }
        }

        if (this.canCastleShort()) {
            if(this.isWhite()) {
                moves.add(new Move(this, new int[] {7, 6}));
            }
            else {
                moves.add(new Move(this, new int[] {0, 6}));
            }
        }
        return moves;
    }

    public void move(Move move) {
        int[] end = move.getEnd();

        // rooks also move if castling (king moves more than 1 square)
        if (Math.abs(this.getPosition()[1] - end[1]) > 1) {
            // white moves
            if (end[0] == 7) {
                // long castle
                if (end[1] == 2) {
                    Chessboard.chessboard[7][0].move(new Move(Chessboard.chessboard[7][0], new int[] {7, 3}));
                }
                //short castle
                else {
                    Chessboard.chessboard[7][7].move(new Move(Chessboard.chessboard[7][7], new int[] {7, 5}));
                }
            }
            // black moves
            else {
                if (end[1] == 2) {
                    Chessboard.chessboard[0][0].move(new Move(Chessboard.chessboard[0][0], new int[] {0, 3}));
                }
                else {
                    Chessboard.chessboard[0][7].move(new Move(Chessboard.chessboard[0][7], new int[] {0, 5}));
                }
            }
        }

        Chessboard.chessboard[this.getPosition()[0]][this.getPosition()[1]] = new Empty();
        Chessboard.chessboard[end[0]][end[1]] = new King(this);
        Chessboard.chessboard[end[0]][end[1]].setPosition(end);
        Chessboard.clearDecoys();
    }


}
