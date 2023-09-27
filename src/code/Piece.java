package code;

import java.util.ArrayList;

public abstract class Piece {

    protected boolean white;
    protected int x;
    protected int y;
    protected int[] position = {x, y};
    protected boolean hasMoved = false;

    // constructors
    public Piece(boolean white, int x, int y) {
        this.white = white;
        this.x = x;
        this.y = y;
        this.position[0] = x;
        this.position[1] = y;
    }

    public Piece(Piece piece) {
        this.white = piece.white;
        this.x = piece.x;
        this.y = piece.y;
        this.hasMoved = piece.hasMoved;
    }

    public Piece(boolean white) {
        this.white = white;
    }

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Piece() {
    }

    // getters & setters
    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.x = position[0];
        this.y = position[1];
        this.position = position;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    // methods
    public abstract ArrayList<Move> getMoves();
    
    public abstract void move(Move move);

    public int max(int[] array) {
        int max = array[0];
        for (int element : array) {
            if (element > max) {
                max = element;
            }
        }
        return max;
    }

    public int min(int[] array) {
        int min = array[0];
        for (int element : array) {
            if (element < min) {
                min = element;
            }
        }
        return min;
    }

    // this method is used in rooks and queens
    public ArrayList<ArrayList<Move>> getRows() {
        ArrayList<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();
        ArrayList<Move> moves1 = new ArrayList<Move>();
        ArrayList<Move> moves2 = new ArrayList<Move>();
        ArrayList<Move> moves3 = new ArrayList<Move>();
        ArrayList<Move> moves4 = new ArrayList<Move>();
        int x = this.getPosition()[0];
        int y = this.getPosition()[1];

        for (int i=1; i<8; i++) {
            // up
            int[] end1 = {x+i, y};
            if (max(end1) < 8 && min(end1) >= 0) {
                moves1.add(new Move(this, end1));
            }
            // right
            int[] end2 = {x, y+i};
            if (max(end2) < 8 && min(end2) >= 0) {
                moves2.add(new Move(this, end2));
            }
            // down
            int[] end3 = {x-i, y};
            if (max(end3) < 8 && min(end3) >= 0) {
                moves3.add(new Move(this, end3));
            }
            // left
            int[] end4 = {x, y-i};
            if (max(end4) < 8 && min(end4) >= 0) {
                moves4.add(new Move(this, end4));
            }
        }
        moves.add(moves1);
        moves.add(moves2);
        moves.add(moves3);
        moves.add(moves4);
        return moves;
    }

    // this method is used on bishops and queens
    public ArrayList<ArrayList<Move>> getDiagonals() {
        ArrayList<ArrayList<Move>> moves = new ArrayList<ArrayList<Move>>();
        ArrayList<Move> moves1 = new ArrayList<Move>();
        ArrayList<Move> moves2 = new ArrayList<Move>();
        ArrayList<Move> moves3 = new ArrayList<Move>();
        ArrayList<Move> moves4 = new ArrayList<Move>();
        int x = this.getPosition()[0];
        int y = this.getPosition()[1];

        for (int i=1; i<8; i++) {
            // up right
            int[] end1 = {x+i, y+i};
            if (max(end1) < 8 && min(end1) >= 0) {
                moves1.add(new Move(this, end1));
            }
            // down right
            int[] end2 = {x-i, y+i};
            if (max(end2) < 8 && min(end2) >= 0) {
                moves2.add(new Move(this, end2));
            }
            // down left
            int[] end3 = {x-i, y-i};
            if (max(end3) < 8 && min(end3) >= 0) {
                moves3.add(new Move(this, end3));
            }
            // up left
            int[] end4 = {x+i, y-i};
            if (max(end4) < 8 && min(end4) >= 0) {
                moves4.add(new Move(this, end4));
            }
        }
        moves.add(moves1);
        moves.add(moves2);
        moves.add(moves3);
        moves.add(moves4);
        return moves;
    }

    // Overriding clone method for Piece class
    public static Piece clone(Piece piece) {
        if (piece instanceof Rook) {
            return new Rook(piece);
        }
        else if (piece instanceof Queen) {
            return new Queen(piece);
        }
        else if (piece instanceof Bishop) {
            return new Bishop(piece);
        }
        else if (piece instanceof Knight) {
            return new Knight(piece);
        }
        else if (piece instanceof King) {
            return new King(piece);
        }
        else if (piece instanceof Pawn) {
            return new Pawn(piece);
        }
        else {
            return new Decoy();
        }
        
    }
}
