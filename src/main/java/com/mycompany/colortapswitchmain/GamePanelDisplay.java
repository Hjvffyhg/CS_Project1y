package com.mycompany.colortapswitchmain;

import javax.swing.*;
import java.awt.*;

public class GamePanelDisplay extends JPanel {
    public void StartGame(String playerName) {
        JFrame frame = new JFrame("COLOR GAME");
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // lagay yung frame
        frame.setContentPane(new GamePanel(playerName, frame)); 
        frame.setVisible(true);
        frame.repaint();
    }
}

class GamePanel extends JPanel {
    private processClass pc;
    private FileWritingClass fw = new FileWritingClass(); 
    private JFrame parentFrame; 

    private String correctAnswer;
    private int colorOrText;
    private int totalScore = 0;
    private int highValue = 0; 
    private int lives = 3; 
    private boolean timeUp = false;
    private String playerName;
    private Timer gameTimer; 

    private JLabel word, score, timer, highScoreLabel, highPointsLabel;
    private JLabel[] lifeLabels = new JLabel[3]; 
    private JLabel colorLabel, textLabel;

    public GamePanel(String playerName, JFrame parentFrame) {
        this.playerName = playerName;
        this.parentFrame = parentFrame; // Initialize frame
        this.pc = new processClass();
        this.highValue = fw.getHighScore(); 
        setLayout(null);

        // 1. LOAD IMAGES
        ImageIcon gbmrez = getScaledIcon("/images/gamebg.png", 1000, 1000);
        ImageIcon ptsrez = getScaledIcon("/images/ptsbg.png", 200, 150);
        ImageIcon btmrez = getScaledIcon("/images/bottomarea.png", 1000, 850);
        ImageIcon taskrez = getScaledIcon("/images/taskbg.png", 400, 350);
        ImageIcon heartrez = getScaledIcon("/images/heart.png", 40, 40);

        // 2. MAIN BACKGROUND CONTAINER
        JLabel homegbg = new JLabel(gbmrez);
        homegbg.setBounds(0, 0, 1000, 1000);
        homegbg.setLayout(null);
        add(homegbg);

        // 3. SCORE & HIGH SCORE LABELS
        JLabel pts = new JLabel("PTS:");
        pts.setBounds(75, 55, 100, 35);
        pts.setForeground(Color.WHITE);
        pts.setFont(new Font("Arial", Font.BOLD, 26));
        homegbg.add(pts);

        score = new JLabel("0");
        score.setBounds(145, 55, 100, 35);
        score.setForeground(Color.WHITE);
        score.setFont(new Font("Arial", Font.BOLD, 26));
        homegbg.add(score);

        highScoreLabel = new JLabel("HIGH SCORE", SwingConstants.CENTER);
        highScoreLabel.setBounds(20, 95, 200, 30); 
        highScoreLabel.setForeground(new Color(255, 220, 0)); 
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 22));
        homegbg.add(highScoreLabel);

        highPointsLabel = new JLabel(String.valueOf(highValue), SwingConstants.CENTER);
        highPointsLabel.setBounds(20, 125, 200, 35); 
        highPointsLabel.setForeground(new Color(255, 220, 0)); 
        highPointsLabel.setFont(new Font("Arial", Font.BOLD, 26));
        homegbg.add(highPointsLabel);

        // 4. TIMER LABELS
        JLabel timeHeader = new JLabel("TIMER");
        timeHeader.setBounds(830, 55, 120, 35);
        timeHeader.setForeground(Color.WHITE);
        timeHeader.setFont(new Font("Arial", Font.BOLD, 26));
        homegbg.add(timeHeader);

        timer = new JLabel("1:00");
        timer.setBounds(845, 90, 100, 35);
        timer.setForeground(Color.WHITE);
        timer.setFont(new Font("Arial", Font.BOLD, 26));
        homegbg.add(timer);

        // 5. LIVES LABELS
        for (int i = 0; i < 3; i++) {
            lifeLabels[i] = new JLabel(heartrez); 
            lifeLabels[i].setBounds(810 + (i * 45), 125, 40, 40);
            homegbg.add(lifeLabels[i]);
            homegbg.setComponentZOrder(lifeLabels[i], 0);
        }

        // 6. TASK DISPLAY
        word = new JLabel("", SwingConstants.CENTER);
        word.setBounds(350, 180, 300, 70);
        word.setOpaque(true);
        word.setBackground(Color.WHITE);
        word.setFont(new Font("Arial", Font.BOLD, 36));
        homegbg.add(word);

        colorLabel = new JLabel("COLOR", SwingConstants.CENTER);
        colorLabel.setBounds(400, 60, 200, 40);
        colorLabel.setOpaque(true);
        homegbg.add(colorLabel);

        textLabel = new JLabel("TEXT", SwingConstants.CENTER);
        textLabel.setBounds(400, 105, 200, 40);
        textLabel.setOpaque(true);
        homegbg.add(textLabel);

        // 7. DECORATIVE BACKGROUNDS
        JLabel ptsbg = new JLabel(ptsrez);
        ptsbg.setBounds(20, 35, 200, 150);
        homegbg.add(ptsbg);

        JLabel timebg = new JLabel(ptsrez);
        timebg.setBounds(770, 35, 200, 150);
        homegbg.add(timebg);

        JLabel taskbg = new JLabel(taskrez);
        taskbg.setBounds(300, -20, 400, 350);
        homegbg.add(taskbg);

        JLabel underBorder = new JLabel(btmrez);
        underBorder.setBounds(-15, 10, 1000, 850);
        homegbg.add(underBorder); 

        // 8. LAYERS
        homegbg.setComponentZOrder(score, 0);
        homegbg.setComponentZOrder(pts, 0);
        homegbg.setComponentZOrder(highScoreLabel, 0);
        homegbg.setComponentZOrder(highPointsLabel, 0);
        homegbg.setComponentZOrder(timeHeader, 0);
        homegbg.setComponentZOrder(timer, 0);
        homegbg.setComponentZOrder(word, 0);
        homegbg.setComponentZOrder(colorLabel, 0);
        homegbg.setComponentZOrder(textLabel, 0);

        setupButtons(homegbg);
        startTimer();
        nextRound();
    }

    private void handleClick(String clicked) {
        if (timeUp) return;

        if (clicked.equalsIgnoreCase(correctAnswer)) {
            totalScore++;
            score.setText(String.valueOf(totalScore));
            if (totalScore > highValue) {
                highValue = totalScore;
                highPointsLabel.setText(String.valueOf(highValue));
            }
        } else {
            lives--;
            if (lives >= 0) lifeLabels[lives].setVisible(false); 
            if (lives <= 0) {
                endGame("No more lives!");
                return;
            }
        }
        nextRound();
    }

    private void setupButtons(JLabel container) {
        int size = 220, gap = 15, padding = 20, startY = 320;
        int startX = (1000 - (size * 2 + gap)) / 2;

        JPanel bgPanel = new JPanel(null);
        bgPanel.setBackground(Color.DARK_GRAY);
        bgPanel.setBounds(startX - padding, startY - padding, size * 2 + gap + padding * 2, size * 2 + gap + padding * 2);
        bgPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        container.add(bgPanel);

        String[] colorNames = {"GREEN", "RED", "BLUE", "YELLOW"};
        Color[] colorValues = {Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW};

        for (int i = 0; i < 4; i++) {
            final String name = colorNames[i];
            JButton btn = new JButton();
            btn.setBackground(colorValues[i]);
            btn.setBounds((i % 2) * (size + gap) + padding, (i / 2) * (size + gap) + padding, size, size);
            btn.setOpaque(true);
            btn.addActionListener(e -> handleClick(name));
            bgPanel.add(btn);
        }
    }

    private void startTimer() {
        final int[] secondsLeft = {60};
        gameTimer = new Timer(1000, e -> {
            secondsLeft[0]--;
            timer.setText(String.format("%d:%02d", secondsLeft[0] / 60, secondsLeft[0] % 60));
            if (secondsLeft[0] <= 0) {
                endGame("Time Up!");
            }
        });
        gameTimer.start();
    }

    private void endGame(String message) {
        if (timeUp) return;
        timeUp = true;
        if (gameTimer != null) gameTimer.stop();
        
        fw.saveUserScore(new PlayerInfo(playerName, totalScore));
        JOptionPane.showMessageDialog(this, message + " Final Score: " + totalScore);

        // --- NAVIGATION BACK TO HOME ---
        parentFrame.setContentPane(new Home(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void nextRound() {
        if (timeUp) return;
        colorOrText = pc.CoT();
        int wordIdx = pc.getRandomColorIndex();
        int inkIdx = pc.getRandomColorIndex();
        word.setText(pc.getTextByIndex(wordIdx));
        word.setForeground(pc.getColorByIndex(inkIdx));
        boolean isText = (colorOrText == 1);
        textLabel.setBackground(isText ? Color.YELLOW : Color.WHITE);
        colorLabel.setBackground(isText ? Color.WHITE : Color.YELLOW);
        correctAnswer = pc.getTextByIndex(isText ? wordIdx : inkIdx);
    }

    private ImageIcon getScaledIcon(String path, int w, int h) {
        try {
            Image img = new ImageIcon(getClass().getResource(path)).getImage();
            return new ImageIcon(img.getScaledInstance(w, h, Image.SCALE_SMOOTH));
        } catch (Exception e) { return null; }
    }
}