package colortapswitch;

import javax.swing.*;
import java.awt.*;

public class GamePanelDisplay extends JPanel {

    public void StartGame(String playerName) {
        JFrame frame = new JFrame("COLOR GAME");

        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new GamePanel(playerName));
        frame.setVisible(true);
    }
}

// ================= PANEL =================
class GamePanel extends JPanel {

    UserInfo userInfo;
    processClass pc;
    
    String correctAnswer;
    int colorOrText;
    int totalScore = 0;
    boolean timeUp = false;
    String playerName;

    JLabel word, score, timer;
    JButton green, red, blue, yellow;

    public GamePanel(String playerName) {
        this.playerName = playerName;
        pc = new processClass();

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
        JLabel colorLabel = new JLabel("COLOR", SwingConstants.CENTER);
        colorLabel.setBounds(400, 60, 200, 40);
        colorLabel.setOpaque(true);

        JLabel textLabel = new JLabel("TEXT", SwingConstants.CENTER);
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
                size * 2 + gap + padding * 2
        );
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

        // ===== TIMER =====
        int[] secondsLeft = {10};

        javax.swing.Timer countdown = new javax.swing.Timer(1000, null);

        countdown.addActionListener(e -> {

            secondsLeft[0]--;

            int minutes = secondsLeft[0] / 60;
            int seconds = secondsLeft[0] % 60;
            timer.setText(String.format("%d:%02d", minutes, seconds));

            if (secondsLeft[0] <= 0) {
                countdown.stop(); // NOW VALID
                timeUp = true;

                timer.setText("0:00");
                timer.setForeground(Color.RED);

                green.setEnabled(false);
                red.setEnabled(false);
                blue.setEnabled(false);
                yellow.setEnabled(false);
                
                userInfo = new UserInfo(playerName, totalScore);
                
                FileWritingClass w2f =  new FileWritingClass();
                w2f.saveUserScore(userInfo);

                JOptionPane.showMessageDialog(null,
                        "Time's up! Final Score: " + totalScore,
                        "GAME OVER",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        countdown.start();

        // ===== START FIRST ROUND =====
        nextRound(colorLabel, textLabel);
    }

    // ===== NEW ROUND =====
    private void nextRound(JLabel colorLabel, JLabel textLabel) {

        if (timeUp) {
            return;
        }
        // 1/0
        colorOrText = pc.CoT();

        int wordIndex = pc.getRandomColorIndex();
        int inkIndex = pc.getRandomColorIndex();

        String wordText = pc.getTextByIndex(wordIndex);
        Color inkColor = pc.getColorByIndex(inkIndex);

        word.setText(wordText);
        
        word.setForeground(inkColor);

        // Highlight mode
        // 1 = TEXT mode, 0 = COLOR mode
        if (colorOrText == 1) {
            colorLabel.setBackground(Color.DARK_GRAY);
            textLabel.setBackground(Color.WHITE);
        } else {
            colorLabel.setBackground(Color.WHITE);
            textLabel.setBackground(Color.DARK_GRAY);
        }

        // 0/1 
        correctAnswer = (colorOrText == 0)
                ? pc.getTextByIndex(inkIndex)
                : wordText;
    }

    // ===== HANDLE CLICK =====
    private void handleClick(String clicked) {

        if (timeUp) {
            return;
        }

        if (clicked.equals(correctAnswer)) {
            totalScore++;
        }

        score.setText(String.valueOf(totalScore));

        // NEW ROUND EVERY CLICK
        nextRound((JLabel) getComponent(3), (JLabel) getComponent(4));
    }

    private JButton createColorButton(Color color) {
        JButton colorBtn = new JButton();
        colorBtn.setBackground(color);
        colorBtn.setOpaque(true);
        colorBtn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        colorBtn.setFocusPainted(false);
        return colorBtn;
    }
}
