package cpsc224.panels;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import cpsc224.creatures.Creature;
import cpsc224.creatures.Player;
import cpsc224.effects.Effect;
import cpsc224.effects.PoisonEffect;
import cpsc224.items.weapons.Weapon;
import cpsc224.utils.ImageUtils;

/**
 * A panel to visualize the state of a creature in a fight.
 */
public class CreaturePanel extends JPanel implements GamePanel{

    private Creature creature;

    private JLabel imageLabel;

    private JPanel healthPanel;
    private JProgressBar healthBar;
    private JLabel healthNumber;

    private JLabel nameLabel;

    JPanel buttonPanel;
    JButton[] weaponButtons;

    /**
     * Creates a panel for the specified creature.
     * @param creature the creature
     */
    public CreaturePanel(Creature creature) {
        this.creature = creature;

        initComponents();
        layoutComponents();
        addListeners();
    }

    /**
     * Initializes the panel's components
     */
    private void initComponents() {
        // creature name
        nameLabel = new JLabel(creature.getName());
        nameLabel.setFont(new Font("Dialog", Font.BOLD, 14));

        // make panel transparent
        setOpaque(false);

        // creature image
        imageLabel = new JLabel();
        boolean flipHorizontally = !(creature instanceof Player);
        try {
            imageLabel.setIcon(ImageUtils.getImageIcon("images/creatures/" + creature.getName() + ".png", 256, 256, flipHorizontally));
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to load image from creature " + creature.getName());
        }
        
        // health panel
        healthBar = new JProgressBar(0, (int)creature.getMaxHealth());
        healthBar.setValue((int)creature.getHealth());
        healthBar.setForeground(FightPanel.HEALTH_COLOR);
        healthNumber = new JLabel(String.valueOf(creature.getHealth()));
        healthPanel = new JPanel();
        healthPanel.setOpaque(false);

        // Weapon buttons
        weaponButtons = new JButton[creature.getInventory().getMaxWeapons()];
        buttonPanel = new JPanel(new GridLayout(2, 3));
        for (int i = 0; i < weaponButtons.length; i++) {
            Weapon weapon = creature.getInventory().getWeapon(i);
            weaponButtons[i] = new JButton();
            //weaponButtons[i].setPreferredSize(new Dimension(150, 40));
            if (weapon != null) {
                weaponButtons[i].setText(weapon.getName());
                weaponButtons[i].setIcon((ImageUtils.getImageIcon("images/creatures/" + creature.getName() + ".png", 32, 32, flipHorizontally)));
            } else {
                weaponButtons[i].setText("None");
                weaponButtons[i].setEnabled(false);
            }
            buttonPanel.add(weaponButtons[i]);
        }

        // enable buttons if this is a player
        enableWeaponButtons(creature instanceof Player);
    }

    /**
     * Lays out the panel's components
     */
    private void layoutComponents() {
        // health panel
        healthPanel.add(healthBar);
        healthPanel.add(healthNumber);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(0, 0, 20, 0);
        c.gridx = 0;
        c.gridy = 0;
        add(imageLabel, c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridy = 1;
        add(nameLabel, c);

        c.gridy = 2;
        add(healthPanel, c);

        c.gridy = 3;
        c.ipadx = 30;
        add(buttonPanel, c);
    }

    /**
     * Add listeners to the dialog's components
     */
    private void addListeners() {

    }

    public void updateDisplay() {
        StringBuilder infoText = new StringBuilder();
        for (String s : creature.getInfo())
            infoText.append(s).append("\n");


        healthBar.setValue((int)creature.getHealth());
        healthNumber.setText(String.valueOf(Math.round(creature.getHealth())));

        updateHealthBar();
    }

    /**
     * Updates the health bar to account for changes in the fight.
     */
    private void updateHealthBar() {
        String toolTipText = "";
        healthBar.setForeground(FightPanel.HEALTH_COLOR);
        for (Effect e : creature.getEffects()) {
            toolTipText += e.toString() + "<br>";
            if (e instanceof PoisonEffect)
                healthBar.setForeground(FightPanel.POISON_COLOR);
        }
        healthBar.setToolTipText("<html><p width=\"100\">" + toolTipText + "</p></html>");
    }


    /**
     * Enables or disables all of the buttons with assigned weapons.
     * @param b true if the buttons should be enabled
     */
    public void enableWeaponButtons(boolean b) {
        for (int i = 0; i < weaponButtons.length; i++) {
            Weapon weapon = creature.getInventory().getWeapon(i);
            if (weapon != null)
                weaponButtons[i].setEnabled(b);
        }
    }

    /**
     * Gets the weapon buttons.
     * @return the weapon buttons as an array
     */
    public JButton[] getWeaponButtons() {
        return weaponButtons;
    }

}
