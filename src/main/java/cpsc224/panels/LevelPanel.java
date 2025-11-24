package cpsc224.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import cpsc224.creatures.Player;
import cpsc224.levels.BossRoom;
import cpsc224.levels.Level;
import cpsc224.levels.Room;
import cpsc224.levels.ShopRoom;

public class LevelPanel extends JPanel implements GamePanel {

    Level level;
    Player player;
    Room[][] rooms;
    JButton[][] roomButtons;

    public LevelPanel(Level level, Player player) {
        this.level = level;
        this.player = player;
        rooms = level.getRooms();
        
        initComponents();
        layoutComponents();
        addListeners();

    }

    private void initComponents() {
        roomButtons = new JButton[rooms.length][rooms.length];
    }

    private void layoutComponents() {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;

        for (int i = 0; i < level.getRoomLength(); i++) {
            for (int j = 0; j < level.getRoomLength(); j++) {
                Room room = rooms[i][j];
                if (room != null) {
                    c.gridy = i;
                    c.gridx = j;
                    c.fill = GridBagConstraints.BOTH;
                    roomButtons[i][j] = new JButton(i + ", " + j);

                    if (room instanceof BossRoom)
                        roomButtons[i][j].setText("Boss");
                    if (room instanceof ShopRoom)
                        roomButtons[i][j].setText("Shop");
                    
                    add(roomButtons[i][j], c);
                }
            }
        }
    }

    private void addListeners() {
        for (int i = 0; i < roomButtons.length; i++) {
            for (int j = 0; j < roomButtons[i].length; j++) {
                JButton button = roomButtons[i][j];
                if (button == null)
                    continue;

                Room room = rooms[i][j];
                button.addActionListener(e -> {
                    if (room.hasCreature()) {
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        frame.remove(this);
                        frame.getContentPane().add(new FightPanel(player, room));
                        frame.pack();
                    }
                });
            }
        }
    }

    @Override
    public void updateDisplay() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateDisplay'");
    }
}
