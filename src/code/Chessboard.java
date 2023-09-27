package code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Chessboard {

    // create 8x8 chessboard
    public static Piece[][] chessboard;

    // temporal chessboard for checking checks and mates
    public static Piece[][] tempChessboard = new Piece[8][8];

    public static ArrayList<String> gamePositions = new ArrayList<String>();

    public static boolean gameStarted = false;

    // set initial position
    public static void reset() {
        chessboard  = new Piece[8][8];

        // setting deffault piece positions
        // black pieces
        chessboard[0][0] = new Rook(false, 0, 0);
        chessboard[0][1] = new Knight(false, 0, 1);
        chessboard[0][2] = new Bishop(false, 0, 2);
        chessboard[0][3] = new Queen(false, 0, 3);
        chessboard[0][4] = new King(false, 0, 4);
        chessboard[0][5] = new Bishop(false, 0, 5);
        chessboard[0][6] = new Knight(false, 0, 6);
        chessboard[0][7] = new Rook(false, 0, 7);

        // black pawns
        chessboard[1][0] = new Pawn(false, 1, 0);
        chessboard[1][1] = new Pawn(false, 1, 1);
        chessboard[1][2] = new Pawn(false, 1, 2);
        chessboard[1][3] = new Pawn(false, 1, 3);
        chessboard[1][4] = new Pawn(false, 1, 4);
        chessboard[1][5] = new Pawn(false, 1, 5);
        chessboard[1][6] = new Pawn(false, 1, 6);
        chessboard[1][7] = new Pawn(false, 1, 7);

        // white pieces
        chessboard[7][0] = new Rook(true, 7, 0);
        chessboard[7][1] = new Knight(true, 7, 1);
        chessboard[7][2] = new Bishop(true, 7, 2);
        chessboard[7][3] = new Queen(true, 7, 3);
        chessboard[7][4] = new King(true, 7, 4);
        chessboard[7][5] = new Bishop(true, 7, 5);
        chessboard[7][6] = new Knight(true, 7, 6);
        chessboard[7][7] = new Rook(true, 7, 7);

        // white pawns
        chessboard[6][0] = new Pawn(true, 6, 0);
        chessboard[6][1] = new Pawn(true, 6, 1);
        chessboard[6][2] = new Pawn(true, 6, 2);
        chessboard[6][3] = new Pawn(true, 6, 3);
        chessboard[6][4] = new Pawn(true, 6, 4);
        chessboard[6][5] = new Pawn(true, 6, 5);
        chessboard[6][6] = new Pawn(true, 6, 6);
        chessboard[6][7] = new Pawn(true, 6, 7);

        // empty squares
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (Chessboard.chessboard[i][j] == null) {
                    chessboard[i][j] = new Empty(i, j);
                }
            }
        }
    }

    // check if square is empty or has a decoy
    public static boolean isEmpty(int[] position) {
        if (Chessboard.chessboard[position[0]][position[1]] instanceof Empty || Chessboard.chessboard[position[0]][position[1]] instanceof Decoy) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isDecoy(int[] position) {
        if (Chessboard.chessboard[position[0]][position[1]] instanceof Decoy) {
            return true;
        }
        else {
            return false;
        }
    }

    // checking if piece at square is white
    public static boolean isWhite(int[] position) {
        if (Chessboard.chessboard[position[0]][position[1]].isWhite()) {
            return true;
        }
        else {
            return false;
        }
    }

    // checking if piece at square is black
    public static boolean isBlack(int[] position) {
        if (!Chessboard.isWhite(position) && !Chessboard.isEmpty(position)) {
            return true;
        }
        else {
            return false;
        }
    }

    // checking if they are opposite color pieces
    public static boolean isOpposite(Piece piece, int[] position) {
        if (piece.isWhite() != Chessboard.isWhite(position)) {
            return true;
        }
        else {
            return false;
        }
    }

    // checking if a square is attacked by white
    public static boolean isAttackedByWhite(int[] position) {
        // getting all the squares under white's attack
        ArrayList<Move> attackedSquares = Player.getWhiteMoves();

        for (Move move : attackedSquares) {
            if (Arrays.equals(move.getEnd(), position)) {
                return true;
            }
        }
        return false;
    }

    // checking if a square is attacked by black
    public static boolean isAttackedByBlack(int[] position) {
        // getting all the squares under black's attack
        ArrayList<Move> attackedSquares = Player.getBlackMoves();

        for (Move move : attackedSquares) {
            if (Arrays.equals(move.getEnd(), position)) {
                return true;
            }
        }
        return false;
    }

    // copying chessboard to tempChessboard
    public static void copyChessboardToTemp() {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                Chessboard.tempChessboard[i][j] = Chessboard.chessboard[i][j];
            }
        }
    }

    // copying tempChessboard to chessboard
    public static void copyTempToChessboard() {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                Chessboard.chessboard[i][j] = Chessboard.tempChessboard[i][j];
            }
        }
    }

    // clearing decoys from the chessboard
    public static void clearDecoys() {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                if (Chessboard.chessboard[i][j] instanceof Decoy) {
                    chessboard[i][j] = new Empty(i, j);
                }
            }
        }
    }

    // translating from chessboard array to FEN string
    public static String toFENString() {
        String string = "";
        for (int i=0; i<8; i++) {
            int empty = 0;
            for (int j=0; j<8; j++) {
                if (Chessboard.chessboard[i][j] instanceof Empty) {
                    empty++;
                }
                else if (empty != 0) {
                    string += empty;
                    empty = 0;
                }


                if (empty == 0) {
                    if (Chessboard.chessboard[i][j] instanceof Rook) {
                        if (Chessboard.chessboard[i][j].isWhite()) {
                            string += "R";
                        }
                        else {
                            string += "r";
                        }
                    }
                    else if (Chessboard.chessboard[i][j] instanceof Queen) {
                        if (Chessboard.chessboard[i][j].isWhite()) {
                            string += "Q";
                        }
                        else {
                            string += "q";
                        }
                    }
                    else if (Chessboard.chessboard[i][j] instanceof Bishop) {
                        if (Chessboard.chessboard[i][j].isWhite()) {
                            string += "B";
                        }
                        else {
                            string += "b";
                        }
                    }
                    else if (Chessboard.chessboard[i][j] instanceof Knight) {
                        if (Chessboard.chessboard[i][j].isWhite()) {
                            string += "N";
                        }
                        else {
                            string += "n";
                        }
                    }
                    else if (Chessboard.chessboard[i][j] instanceof King) {
                        if (Chessboard.chessboard[i][j].isWhite()) {
                            string += "K";
                        }
                        else {
                            string += "k";
                        }
                    }
                    else if (Chessboard.chessboard[i][j] instanceof Pawn) {
                        if (Chessboard.chessboard[i][j].isWhite()) {
                            string += "P";
                        }
                        else {
                            string += "p";
                        }
                    }
                    else if (Chessboard.chessboard[i][j] instanceof Decoy) {
                        string += "-";   
                    }
                }
            }
            if (empty != 0) {
                string += empty;
                empty = 0;
            }
            if (i != 7) {
                string += "/";
            }
        }
        return string;
    }

    public static void loadPosition(String fen) {
        HashMap<Character, Piece> map = new HashMap<>();
        map.put('R', new Rook(true));
        map.put('r', new Rook(false));
        map.put('Q', new Queen(true));
        map.put('q', new Queen(false));
        map.put('B', new Bishop(true));
        map.put('b', new Bishop(false));
        map.put('N', new Knight(true));
        map.put('n', new Knight(false));
        map.put('K', new King(true));
        map.put('k', new King(false));
        map.put('P', new Pawn(true));
        map.put('p', new Pawn(false));
        map.put('-', new Decoy());

        String[] string = fen.split("/");

        for (int i=0; i<string.length; i++) {
            String row = string[i];
            int empty = 0;

            for (int j=0; j<row.length(); j++) {
                Character piece = row.charAt(j);
                
                if (Character.isDigit(piece)) {
                    for (int k=0; k<Character.getNumericValue(piece); k++) {
                        Chessboard.chessboard[i][j + k + empty] = new Empty(j, j + k + empty);
                    }
                    empty += Character.getNumericValue(piece) - 1;
                }
                else {
                    Chessboard.chessboard[i][j + empty] = Piece.clone(map.get(piece));
                    Chessboard.chessboard[i][j + empty].setPosition(new int[] {i, j + empty});
                }
            }
        }
    }

    public static boolean checkRepetition() {
        String move = toFENString();
        int repetitions = 1;

        for (String position : gamePositions) {
            if (position.equals(move)) {
                repetitions++;
            }
        }

        if (repetitions >= 3) {
            return true;
        }
        else {
            return false;
        }
    }
}
