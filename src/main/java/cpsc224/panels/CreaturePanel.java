package cpsc224.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
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

/**
 * A panel to visualize the state of a creature in a fight.
 */
public class CreaturePanel extends JPanel{

    private Creature creature;

    private JPanel healthPanel;
    private JProgressBar healthBar;
    private JLabel healthNumber;

    private JLabel nameLabel;
    private JTextArea infoTextArea;

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
        nameLabel = new JLabel(creature.getName());
        infoTextArea = new JTextArea();
        infoTextArea.setEditable(false);

        // health panel
        healthBar = new JProgressBar(0, (int)creature.getMaxHealth());
        healthBar.setValue((int)creature.getHealth());
        healthBar.setForeground(FightPanel.HEALTH_COLOR);
        healthNumber = new JLabel(String.valueOf(creature.getHealth()));
        healthPanel = new JPanel();

        // Weapon buttons
        weaponButtons = new JButton[creature.getInventory().getMaxWeapons()];
        buttonPanel = new JPanel(new GridLayout(2, 3));
        for (int i = 0; i < weaponButtons.length; i++) {
            Weapon weapon = creature.getInventory().getWeapon(i);
            weaponButtons[i] = new JButton();
            if (weapon != null) {
                weaponButtons[i].setText(weapon.getName());
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

        // add everything to this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMaximumSize(new Dimension(200, 200));
        add(nameLabel);
        add(healthPanel);
        add(buttonPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(infoTextArea);
    }

    /**
     * Add listeners to the dialog's components
     */
    private void addListeners() {

    }

    /**
     * Displays the effects in the creature's text area.
     * @param info the text area to display the effects
     */
    public void displayEffectInfo(Collection<String> info) {
        String text = "";
        for (String result : info) {
            text += result + "\n";
        }
        infoTextArea.setText(text);

        updateDisplay();
    }

    /**
     * Updates the panel to account for changes in the fight.
     */
    public void updateDisplay() {

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
