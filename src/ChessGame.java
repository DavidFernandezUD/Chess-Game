import GUI.*;
import code.*;

public class ChessGame {

    public static void main(String[] args) {
        
        new Player();

        // sets deffault chess position
        Chessboard.reset();

        // setting the players pieces and moves
        Player.setPlayersPieces();
        Player.setPlayersMoves();

        new GameWindow(0, 10, 0);
    }
}