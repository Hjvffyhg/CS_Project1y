package colortapswitch;

import javax.swing.*;

public class ColorTapSwitch {

    public static void main(String[] args) {

        JFrame frame = new JFrame("COLOR GAME");
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Start at home screen
        frame.setContentPane(new Home(frame));
        frame.setVisible(true);

    }

}
