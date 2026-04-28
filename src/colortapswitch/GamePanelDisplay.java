package colortapswitch;

import javax.swing.*;
import java.awt.*;

public class GamePanelDisplay {

    public static void startGame(JFrame frame, userInfo info) {
        frame.setContentPane(new GamePanel(info));
        frame.revalidate();
        frame.repaint();
    }
}

class GamePanel extends JPanel {

    processClass PG;
    JLabel word, score, timer;
    JLabel colorLabel, textLabel;

    String correctAnswer;
    int colorOrText;

    int scoreVal = 0;
    boolean timeUp = false;

    JButton green, red, blue, yellow;

    userInfo info;

    public GamePanel(userInfo info) {
        this.info = info;
        PG = new processClass();

        setLayout(null);
        setBackground(new Color(40, 0, 120));

        // ===== SCORE =====
        JLabel pts = new JLabel("PTS: ");
        pts.setBounds(30, 20, 150, 40);
        pts.setForeground(Color.WHITE);
        pts.setFont(new Font("Arial", Font.BOLD, 26));
        add(pts);

        score = new JLabel("0");
        score.setBounds(95, 20, 150, 40);
        score.setForeground(Color.WHITE);
        score.setFont(new Font("Arial", Font.BOLD, 26));
        add(score);

        // ===== TIMER =====
        timer = new JLabel("1:00");
        timer.setBounds(850, 20, 100, 40);
        timer.setForeground(Color.WHITE);
        timer.setFont(new Font("Arial", Font.BOLD, 26));
        add(timer);

        // ===== MODE LABELS =====
        colorLabel = new JLabel("COLOR", SwingConstants.CENTER);
        colorLabel.setBounds(400, 60, 200, 40);
        colorLabel.setOpaque(true);

        textLabel = new JLabel("TEXT", SwingConstants.CENTER);
        textLabel.setBounds(400, 100, 200, 40);
        textLabel.setOpaque(true);

        add(colorLabel);
        add(textLabel);

        // ===== WORD DISPLAY =====
        word = new JLabel("", SwingConstants.CENTER);
        word.setBounds(350, 150, 300, 70);
        word.setOpaque(true);
        word.setBackground(Color.WHITE);
        word.setFont(new Font("Arial", Font.BOLD, 36));
        add(word);

        // ===== BUTTON PANEL =====
        int size = 200;
        int gap = 20;
        int padding = 20;

        int startX = (1000 - (size * 2 + gap)) / 2;
        int startY = 300;

        JPanel bgPanel = new JPanel();
        bgPanel.setBackground(Color.GRAY);
        bgPanel.setBounds(
                startX - padding,
                startY - padding,
                size * 2 + gap + padding * 2,
                size * 2 + gap + padding * 2);
        bgPanel.setLayout(null);
        bgPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        add(bgPanel);

        green = createColorButton(Color.GREEN);
        green.setBounds(padding, padding, size, size);
        bgPanel.add(green);

        red = createColorButton(Color.RED);
        red.setBounds(padding + size + gap, padding, size, size);
        bgPanel.add(red);

        blue = createColorButton(Color.BLUE);
        blue.setBounds(padding, padding + size + gap, size, size);
        bgPanel.add(blue);

        yellow = createColorButton(Color.YELLOW);
        yellow.setBounds(padding + size + gap, padding + size + gap, size, size);
        bgPanel.add(yellow);

        // ===== BUTTON ACTIONS =====
        green.addActionListener(e -> handleClick("GREEN"));
        red.addActionListener(e -> handleClick("RED"));
        blue.addActionListener(e -> handleClick("BLUE"));
        yellow.addActionListener(e -> handleClick("YELLOW"));

        // ===== COUNTDOWN TIMER =====
        int[] secondsLeft = {60};

        javax.swing.Timer countdown = new javax.swing.Timer(1000, null);

        countdown.addActionListener(e -> {
            secondsLeft[0]--;

            int minutes = secondsLeft[0] / 60;
            int seconds = secondsLeft[0] % 60;
            timer.setText(String.format("%d:%02d", minutes, seconds));

            if (secondsLeft[0] <= 0) {
                countdown.stop();
                timeUp = true;

                timer.setText("0:00");
                timer.setForeground(Color.RED);

                green.setEnabled(false);
                red.setEnabled(false);
                blue.setEnabled(false);
                yellow.setEnabled(false);

                // ✅ Save score ONCE here only
                info.setScore(scoreVal);
                info.saveUserInfoToFile();

                JOptionPane.showMessageDialog(null,
                        "Time's up! Final Score: " + scoreVal,
                        "GAME OVER",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        countdown.start();
        nextRound();
    }

    private void nextRound() {
        if (timeUp) return;

        colorOrText = PG.CoT();

        int wordIndex = PG.getRandomColorIndex();
        int inkIndex  = PG.getRandomColorIndex();

        String wordText = PG.getTextByIndex(wordIndex);
        Color inkColor  = PG.getColorByIndex(inkIndex);

        word.setText(wordText);
        word.setForeground(inkColor);

        if (colorOrText == 1) {
            colorLabel.setBackground(Color.DARK_GRAY);
            textLabel.setBackground(Color.WHITE);
        } else {
            colorLabel.setBackground(Color.WHITE);
            textLabel.setBackground(Color.DARK_GRAY);
        }

        correctAnswer = (colorOrText == 0)
                ? PG.getTextByIndex(inkIndex)
                : wordText;
    }

    private void handleClick(String clicked) {
        if (timeUp) return;

        if (clicked.equals(correctAnswer)) {
            scoreVal++;
        }

        score.setText(String.valueOf(scoreVal));
        nextRound();
    }

    private JButton createColorButton(Color color) {
        JButton btn = new JButton();
        btn.setBackground(color);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        btn.setFocusPainted(false);
        return btn;
    }
}