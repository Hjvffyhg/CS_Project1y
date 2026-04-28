package colortapswitch;

import javax.swing.*;

public class ColorTapSwitch {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("COLOR GAME");
            frame.setSize(1000, 850);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Start at home screen
            frame.setContentPane(new Home(frame));
            frame.setVisible(true);
            
        });
    }

}
