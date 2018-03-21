/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock;

import quantumLock.Levels.Level1.Level1;
import quantumLock.Levels.*;
import city.cs.engine.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pwajn
 */
public class QuantumLock implements ActionListener{
    public static final JFrame frame = new JFrame("Quantum Lock");;
    public static Level world;
    private static UserView view;

    static private JLabel timer;
    static private JPanel levelEndPanel = new JPanel();
    static private JLabel levelEndMessage = new JLabel("Well Done, Your Time Was:");
    static private JLabel levelEndTime = new JLabel();
    static private JTable levelEndResults;
    static private JButton nextLevelButton = new JButton("Next Level");

    private static PauseMenu pauseMenu = new PauseMenu();
    public static boolean isPaused = false;

    @Override
    public void actionPerformed(ActionEvent e) { //next level button was pressed
        world.clear();
        nextLevel();
        hidelevelEnd();
    }

    private static int currentLevel = 0; //0 = main menu

    public static List<Level> levelList = new ArrayList<>();

    private QuantumLock(){
        levelEndPanel.setSize(600,300);
        levelEndPanel.setVisible(false);
        levelEndPanel.setBackground(new Color(200,200,200));
        DefaultTableModel model = new DefaultTableModel(new Object[]{"#", "Player", "Time"},10);
        levelEndResults = new JTable(model);
        levelEndMessage.setFont(new Font("endFont", Font.PLAIN, 30));
        levelEndTime.setFont(new Font("endFont", Font.PLAIN, 30));
        nextLevelButton.addActionListener(this);

        levelEndMessage.setEnabled(false);
        levelEndMessage.setVisible(false);
        levelEndResults.setEnabled(false);
        levelEndResults.setVisible(false);

        levelList.add(new Level1());
        levelList.add(new Level2());
        levelList.add(new Level3());

        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //close when closed
        frame.setLocationByPlatform(true);
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);

        startMainMenu();
        //goToLevel(1);
    }

    public void startMainMenu() {
        frame.getContentPane().removeAll();

        MainMenu mainMenu = new MainMenu(this);
        frame.add(mainMenu.mainMenu);
        frame.setVisible(true);
    }

    public static void quitGame() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public static void newGame(){
        currentLevel = 1;
        startLevel();
    }

    public static void nextLevel() {
        currentLevel++;
        startLevel();
    }

    public static void goToLevel(int level) {
        currentLevel = level;
        startLevel();
    }

    private static void startLevel() {
        if(currentLevel >= 4) {
            quitGame(); //close game if at level 4
        }

        frame.getContentPane().removeAll();

        for(KeyListener keyListener : frame.getKeyListeners()) frame.removeKeyListener(keyListener);
        for(MouseListener mouseListener : frame.getMouseListeners()) frame.removeMouseListener(mouseListener);
        for(MouseMotionListener mouseMotionListener : frame.getMouseMotionListeners()) frame.removeMouseMotionListener(mouseMotionListener);

        world = levelList.get(currentLevel-1);
        world.initialize();

        view = new UserView(world, 1366, 768);

        world.setView(view);
        view.setSize(1366,768);
        timer = new JLabel("" + world.currentTime);
        timer.setFont(new Font("timerFont", Font.PLAIN, 30));
        timer.setLocation(1000,60);
        view.add(timer);
        //view.setLayout(new BorderLayout());
        view.setLayout(null);
        view.add(levelEndPanel, BorderLayout.CENTER);
        levelEndPanel.add(levelEndMessage, BorderLayout.NORTH);
        levelEndPanel.add(levelEndTime, BorderLayout.CENTER);
        levelEndPanel.add(levelEndResults, BorderLayout.CENTER);
        levelEndPanel.add(nextLevelButton);
        view.add(pauseMenu.panel);

        frame.add(view); //show world in window
        frame.setVisible(true);

        //listeners
        GameMouseListener gameMouseListener = new GameMouseListener(view);
        frame.addMouseListener(gameMouseListener); //mouse listener
        frame.addMouseMotionListener(gameMouseListener); //mouse motion listener
        world.addStepListener(new GameStepListener(world.getPlayer())); //step listener
        frame.addKeyListener(new GameKeyListener(world.getPlayer())); //key listener

        world.start();
        frame.requestFocus();

        //debug
        //view.setGridResolution(1);
        //DebugViewer debugView = new DebugViewer(world, 500, 500);
    }

    public static void main(String[] args) {
        new QuantumLock();
    }

    private static String formatTimer(long time) {
        long seconds = time/1000;
        seconds = seconds%60;
        String secondsS = (seconds<10) ? "0"+seconds : ""+seconds;
        long minutes = seconds/60;
        String minutesS = (minutes<10) ? "0"+minutes : ""+minutes;
        time = time%1000;
        return "" + minutesS + ":" + secondsS + ":" + time;
    }

    public static void updateTimer(long time) {
        timer.setText(formatTimer(time));
        timer.setSize(140, 25);
    }

    public static void showLevelEnd(long time) {
        levelEndPanel.setVisible(true);
        levelEndMessage.setEnabled(true);
        levelEndMessage.setVisible(true);
        levelEndResults.setEnabled(false);
        levelEndResults.setVisible(true);
        nextLevelButton.setEnabled(true);

        levelEndPanel.setLocation(350,200);

        levelEndTime.setText(formatTimer(time));
        levelEndTime.setLocation(1000,300);

        levelEndMessage.setLocation(1000,200);
        levelEndResults.setLocation(1000,500);

        nextLevelButton.setLocation(1000, 800);

        DefaultTableModel model = (DefaultTableModel) levelEndResults.getModel();
        model.addRow(new Object[]{"1", "You", timer.getText()});

        nextLevelButton.requestFocus();
    }

    public static void hidelevelEnd() {
        for (int i = 0; i < levelEndResults.getRowCount(); i++) {
            for(int j = 0; j < levelEndResults.getColumnCount(); j++) {
                levelEndResults.setValueAt("", i, j);
            }
        }
        levelEndPanel.setVisible(false);
        levelEndMessage.setEnabled(false);
        levelEndMessage.setVisible(false);
        levelEndResults.setVisible(false);
        nextLevelButton.setEnabled(false);
        frame.requestFocus();
    }
    
    public static void pauseGame() {
        isPaused = true;
        world.pause();
        pauseMenu.show();
        frame.requestFocus();
    }
    
    public static void resumeGame() {
        isPaused = false;
        world.resume();
        pauseMenu.hide();
        frame.requestFocus();
    }
}
