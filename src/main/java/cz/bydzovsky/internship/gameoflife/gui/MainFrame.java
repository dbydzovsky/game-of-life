package cz.bydzovsky.internship.gameoflife.gui;


import cz.bydzovsky.internship.gameoflife.model.shapes.GunShape;
import cz.bydzovsky.internship.gameoflife.model.shapes.PulsarShape;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private static final String defaultFileName = "gameoflife.gol";

    private final GamePanel gamePanel = new GamePanel();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private ScheduledFuture<?> scheduled;

    public MainFrame() {
        super("Game of Life");

        setLayout(new BorderLayout());
        add(gamePanel, BorderLayout.CENTER);

        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem playItem = new MenuItem("Play / Pause (p)");
        MenuItem drawPulsar = new MenuItem("Draw pulsar");
        MenuItem drawGun = new MenuItem("Draw gun");
        MenuItem spaceItem = new MenuItem("Step (space)");
        MenuItem clearItem = new MenuItem("Clear (backspace)");
        MenuItem randomItem = new MenuItem("Random (enter)");

        Menu fileMenu = new Menu("File");
        fileMenu.add(spaceItem);
        fileMenu.add(playItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(clearItem);
        fileMenu.add(randomItem);
        fileMenu.add(drawGun);
        fileMenu.add(drawPulsar);

        MenuBar menuBar = new MenuBar();
        menuBar.add(fileMenu);

        setMenuBar(menuBar);
        drawGun.addActionListener(a -> gamePanel.draw(new GunShape()));
        drawPulsar.addActionListener(a -> gamePanel.draw(new PulsarShape()));
        spaceItem.addActionListener(a -> gamePanel.next());
        clearItem.addActionListener(a -> gamePanel.clear());
        randomItem.addActionListener(a -> gamePanel.randomize());
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Game of Life Files", "gol");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
        playItem.addActionListener(e -> {
        });
        openItem.addActionListener(e -> {
            int userOption = fileChooser.showOpenDialog(MainFrame.this);
            if (userOption == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                gamePanel.open(selectedFile);
            }
        });

        saveItem.addActionListener(e -> {
            fileChooser.setSelectedFile(new File(defaultFileName));
            int userOption = fileChooser.showSaveDialog(MainFrame.this);
            if (userOption == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                gamePanel.save(selectedFile);
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                int code = e.getKeyCode();
                switch (code) {
                    case 80:
                        playPause();
                        break;
                    case 32: // space
                        gamePanel.next();
                        break;
                    case 8: // backspace
                        gamePanel.clear();
                        break;
                    case 10: // enter
                        gamePanel.randomize();
                        break;
                }
            }
        });

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void playPause() {
        if (scheduled != null) {
            scheduled.cancel(false);
            scheduled = null;
        } else {
            scheduled = scheduler.scheduleAtFixedRate(gamePanel::next, 500, 200, TimeUnit.MILLISECONDS);
        }
    }
}
