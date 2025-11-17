package cpsc224;

import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;

import cpsc224.creatures.CreatureFactory;
import cpsc224.panels.FightPanel;

public class Application {

    static Random rand;

    public static void main(String[] args) {

        rand = new Random();

        JFrame frame = new JFrame();
        frame.add(new FightPanel(CreatureFactory.createPlayer(), CreatureFactory.createTroll(), rand.nextLong()));
        frame.setMinimumSize(new Dimension(600, 400));
        frame.pack();
        frame.setVisible(true);
    }
}