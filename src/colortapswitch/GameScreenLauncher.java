package colortapswitch;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GameScreenLauncher {

    public static void startGame(JFrame gameWindow, PlayerScore playerScore) {
        gameWindow.setContentPane(new ColorTapGamePanel(gameWindow, playerScore));
        gameWindow.revalidate();
        gameWindow.repaint();
    }
}

class ColorTapGamePanel extends JPanel {

    private final JFrame gameWindow;
    private final ColorRoundGenerator roundGenerator;
    private final PlayerScore playerScore;

    private JLabel challengeWordLabel;
    private JLabel scoreValueLabel;
    private JLabel timerLabel;
    private JLabel colorModeLabel;
    private JLabel textModeLabel;

    private String expectedAnswer;
    private int currentMode;
    private int currentScore = 0;
    private boolean isRoundOver = false;

    private JButton greenButton;
    private JButton redButton;
    private JButton blueButton;
    private JButton yellowButton;

    public ColorTapGamePanel(JFrame gameWindow, PlayerScore playerScore) {
        this.gameWindow = gameWindow;
        this.playerScore = playerScore;
        roundGenerator = new ColorRoundGenerator();

        setLayout(null);
        setBackground(new Color(40, 0, 120));

        JLabel scoreTitleLabel = new JLabel("PTS: ");
        scoreTitleLabel.setBounds(30, 20, 150, 40);
        scoreTitleLabel.setForeground(Color.WHITE);
        scoreTitleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        add(scoreTitleLabel);

        scoreValueLabel = new JLabel("0");
        scoreValueLabel.setBounds(95, 20, 150, 40);
        scoreValueLabel.setForeground(Color.WHITE);
        scoreValueLabel.setFont(new Font("Arial", Font.BOLD, 26));
        add(scoreValueLabel);

        timerLabel = new JLabel("1:00");
        timerLabel.setBounds(850, 20, 100, 40);
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 26));
        add(timerLabel);

        colorModeLabel = new JLabel("COLOR", SwingConstants.CENTER);
        colorModeLabel.setBounds(400, 60, 200, 40);
        colorModeLabel.setOpaque(true);

        textModeLabel = new JLabel("TEXT", SwingConstants.CENTER);
        textModeLabel.setBounds(400, 100, 200, 40);
        textModeLabel.setOpaque(true);

        add(colorModeLabel);
        add(textModeLabel);

        challengeWordLabel = new JLabel("", SwingConstants.CENTER);
        challengeWordLabel.setBounds(350, 150, 300, 70);
        challengeWordLabel.setOpaque(true);
        challengeWordLabel.setBackground(Color.WHITE);
        challengeWordLabel.setFont(new Font("Arial", Font.BOLD, 36));
        add(challengeWordLabel);

        int buttonSize = 200;
        int buttonGap = 20;
        int panelPadding = 20;

        int buttonPanelX = (1000 - (buttonSize * 2 + buttonGap)) / 2;
        int buttonPanelY = 300;

        JPanel buttonGridPanel = new JPanel();
        buttonGridPanel.setBackground(Color.GRAY);
        buttonGridPanel.setBounds(
                buttonPanelX - panelPadding,
                buttonPanelY - panelPadding,
                buttonSize * 2 + buttonGap + panelPadding * 2,
                buttonSize * 2 + buttonGap + panelPadding * 2);
        buttonGridPanel.setLayout(null);
        buttonGridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        add(buttonGridPanel);

        greenButton = createColorButton(Color.GREEN);
        greenButton.setBounds(panelPadding, panelPadding, buttonSize, buttonSize);
        buttonGridPanel.add(greenButton);

        redButton = createColorButton(Color.RED);
        redButton.setBounds(panelPadding + buttonSize + buttonGap, panelPadding, buttonSize, buttonSize);
        buttonGridPanel.add(redButton);

        blueButton = createColorButton(Color.BLUE);
        blueButton.setBounds(panelPadding, panelPadding + buttonSize + buttonGap, buttonSize, buttonSize);
        buttonGridPanel.add(blueButton);

        yellowButton = createColorButton(Color.YELLOW);
        yellowButton.setBounds(panelPadding + buttonSize + buttonGap, panelPadding + buttonSize + buttonGap,
                buttonSize, buttonSize);
        buttonGridPanel.add(yellowButton);

        greenButton.addActionListener(e -> checkPlayerAnswer("GREEN"));
        redButton.addActionListener(e -> checkPlayerAnswer("RED"));
        blueButton.addActionListener(e -> checkPlayerAnswer("BLUE"));
        yellowButton.addActionListener(e -> checkPlayerAnswer("YELLOW"));

        // This timer is the game loop: every second it updates time and stops the round at 0.
        int[] secondsLeft = {60};

        javax.swing.Timer countdownTimer = new javax.swing.Timer(1000, null);
        countdownTimer.addActionListener(e -> {
            secondsLeft[0]--;

            int minutes = secondsLeft[0] / 60;
            int seconds = secondsLeft[0] % 60;
            timerLabel.setText(String.format("%d:%02d", minutes, seconds));

            if (secondsLeft[0] <= 0) {
                countdownTimer.stop();
                finishGame();
            }
        });

        countdownTimer.start();
        showNextChallenge();
    }

    private void showNextChallenge() {
        if (isRoundOver) {
            return;
        }

        currentMode = roundGenerator.chooseColorOrTextMode();

        int wordIndex = roundGenerator.getRandomColorIndex();
        int inkColorIndex = roundGenerator.getRandomColorIndex();

        String displayedWord = roundGenerator.getColorNameByIndex(wordIndex);
        Color displayedInkColor = roundGenerator.getColorValueByIndex(inkColorIndex);

        challengeWordLabel.setText(displayedWord);
        challengeWordLabel.setForeground(displayedInkColor);

        // Dark label shows the current rule: answer by ink COLOR or by written TEXT.
        if (currentMode == ColorRoundGenerator.COLOR_MODE) {
            colorModeLabel.setBackground(Color.DARK_GRAY);
            textModeLabel.setBackground(Color.WHITE);
        } else {
            colorModeLabel.setBackground(Color.WHITE);
            textModeLabel.setBackground(Color.DARK_GRAY);
        }

        expectedAnswer = (currentMode == ColorRoundGenerator.COLOR_MODE)
                ? roundGenerator.getColorNameByIndex(inkColorIndex)
                : displayedWord;
    }

    private void checkPlayerAnswer(String selectedColorName) {
        if (isRoundOver) {
            return;
        }

        if (selectedColorName.equals(expectedAnswer)) {
            currentScore++;
        }

        scoreValueLabel.setText(String.valueOf(currentScore));
        showNextChallenge();
    }

    private void finishGame() {
        isRoundOver = true;
        timerLabel.setText("0:00");
        timerLabel.setForeground(Color.RED);

        setAnswerButtonsEnabled(false);

        // Save the completed score once, then ask whether another loop should start.
        playerScore.setScore(currentScore);
        playerScore.saveScoreToFile();

        int choice = JOptionPane.showConfirmDialog(
                gameWindow,
                "Time's up! Final Score: " + currentScore + "\nPlay again?",
                "GAME OVER",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            GameScreenLauncher.startGame(gameWindow, playerScore);
        } else {
            gameWindow.setContentPane(new HomeScreen(gameWindow));
            gameWindow.revalidate();
            gameWindow.repaint();
        }
    }

    private void setAnswerButtonsEnabled(boolean enabled) {
        greenButton.setEnabled(enabled);
        redButton.setEnabled(enabled);
        blueButton.setEnabled(enabled);
        yellowButton.setEnabled(enabled);
    }

    private JButton createColorButton(Color color) {
        JButton colorButton = new JButton();
        colorButton.setBackground(color);
        colorButton.setOpaque(true);
        colorButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        colorButton.setFocusPainted(false);
        return colorButton;
    }
}
