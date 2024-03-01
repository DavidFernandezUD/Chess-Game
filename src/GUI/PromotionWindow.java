package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import code.*;


public class PromotionWindow implements ActionListener {

    public enum PromotionColor {WHITE, BLACK}

    JFrame frame = new JFrame();

    protected JRadioButton queen;
    protected JRadioButton rook;
    protected JRadioButton knight;
    protected JRadioButton bishop;

    protected Move move;

    public PromotionWindow(PromotionColor color, Move move) {

        this.move = move;

        frame.setTitle("Chess Game");

        // we don't want the player to close the window until he has choosen an option
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(144, 522);
        frame.setResizable(false);
        frame.setIconImage(GameWindow.resizImageIcon((new ImageIcon("resources/Chess_Icon.png")), 40).getImage());
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(0x474747));
        frame.setLayout(new FlowLayout(0, 10, 10));

        ImageIcon queenIcon = GameWindow.resizImageIcon((new ImageIcon("resources/whiteQueen.png")), 100);
        ImageIcon rookIcon = GameWindow.resizImageIcon((new ImageIcon("resources/whiteRook.png")), 100);
        ImageIcon knightIcon = GameWindow.resizImageIcon((new ImageIcon("resources/whiteKnight.png")), 100);
        ImageIcon bishopIcon = GameWindow.resizImageIcon((new ImageIcon("resources/whiteBishop.png")), 100);

        ImageIcon bQueenIcon = GameWindow.resizImageIcon((new ImageIcon("resources/blackQueen.png")), 100);
        ImageIcon bRookIcon = GameWindow.resizImageIcon((new ImageIcon("resources/blackRook.png")), 100);
        ImageIcon bKnightIcon = GameWindow.resizImageIcon((new ImageIcon("resources/blackKnight.png")), 100);
        ImageIcon bBishopIcon = GameWindow.resizImageIcon((new ImageIcon("resources/blackBishop.png")), 100);


        queen = new JRadioButton();
        rook = new JRadioButton();
        knight = new JRadioButton();
        bishop = new JRadioButton();

        // different piece colors for each player
        if (color == PromotionColor.WHITE) {
            queen.setIcon(queenIcon);
            rook.setIcon(rookIcon);
            knight.setIcon(knightIcon);
            bishop.setIcon(bishopIcon);
        }
        else if (color == PromotionColor.BLACK) {
            queen.setIcon(bQueenIcon);
            rook.setIcon(bRookIcon);
            knight.setIcon(bKnightIcon);
            bishop.setIcon(bBishopIcon);
        }
        
        ButtonGroup group = new ButtonGroup();
        group.add(queen);
        group.add(rook);
        group.add(knight);
        group.add(bishop);

        queen.addActionListener(this);
        rook.addActionListener(this);
        knight.addActionListener(this);
        bishop.addActionListener(this);

        frame.add(queen);
        frame.add(rook);
        frame.add(knight);
        frame.add(bishop);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == queen) {
            Pawn.promotionPiece =Pawn.PromotionPiece.QUEEN;
            frame.dispose();
        }
        else if (e.getSource() == rook) {
            Pawn.promotionPiece = Pawn.PromotionPiece.ROOK;
            frame.dispose();
        }
        else if (e.getSource() == knight) {
            Pawn.promotionPiece = Pawn.PromotionPiece.KNIGHT;
            frame.dispose();
        }
        else if (e.getSource() == bishop) {
            Pawn.promotionPiece = Pawn.PromotionPiece.BISHOP;
            frame.dispose();
        }

        Pawn.promotionMove(move);

        BoardPanel.updateBoardPanel();
        Player.setWhiteTurn(!Player.isWhiteTurn());
        Player.setPlayersMoves();
        
        // checking if move is checkmate or stalemate
        if (Player.isWhiteMated() || Player.isBlackMated()) {
            if (Player.isWhiteChecked()) {
                new ResultWindow(ResultWindow.Result.BLACK);
            }
            else if (Player.isBlackChecked()) {
                new ResultWindow(ResultWindow.Result.WHITE);
            }
            else {
                new ResultWindow(ResultWindow.Result.STALEMATE);
            }
        }
    }
}
