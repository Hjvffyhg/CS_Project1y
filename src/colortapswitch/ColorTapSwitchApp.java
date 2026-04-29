package colortapswitch;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ColorTapSwitchApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame gameWindow = new JFrame("COLOR TAP SWITCH");
            gameWindow.setSize(1000, 850);
            gameWindow.setLocationRelativeTo(null);
            gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Show the name-entry screen before each new player starts a game.
            gameWindow.setContentPane(new HomeScreen(gameWindow));
            gameWindow.setVisible(true);
        });
    }
}
