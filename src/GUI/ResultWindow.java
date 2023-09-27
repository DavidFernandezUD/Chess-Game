package GUI;

import java.awt.*;
import javax.swing.*;

public class ResultWindow {

    public enum Result {WHITE, BLACK, STALEMATE, WHITE_TIME, BLACK_TIME, REPETITION} 

    protected JFrame frame = new JFrame();
    protected JPanel panelTop = new JPanel();
    protected JPanel panelBottom = new JPanel();
    protected JLabel textLabel;
    protected JLabel symbolLabel;
    protected String text;
    protected String symbol;

    public ResultWindow(Result r) {

        if (r == Result.WHITE) {
            text = "WHITE WON";
            symbol = "1 - 0";
        }
        else if (r == Result.BLACK) {
            text = "BLACK WON";
            symbol = "0 - 1";
        }
        else if (r == Result.STALEMATE) {
            text = "STALEMATE";
            symbol = "1/2 - 1/2";
        }
        else if (r == Result.WHITE_TIME) {
            text = "WHITE WON";
            symbol = "1 - 0";
        }
        else if (r == Result.BLACK_TIME) {
            text = "BLACK WON";
            symbol = "0 - 1";
        }
        else if (r == Result.REPETITION) {
            text = "REPETITION";
            symbol = "1/2 - 1/2";
        }

        frame.setTitle("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setResizable(false);
        frame.setIconImage(GameWindow.resizImageIcon((new ImageIcon("resources\\Chess_Icon.png")), 40).getImage());
        frame.setLocationRelativeTo(null);

        panelTop.setBounds(0, 0, 300, 75);
        panelTop.setFont(new Font(null, Font.BOLD, 30));
        panelTop.setBackground(new Color(0x6bb570));
        panelTop.setLayout(null);

        panelBottom.setBounds(0, 75, 300, 125);
        panelBottom.setFont(new Font(null, Font.BOLD, 30));
        panelBottom.setBackground(new Color(0x474747));
        panelBottom.setLayout(null);

        textLabel = new JLabel(text, SwingConstants.CENTER);
        textLabel.setFont(new Font(null, Font.BOLD, 30));
        textLabel.setForeground(Color.WHITE);
        textLabel.setBounds(-10, 0, 300, 75);

        symbolLabel = new JLabel(symbol, SwingConstants.CENTER);
        symbolLabel.setFont(new Font(null, Font.BOLD, 30));
        symbolLabel.setForeground(new Color(0x6bb570));
        symbolLabel.setBounds(-10, 20, 300, 200);

        panelTop.add(textLabel);
        panelBottom.add(symbolLabel);
        
        frame.add(panelTop);
        frame.add(panelBottom);
        
        frame.setVisible(true);

        // stopping the clock when the game ends
        Clock.clockRunning = false;
    }
}
