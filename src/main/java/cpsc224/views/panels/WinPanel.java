package cpsc224.views.panels;

import cpsc224.utils.BufferedImageBuilder;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class WinPanel extends JPanel {

    public WinPanel() {
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
        labelPanel.setBackground(new Color(184, 105, 26));
        labelPanel.setBorder(BorderFactory.createLineBorder(new Color(130, 77, 23), 5));

        JLabel label = new JLabel("You Win!");
        label.setFont(new Font("Dialog", Font.BOLD, 140));
        label.setForeground(Color.WHITE);

        labelPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        labelPanel.add(label, BorderLayout.CENTER);
        labelPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0, 100)));
        add(labelPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = new BufferedImageBuilder("/images/backgrounds/WinBackground.png").getImage();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
