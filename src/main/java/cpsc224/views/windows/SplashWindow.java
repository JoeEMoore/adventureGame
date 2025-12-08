package cpsc224.views.windows;

import javax.swing.*;
import java.awt.*;

public class SplashWindow extends JWindow {

    public SplashWindow() {

        
        JPanel panel = new JPanel() {
            private Image bg = new ImageIcon(getClass().getResource("/images/backgrounds/selectBackground2.png")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);  

        JLabel title = new JLabel("Adventure Game");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Dialog", Font.BOLD, 36));
        title.setForeground(Color.WHITE);

        JButton newGameButton = new JButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMinimumSize(newGameButton.getPreferredSize());

        newGameButton.addActionListener(e -> {
            dispose();
            new GameFrame();
        });

        exitButton.addActionListener(e -> dispose());

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
