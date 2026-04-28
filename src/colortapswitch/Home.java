package colortapswitch;

/**
 *
 * @author tino
 */
import javax.swing.*;
import java.awt.*;

public class Home extends JPanel {

    private JFrame frame;
    public String username;

    public Home(JFrame frame) {

        setLayout(null);
        setBackground(new Color(40, 0, 120));

        JLabel C = new JLabel("COLOR");
        C.setBounds(125, 100, 500, 300);
        C.setForeground(Color.BLACK);
        C.setFont(new Font("Arial", Font.BOLD, 130));

        JLabel S = new JLabel("TAP");
        S.setBounds(620, 100, 500, 300);
        S.setForeground(Color.BLACK);
        S.setFont(new Font("Arial", Font.BOLD, 130));

        JLabel T = new JLabel("SWITCH");
        T.setBounds(255, 240, 700, 300);
        T.setForeground(Color.BLACK);
        T.setFont(new Font("Arial", Font.BOLD, 130));

        add(T);
        add(C);
        add(S);

        JTextField name = new JTextField();
        name.setBounds(400, 600, 200, 60);
        add(name);

        JButton startBtn = new JButton("START GAME");
        startBtn.setBounds(400, 700, 200, 60);
        add(startBtn);

        startBtn.addActionListener(e -> {
            username = name.getText();

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter your name first!");
                return;
            }

            userInfo info = new userInfo();
            info.setUsername(username);

            GamePanelDisplay.startGame(frame, info);
        });
    }
}
