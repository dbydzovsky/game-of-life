package cz.bydzovsky.internship.gameoflife;

import cz.bydzovsky.internship.gameoflife.gui.MainFrame;

import javax.swing.SwingUtilities;


public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}