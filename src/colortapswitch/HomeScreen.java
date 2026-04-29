package colortapswitch;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HomeScreen extends JPanel {

    private final JFrame gameWindow;
    private String playerName;

    public HomeScreen(JFrame gameWindow) {
        this.gameWindow = gameWindow;

        setLayout(null);
        setBackground(new Color(40, 0, 120));

        JLabel colorTitleLabel = new JLabel("COLOR");
        colorTitleLabel.setBounds(125, 100, 500, 300);
        colorTitleLabel.setForeground(Color.BLACK);
        colorTitleLabel.setFont(new Font("Arial", Font.BOLD, 130));

        JLabel tapTitleLabel = new JLabel("TAP");
        tapTitleLabel.setBounds(620, 100, 500, 300);
        tapTitleLabel.setForeground(Color.BLACK);
        tapTitleLabel.setFont(new Font("Arial", Font.BOLD, 130));

        JLabel switchTitleLabel = new JLabel("SWITCH");
        switchTitleLabel.setBounds(255, 240, 700, 300);
        switchTitleLabel.setForeground(Color.BLACK);
        switchTitleLabel.setFont(new Font("Arial", Font.BOLD, 130));

        add(switchTitleLabel);
        add(colorTitleLabel);
        add(tapTitleLabel);

        JTextField playerNameField = new JTextField();
        playerNameField.setBounds(400, 600, 200, 60);
        add(playerNameField);

        JButton startGameButton = new JButton("START GAME");
        startGameButton.setBounds(400, 700, 200, 60);
        add(startGameButton);

        startGameButton.addActionListener(e -> {
            playerName = playerNameField.getText().trim();

            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter your name first!");
                return;
            }

            PlayerScore playerScore = new PlayerScore();
            playerScore.setPlayerName(playerName);

            GameScreenLauncher.startGame(gameWindow, playerScore);
        });
    }
}
