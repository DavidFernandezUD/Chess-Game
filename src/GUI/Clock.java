package GUI;

import javax.swing.*;
import code.*;
import java.awt.*;

public class Clock extends JFrame {

    protected static int hoursBlack, minutesBlack, secondsBlack, hoursWhite, minutesWhite, secondsWhite;

    protected JPanel panelTop;
    protected JPanel panelBottom;
    protected static JLabel timeBlack;
    protected static JLabel timeWhite;
    public static boolean clockRunning = true;

    public Clock(int hours, int minutes, int seconds) {

        hoursBlack = hours;
        hoursWhite = hours;
        minutesBlack = minutes;
        minutesWhite = minutes;
        secondsBlack = seconds;
        secondsWhite = seconds;

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setPreferredSize(new Dimension(200, 135));
        this.setResizable(false);
        this.setIconImage(GameWindow.resizImageIcon((new ImageIcon("resources\\Chess_Icon.png")), 40).getImage());
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setLocation(1500, 200);

        panelTop = new JPanel();
        panelTop.setBounds(0, 0, 200, 50);
        panelTop.setFont(new Font(null, Font.BOLD, 30));
        panelTop.setBackground(new Color(0x474747));
        panelTop.setLayout(null);

        panelBottom = new JPanel();
        panelBottom.setBounds(0, 50, 200, 50);
        panelBottom.setFont(new Font(null, Font.BOLD, 30));
        panelBottom.setBackground(new Color(0xFFFFFF));
        panelBottom.setLayout(null);

        timeBlack = new JLabel(toTwoDigits(hoursBlack) + ":" + toTwoDigits(minutesBlack) + ":" + toTwoDigits(secondsBlack), SwingConstants.CENTER);
        timeBlack.setFont(new Font(null, Font.BOLD, 30));
        timeBlack.setForeground(Color.WHITE);
        timeBlack.setBounds(-5, 0, 200, 50);

        timeWhite = new JLabel(toTwoDigits(hoursWhite) + ":" + toTwoDigits(minutesWhite) + ":" + toTwoDigits(secondsWhite), SwingConstants.CENTER);
        timeWhite.setFont(new Font(null, Font.BOLD, 30));
        timeWhite.setForeground(new Color(0x474747));
        timeWhite.setBounds(-5, 0, 200, 50);

        panelTop.add(timeBlack);
        panelBottom.add(timeWhite);
        
        this.add(panelTop);
        this.add(panelBottom);

        this.pack();
        this.setVisible(true);

        // setting the clock running
        runClock();
    }

    public static void runClock() {
        while (clockRunning) {
            timeWhite.setText(toTwoDigits(hoursWhite) + ":" + toTwoDigits(minutesWhite) + ":" + toTwoDigits(secondsWhite));
            timeBlack.setText(toTwoDigits(hoursBlack) + ":" + toTwoDigits(minutesBlack) + ":" + toTwoDigits(secondsBlack));

            try {
                Thread.sleep(1000);

                if (Player.isWhiteTurn() && Chessboard.gameStarted) {
                    if (secondsWhite != 0) {
                        secondsWhite--;
                    }
                    else if (minutesWhite != 0) {
                        minutesWhite--;
                        secondsWhite = 59;
                    }
                    else if (hoursWhite != 0) {
                        hoursWhite--;
                        minutesWhite = 59;
                    }
                    else {
                        new ResultWindow(ResultWindow.Result.BLACK_TIME);
                        break;
                    }
                }
                else if (!Player.isWhiteTurn()) {
                    if (secondsBlack != 0) {
                        secondsBlack--;
                    }
                    else if (minutesBlack != 0) {
                        minutesBlack--;
                        secondsBlack = 59;
                    }
                    else if (hoursBlack != 0) {
                        hoursBlack--;
                        minutesBlack = 59;
                    }
                    else {
                        new ResultWindow(ResultWindow.Result.WHITE_TIME);
                        break;
                    }
                }
            } catch (InterruptedException e) {
            }
        }
    }

    public static void resetClock() {
        timeWhite.setText(toTwoDigits(hoursWhite) + ":" + toTwoDigits(minutesWhite) + ":" + toTwoDigits(secondsWhite));
        timeBlack.setText(toTwoDigits(hoursBlack) + ":" + toTwoDigits(minutesBlack) + ":" + toTwoDigits(secondsBlack));  
    }

    public static String toTwoDigits(int num) {
        if (num < 10) {
            return "0" + Integer.toString(num);
        }
        return Integer.toString(num);
    }
}
