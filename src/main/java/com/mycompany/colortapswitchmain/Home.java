package com.mycompany.colortapswitchmain;

import javax.swing.*;
import java.awt.*;

public class Home extends JPanel {

    public String playerName;

    public Home(JFrame frame) {
        // 1. IMAGE LOADING
        ImageIcon titrez = getScaledIcon("/images/title.png", 800, 700);
        ImageIcon homebgrez = getScaledIcon("/images/homebg.png", 1000, 1000);
        ImageIcon startrez = getScaledIcon("/images/start.png", 180, 145);

        setLayout(null);

        // 2. BACKGROUND LABEL
        JLabel bgLabel = new JLabel(homebgrez);
        bgLabel.setBounds(0, 0, 1000, 1000);
        bgLabel.setLayout(null); 
        add(bgLabel);

        // 3. TITLE
        JLabel titleLabel = new JLabel(titrez);
        titleLabel.setBounds(100, 50, 800, 500);
        bgLabel.add(titleLabel);

        // 4. USERNAME INPUT FIELD
        JTextField userNameJTF = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                RadialGradientPaint paint = new RadialGradientPaint(
                    new Point(getWidth() / 2, getHeight() / 2),
                    getWidth() / 2f,
                    new float[]{0f, 1f},
                    new Color[]{Color.WHITE, new Color(255, 235, 140)}
                );

                g2.setPaint(paint);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);    
            }
        };

        userNameJTF.setBounds(400, 600, 200, 60);
        userNameJTF.setOpaque(false);
        userNameJTF.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        userNameJTF.setForeground(Color.BLACK);
        userNameJTF.setCaretColor(Color.BLACK);
        userNameJTF.setFont(new Font("Arial", Font.BOLD, 20));
        userNameJTF.setHorizontalAlignment(JTextField.CENTER);
        bgLabel.add(userNameJTF);

        // 5. START BUTTON
        JButton startJB = new JButton(startrez);
        startJB.setBounds(400, 680, 200, 60);
        startJB.setOpaque(false);
        startJB.setContentAreaFilled(false);
        startJB.setBorderPainted(false);
        startJB.setFocusPainted(false);
        bgLabel.add(startJB);

        // 6. ACTION LISTENER (The Fix)
        startJB.addActionListener(e -> {
            this.playerName = userNameJTF.getText().trim();
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a name!");
                return;
            }
            frame.setContentPane(new GamePanel(playerName, frame)); 
            frame.revalidate();
            frame.repaint();
        });
    }

    // paramaging smooth ang mga image
    private ImageIcon getScaledIcon(String path, int w, int h) {
        try {
            java.net.URL imgUrl = getClass().getResource(path);
            if (imgUrl == null) return null;
            Image img = new ImageIcon(imgUrl).getImage();
            return new ImageIcon(img.getScaledInstance(w, h, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            return null;
        }
    }
}