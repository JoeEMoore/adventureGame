package cpsc224.panels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import cpsc224.Application;
import cpsc224.Game;
import cpsc224.creatures.CreatureFactory;
import cpsc224.creatures.Player;
import cpsc224.utils.BufferedImageBuilder;

public class PlayerSelectionPanel extends JPanel implements GamePanel {

    private JLabel player1Image;
    private JLabel player2Image;
    private JLabel player3Image;
    private JLabel player4Image;

    private JButton player1Button;
    private JButton player2Button;
    private JButton player3Button;
    private JButton player4Button;

    private JLabel titleLabel;

    public PlayerSelectionPanel() {

        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initComponents() {
        BufferedImageBuilder imageBuilder = new BufferedImageBuilder("/images/creatures/Player.png");
        ImageIcon icon = imageBuilder
            .scale(256, 256)
            .toImageIcon();

        player1Image = new JLabel();
        player2Image = new JLabel();
        player3Image = new JLabel();
        player4Image = new JLabel();
        player1Image.setIcon(icon);
        player2Image.setIcon(icon);
        player3Image.setIcon(icon);
        player4Image.setIcon(icon);

        player1Button = new JButton("Knight");
        player2Button = new JButton("Mage");
        player3Button = new JButton("Ranger");
        player4Button = new JButton("Barbarian");

        titleLabel = new JLabel("Choose Your Character");
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 36));

        setBackground(Application.MENU_COLOR);
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;

        c.anchor = GridBagConstraints.PAGE_START;
        c.gridy = 0;
        c.gridx = 1;
        c.gridwidth = 2;
        add(titleLabel, c);
        
        c.insets = new Insets(100, 0, 0, 0);
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(player1Image, c);

        c.gridx = 1;
        add(player2Image, c);
        
        c.gridx = 2;
        add(player3Image, c);
        
        c.gridx = 3;
        add(player4Image, c);

        c.insets = new Insets(20, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 2;
        add(player1Button, c);

        c.gridx = 1;
        add(player2Button, c);

        c.gridx = 2;
        add(player3Button, c);

        c.gridx = 3;
        add(player4Button, c);
    }

    private void addListeners() {
        player1Button.addActionListener(e -> {
            Player player = CreatureFactory.createSlashPlayer();
            Game.getInstance().setPlayer(player);
            startGame();
        });

        player2Button.addActionListener(e -> {
            Player player = CreatureFactory.createMagePlayer();
            Game.getInstance().setPlayer(player);
            startGame();
        });

        player3Button.addActionListener(e -> {
            Player player = CreatureFactory.createRangePlayer();
            Game.getInstance().setPlayer(player);
            startGame();
        });

        player4Button.addActionListener(e -> {
            Player player = CreatureFactory.createBluntPlayer();
            Game.getInstance().setPlayer(player);
            startGame();
        });
    }


    @Override
    public void updateDisplay() {

    }

    private void startGame() {
        JFrame frame = (JFrame)SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new MapPanel());
        frame.revalidate();
        frame.repaint();
    }

}
