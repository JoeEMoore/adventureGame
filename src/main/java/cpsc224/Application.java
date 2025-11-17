package cpsc224;

import java.awt.Dimension;

import javax.swing.JFrame;

import cpsc224.creatures.CreatureFactory;
import cpsc224.panels.FightPanel;

public class Application {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new FightPanel(CreatureFactory.createPlayer(), CreatureFactory.createRat()));
        frame.setMinimumSize(new Dimension(600, 400));
        frame.pack();
        frame.setVisible(true);
    }
}