package GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import code.*;

public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener {
    
    // setected piece and moves
    protected boolean isPieceSelected = false;
    protected Piece pieceSelected;
    protected int[] positionSelected;
    protected boolean playing = true;

    // colors
    protected static final Color lightColor = new Color(0xdecba0);
    protected static final Color darkColor = new  Color(0xb89063);
    protected static final Color TRANS_GREEN = new Color(0, 255, 0, 80);
    protected static final Color TRANS_RED = new Color(255, 0, 0, 80);
    protected static final Color TRANS_YELLOW = new Color(255, 255, 0, 100);
    protected static final Color TRANS_GRAY = new Color(200, 200, 200, 60);

    // images
    protected static BufferedImage whitePawn, blackPawn, whiteKnight, blackKnight, whiteBishop, blackBishop, whiteRook, blackRook, whiteQueen, blackQueen, whiteKing, blackKing;

    static {
        try{
            whitePawn = resize(ImageIO.read(new File("src/resources/whitePawn.png")));
            blackPawn = resize(ImageIO.read(new File("src/resources/blackPawn.png")));
            whiteKnight = resize(ImageIO.read(new File("src/resources/whiteKnight.png")));
            blackKnight = resize(ImageIO.read(new File("src/resources/blackKnight.png")));
            whiteBishop = resize(ImageIO.read(new File("src/resources/whiteBishop.png")));
            blackBishop = resize(ImageIO.read(new File("src/resources/blackBishop.png")));
            whiteRook = resize(ImageIO.read(new File("src/resources/whiteRook.png")));
            blackRook = resize(ImageIO.read(new File("src/resources/blackRook.png")));
            whiteQueen = resize(ImageIO.read(new File("src/resources/whiteQueen.png")));
            blackQueen = resize(ImageIO.read(new File("src/resources/blackQueen.png")));
            whiteKing = resize(ImageIO.read(new File("src/resources/whiteKing.png")));
            blackKing = resize(ImageIO.read(new File("src/resources/blackKing.png")));
        }
        catch (IOException e) {
            System.out.println("image file not found");
        }
    }

    // layers
    protected static BufferedImage pieceLayer;
    protected static BufferedImage cursorLayer;
    protected static BufferedImage highlightLayer;
    protected static BufferedImage movesLayer;
    protected static BufferedImage boardLayer;

    // sizes
    protected static final int SQUARE_SIZE = (int) GameWindow.SIZE/8;

    public BoardPanel() {

        this.setPreferredSize(new Dimension(GameWindow.SIZE, GameWindow.SIZE));

        pieceLayer = new BufferedImage(GameWindow.SIZE, GameWindow.SIZE, BufferedImage.TYPE_4BYTE_ABGR);
        cursorLayer = new BufferedImage(GameWindow.SIZE, GameWindow.SIZE, BufferedImage.TYPE_4BYTE_ABGR);
        highlightLayer = new BufferedImage(GameWindow.SIZE, GameWindow.SIZE, BufferedImage.TYPE_4BYTE_ABGR);
        movesLayer = new BufferedImage(GameWindow.SIZE, GameWindow.SIZE, BufferedImage.TYPE_4BYTE_ABGR);
        boardLayer = new BufferedImage(GameWindow.SIZE, GameWindow.SIZE, BufferedImage.TYPE_4BYTE_ABGR);

        Graphics2D g = boardLayer.createGraphics();
        Rectangle square;

        // drawing board
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                square = new Rectangle(i*SQUARE_SIZE, j*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                // checking if it is a light square or a dark square
                if (((i + j) % 2) == 0) {
                    g.setColor(lightColor);
                }
                else {
                    g.setColor(darkColor);
                }
                g.fill(square);
            }
        }
        
        // adding eventListeners
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(boardLayer, null, this);
        g2d.drawImage(movesLayer, null, this);
        g2d.drawImage(highlightLayer, null, this);
        g2d.drawImage(cursorLayer, null, this);
        g2d.drawImage(pieceLayer, null, this);
    }

    public static void updateBoardPanel() {
        clearBufferedImage(pieceLayer);
        Rectangle square;
        BufferedImage pieceImage;

        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                square = new Rectangle(i*SQUARE_SIZE, j*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                pieceImage = getPieceImage(Chessboard.chessboard[j][i]);
                if (pieceImage != null) {
                    drawImageInLayer(pieceLayer, pieceImage, square);
                }
            }
        }
    }

    public static void drawImageInLayer(BufferedImage layer, BufferedImage image, Rectangle square) {
        Graphics2D g2 = layer.createGraphics();
        g2.drawImage(image, null, (int) square.getX(), (int) square.getY());
        g2.dispose();
    }

    public void drawColorInLayer(BufferedImage layer, Color color, Rectangle square) {
        Graphics2D g2 = layer.createGraphics();
        g2.setColor(color);
        g2.fill(square);
        g2.dispose();
        repaint();
    }

    // clears a layer
    private static void clearBufferedImage(BufferedImage layer) {
        Graphics2D g2 = layer.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setColor(new Color(0, 0, 0, 0));
        g2.fillRect(0, 0, GameWindow.SIZE, GameWindow.SIZE);
        g2.dispose();
    }

    public void showMoves(Piece piece) {
        ArrayList<Move> moves = piece.getMoves();
        
        for (Move move : moves) {
            int x = move.getEnd()[1];
            int y = move.getEnd()[0];
    
            Rectangle square = new Rectangle(x * BoardPanel.SQUARE_SIZE, y* BoardPanel.SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

            // if the move is legal it draws the square in green, if not in red
            if (move.isMoveLegal()) {
                drawColorInLayer(movesLayer, TRANS_GREEN, square);
            }
            else {
                drawColorInLayer(movesLayer, TRANS_RED, square);
            }
        }
    }
    
    // resize() resizes the image to the correct size of 100x100
    public static BufferedImage resize(BufferedImage image) { 
        Image tmp = image.getScaledInstance(BoardPanel.SQUARE_SIZE, BoardPanel.SQUARE_SIZE, Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(BoardPanel.SQUARE_SIZE, BoardPanel.SQUARE_SIZE, BufferedImage.TYPE_INT_ARGB);
    
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
    
        return newImage;
    }

    // rounds the cordinates to the corners of each square
    public int roundCordinate(int cordinate) {
        int newCordinate;
        newCordinate = ((int) (cordinate / BoardPanel.SQUARE_SIZE)) * BoardPanel.SQUARE_SIZE;
        return newCordinate;
    }

    // translates mouse cordinates into the chessBoard array inedxes
    public int translateCordinate(int cordinate) {
        int newCordinate = roundCordinate(cordinate) / BoardPanel.SQUARE_SIZE;
        return newCordinate;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        clearBufferedImage(cursorLayer);
        Rectangle square = new Rectangle(roundCordinate(e.getX()), roundCordinate(e.getY()), SQUARE_SIZE, SQUARE_SIZE);
        drawColorInLayer(cursorLayer, TRANS_GRAY, square);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        // clearing prevoius layers
        clearBufferedImage(highlightLayer);
        clearBufferedImage(movesLayer);

        // the squere is highlighted in yellow when pressed
        Rectangle square = new Rectangle(roundCordinate(e.getX()), roundCordinate(e.getY()), SQUARE_SIZE, SQUARE_SIZE);
        drawColorInLayer(highlightLayer, TRANS_YELLOW, square);

        int[] boardCordinates = {translateCordinate(e.getY()), translateCordinate(e.getX())};
        
        if (isPieceSelected && this.playing) {
            ArrayList<Move> moves = pieceSelected.getMoves();
            Move move = new Move(pieceSelected, boardCordinates);

            // checking if that move is inside the move list and if it is a legal move
            if (move.isMoveIn(moves) && move.isMoveLegal()) {
                if (move.getPiece() instanceof Pawn) {
                    // checking for pawn promotion
                    if (((Pawn) move.getPiece()).isPromoting(move)) {
                        // white pawns
                        if (move.getPiece().isWhite()) {
                            new PromotionWindow(PromotionWindow.PromotionColor.WHITE, move);
                        }
                        else {
                            new PromotionWindow(PromotionWindow.PromotionColor.BLACK, move);
                        }
                    }
                    // normal pawn moves
                    else {
                        Chessboard.chessboard[positionSelected[0]][positionSelected[1]].move(move);

                        updateBoardPanel();
                        Player.setWhiteTurn(!Player.isWhiteTurn());
                        Player.setPlayersMoves();

                        // en passant implementation (settin decoys if double move)
                        if (Pawn.isDoubleMove(move)) {
                            if (move.getPiece().isWhite()) {
                                Chessboard.chessboard[move.getEnd()[0] + 1][move.getEnd()[1]] = new Decoy(move.getEnd()[0] + 1, move.getEnd()[1]);
                            }
                            else {
                                Chessboard.chessboard[move.getEnd()[0] - 1][move.getEnd()[1]] = new Decoy(move.getEnd()[0] - 1, move.getEnd()[1]);
                            }
                        }
                    }
                    Chessboard.chessboard[move.getEnd()[0]][move.getEnd()[1]].setHasMoved(true);
                }
                else {
                    // moving the normal pieces
                    Chessboard.chessboard[positionSelected[0]][positionSelected[1]].move(move);


                    // when a piece moves it changes the hasMoved variable to true
                    // I did it here instead of in the piece move methods because it created some bugs when moving them in the temp board
                    Chessboard.chessboard[move.getEnd()[0]][move.getEnd()[1]].setHasMoved(true);
                
                    updateBoardPanel();

                    // we change the players turn after they move
                    Player.setWhiteTurn(!Player.isWhiteTurn());

                    // updating each players available moves and pieces
                    Player.setPlayersMoves();
                }

                // checking if move is checkmate or stalemate
                if (Player.isWhiteMated() || Player.isBlackMated()) {
                    if (Player.isWhiteChecked()) {
                        new ResultWindow(ResultWindow.Result.BLACK);
                        this.playing = false;
                    }
                    else if (Player.isBlackChecked()) {
                        new ResultWindow(ResultWindow.Result.WHITE);
                        this.playing = false;
                    }
                    else {
                        new ResultWindow(ResultWindow.Result.STALEMATE);
                        this.playing = false;
                    }
                }
                else if (Chessboard.checkRepetition()) {
                    new ResultWindow(ResultWindow.Result.REPETITION);
                    this.playing = false;
                }

                // seting game started to true
                Chessboard.gameStarted = true;

                // adding position to gamePositions
                Chessboard.gamePositions.add(Chessboard.toFENString());
            }
            else {
                isPieceSelected = false;
                pieceSelected = Chessboard.chessboard[boardCordinates[0]][boardCordinates[1]];
                positionSelected = new int[] {boardCordinates[0], boardCordinates[1]};
            }
        }

        // checking if a piece of the players color has been selected
        if (((Player.isWhiteTurn() && Chessboard.isWhite(boardCordinates)) || (!Player.isWhiteTurn() && Chessboard.isBlack(boardCordinates))) && this.playing) {
        
            // the moves are highlighted in green when clicking a piece
            showMoves(Chessboard.chessboard[boardCordinates[0]][boardCordinates[1]]);
            isPieceSelected = true;
            pieceSelected = Chessboard.chessboard[boardCordinates[0]][boardCordinates[1]];
            positionSelected = new int[] {boardCordinates[0], boardCordinates[1]};
        }
        else {
            isPieceSelected = false;
            pieceSelected = Chessboard.chessboard[boardCordinates[0]][boardCordinates[1]];
            positionSelected = new int[] {boardCordinates[0], boardCordinates[1]};
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // selecting the right image for each piece
    public static BufferedImage getPieceImage(Piece piece) {
        if (piece instanceof Pawn) {
            if (piece.isWhite()) {
                return whitePawn;
            }
            else {
                return blackPawn;
            }
        }
        else if (piece instanceof Rook) {
            if (piece.isWhite()) {
                return whiteRook;
            }
            else {
                return blackRook;
            }
        }
        else if (piece instanceof Knight) {
            if (piece.isWhite()) {
                return whiteKnight;
            }
            else {
                return blackKnight;
            }
        }
        else if (piece instanceof Bishop) {
            if (piece.isWhite()) {
                return whiteBishop;
            }
            else {
                return blackBishop;
            }
        }
        else if (piece instanceof Queen) {
            if (piece.isWhite()) {
                return whiteQueen;
            }
            else {
                return blackQueen;
            }
        }
        else if (piece instanceof King) {
            if (piece.isWhite()) {
                return whiteKing;
            }
            else {
                return blackKing;
            }
        }
        else {
            return null;
        }
    }
}
