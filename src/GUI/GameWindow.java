package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;
import code.Chessboard;
import code.Player;

public class GameWindow extends JFrame implements ActionListener {

    // the game window components rescale with this size value so you can change the size of the window without altering the proportions
    public static final int SIZE = 800;

    protected JMenuItem save;
    protected JMenuItem load;
    protected JMenuItem newGame;
    protected JMenuItem classic;
    protected JMenuItem rapid;
    protected JMenuItem blitz;

    public static int hours;
    public static int minutes;
    public static int seconds;

    public GameWindow(int hours, int minutes, int seconds) {

        GameWindow.hours = hours;
        GameWindow.minutes = minutes;
        GameWindow.seconds = seconds;

        this.setTitle("Chess Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(SIZE, SIZE + 200);
        this.setIconImage(resizImageIcon((new ImageIcon("resources\\Chess_Icon.png")), 40).getImage());
        this.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0x474747));
        menuBar.setBorderPainted(false);
        
        JMenu file = new JMenu("File");
        file.setForeground(new Color(0xFFFFFF));

        save = new JMenuItem("save");
        load = new JMenuItem("load");

        save.addActionListener(this);
        load.addActionListener(this);

        JMenu restart = new JMenu("Restart");
        restart.setForeground(new Color(0xFFFFFF));

        newGame = new JMenuItem("new game");

        newGame.addActionListener(this);

        JMenu time = new JMenu("Time");
        time.setForeground(new Color(0xFFFFFF));

        classic = new JMenuItem("classic");
        rapid = new JMenuItem("rapid");
        blitz = new JMenuItem("blitz");

        classic.addActionListener(this);
        rapid.addActionListener(this);
        blitz.addActionListener(this);

        // keyboard shortcuts
        file.setMnemonic(KeyEvent.VK_F); // Alt + f to save
        restart.setMnemonic(KeyEvent.VK_R); // Alt + r to save
        save.setMnemonic(KeyEvent.VK_S); // s to save
        load.setMnemonic(KeyEvent.VK_L); // l to load
        newGame.setMnemonic(KeyEvent.VK_N); // n to start new game
        time.setMnemonic(KeyEvent.VK_T); // Alt + t for time
        classic.setMnemonic(KeyEvent.VK_C); // c for classic
        rapid.setMnemonic(KeyEvent.VK_R); // r for rapid
        blitz.setMnemonic(KeyEvent.VK_B); // b for blitz

        file.add(save);
        file.add(load);

        restart.add(newGame);

        time.add(classic);
        time.add(rapid);
        time.add(blitz);

        menuBar.add(file);
        menuBar.add(restart);
        menuBar.add(time);

        this.setJMenuBar(menuBar);

        // setting the board
        BoardPanel board = new BoardPanel();
        BoardPanel.updateBoardPanel();
        
        this.add(board);

        this.pack();
        this.setVisible(true);

        new Clock(hours, minutes, seconds);
    }

    // resizing the icon so it looks better when rescaled
    public static ImageIcon resizImageIcon(ImageIcon imageIcon, int size) {
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(size, size,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("C:\\"));

            int saved = fileChooser.showSaveDialog(null);

            if (saved == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                try {
                FileWriter writer = new FileWriter(file);
                writer.write(Chessboard.toFENString());
                writer.close();
                }
                catch (IOException e2) {
                }  
            }
        }
        else if (e.getSource() == load) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("C:\\"));

            int selected = fileChooser.showOpenDialog(null);

            if (selected == JFileChooser.APPROVE_OPTION) {
                
                
                try {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                    Scanner myReader = new Scanner(file);

                    String FENstring = myReader.nextLine();
                    Chessboard.loadPosition(FENstring);
                    
                    // Updating everything
                    Player.setPlayersPieces();
                    Player.setPlayersMoves();
                    BoardPanel.updateBoardPanel();

                    myReader.close();
                }
                catch (FileNotFoundException e2) {
                    System.out.println("An error occurred.");
                    e2.printStackTrace();
                }
            }
        }
        else if (e.getSource() == newGame) {

            Chessboard.reset();

            Chessboard.gameStarted = false;

            Player.setPlayersPieces();
            Player.setPlayersMoves();
            Player.setWhiteTurn(true);

            Clock.hoursWhite = hours;
            Clock.hoursBlack = hours;
            Clock.minutesWhite = minutes;
            Clock.minutesBlack = minutes;
            Clock.secondsWhite = seconds;
            Clock.secondsBlack = seconds;

            Clock.resetClock();

            BoardPanel.updateBoardPanel();
        }
        else if (e.getSource() == classic) {

            Chessboard.reset();

            Chessboard.gameStarted = false;

            Player.setPlayersPieces();
            Player.setPlayersMoves();
            Player.setWhiteTurn(true);

            hours = 1;
            minutes = 0;
            seconds = 0;

            Clock.hoursWhite = hours;
            Clock.hoursBlack = hours;
            Clock.minutesWhite = minutes;
            Clock.minutesBlack = minutes;
            Clock.secondsWhite = seconds;
            Clock.secondsBlack = seconds;

            Clock.resetClock();

            BoardPanel.updateBoardPanel();
        }
        else if (e.getSource() == rapid) {

            Chessboard.reset();

            Chessboard.gameStarted = false;

            Player.setPlayersPieces();
            Player.setPlayersMoves();
            Player.setWhiteTurn(true);

            hours = 0;
            minutes = 10;
            seconds = 0;

            Clock.hoursWhite = hours;
            Clock.hoursBlack = hours;
            Clock.minutesWhite = minutes;
            Clock.minutesBlack = minutes;
            Clock.secondsWhite = seconds;
            Clock.secondsBlack = seconds;

            Clock.resetClock();

            BoardPanel.updateBoardPanel();
        }
        else if (e.getSource() == blitz) {

            Chessboard.reset();

            Chessboard.gameStarted = false;

            Player.setPlayersPieces();
            Player.setPlayersMoves();
            Player.setWhiteTurn(true);

            hours = 0;
            minutes = 5;
            seconds = 0;

            Clock.hoursWhite = hours;
            Clock.hoursBlack = hours;
            Clock.minutesWhite = minutes;
            Clock.minutesBlack = minutes;
            Clock.secondsWhite = seconds;
            Clock.secondsBlack = seconds;

            Clock.resetClock();

            BoardPanel.updateBoardPanel();
        }
    }
    
}
