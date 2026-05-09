package colortapswitch;

import javax.swing.*;
import java.awt.*;

public class Home extends JPanel {

    public String playerName;
   
    public Home(JFrame frame) {
   

        setLayout(null);
        setBackground(new Color(40, 0, 120));

        JLabel colorJL = new JLabel("COLOR");
        colorJL.setBounds(125, 100, 500, 300);
        colorJL.setForeground(Color.BLACK);
        colorJL.setFont(new Font("Arial", Font.BOLD, 130));

        JLabel tapJL = new JLabel("TAP");
        tapJL.setBounds(620, 100, 500, 300);
        tapJL.setForeground(Color.BLACK);
        tapJL.setFont(new Font("Arial", Font.BOLD, 130));

        JLabel switchJL = new JLabel("SWITCH");
        switchJL.setBounds(255, 240, 700, 300);
        switchJL.setForeground(Color.BLACK);
        switchJL.setFont(new Font("Arial", Font.BOLD, 130));

        add(colorJL);
        add(tapJL);
        add(switchJL);

        JTextField userNameJTF = new JTextField();
        userNameJTF.setBounds(400, 600, 200, 60);
        add(userNameJTF);

        JButton startJB = new JButton("START GAME");
        startJB.setBounds(400, 700, 200, 60);
        add(startJB);

        startJB.addActionListener(e -> {
            
            this.playerName = userNameJTF.getText();
            
            frame.setContentPane(new GamePanel(playerName));
            frame.revalidate();
            frame.repaint();
        });
    }
}
