package cpsc224.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.Timer;

import cpsc224.creatures.Creature;
import cpsc224.creatures.Player;
import cpsc224.effects.Effect;
import cpsc224.effects.PoisonEffect;
import cpsc224.items.weapons.Weapon;

public class CreaturePanel extends JPanel{

    private Creature creature;

    private JPanel healthPanel;
    private JProgressBar healthBar;
    private JLabel healthNumber;

    private JLabel nameLabel;
    private JTextArea infoTextArea;

    JPanel buttonPanel;
    JButton[] weaponButtons;

    public CreaturePanel(Creature creature) {
        this.creature = creature;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMaximumSize(new Dimension(200, 200));

        weaponButtons = new JButton[creature.getInventory().getMaxWeapons()];

        // Weapon buttons
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

        if (!(creature instanceof Player))
            enableWeaponButtons(false);

        healthBar = new JProgressBar(0, (int)creature.getMaxHealth());
        healthBar.setValue((int)creature.getHealth());
        healthBar.setForeground(FightPanel.HEALTH_COLOR);
        healthNumber = new JLabel(String.valueOf(creature.getHealth()));
        healthPanel = new JPanel();
        healthPanel.add(healthBar);
        healthPanel.add(healthNumber);

        nameLabel = new JLabel(creature.getName());
        infoTextArea = new JTextArea();
        infoTextArea.setEditable(false);
        add(nameLabel);
        add(healthPanel);
        add(buttonPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(infoTextArea);
    }

    public void displayEffectInfo(Collection<String> info) {
        String text = "";
        for (String result : info) {
            text += creature.getName() + " was effected by " + result + "\n";
        }
        infoTextArea.setText(text);

        updateDisplay();
    }

    public void updateDisplay() {

        healthBar.setValue((int)creature.getHealth());
        healthNumber.setText(String.valueOf(Math.round(creature.getHealth())));

        updateHealthBar();
    }

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

    public void enableWeaponButtons(boolean b) {
        for (int i = 0; i < weaponButtons.length; i++) {
            Weapon weapon = creature.getInventory().getWeapon(i);
            if (weapon != null)
                weaponButtons[i].setEnabled(b);
        }
    }

    public JPanel getHealthPanel() {
        return healthPanel;
    }

    public JProgressBar getHealthBar() {
        return healthBar;
    }

    public JLabel getHealthNumber() {
        return healthNumber;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public JTextArea getInfoTextArea() {
        return infoTextArea;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public JButton[] getWeaponButtons() {
        return weaponButtons;
    }

}
