package cpsc224.windows;

import javax.swing.*;
import java.awt.*;

public class SplashWindow extends JWindow {

    public SplashWindow() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(156, 219, 173));

        JLabel title = new JLabel("Adventure Game");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Dialog", Font.BOLD, 36));

        JButton newGameButton = new JButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //newGameButton.setSize(new Dimension(100, 20));

        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMinimumSize(newGameButton.getPreferredSize());

        newGameButton.addActionListener(e -> {
            dispose();
            new GameFrame();
        });

        exitButton.addActionListener(e -> {
            dispose();
        });

        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 75)));
        panel.add(newGameButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(exitButton);

        add(panel);
        setPreferredSize(new Dimension(500, 300));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
