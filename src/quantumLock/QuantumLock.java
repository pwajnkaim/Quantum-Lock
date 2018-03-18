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
public class QuantumLock extends KeyAdapter implements ActionListener{
    public final JFrame frame;
    Level world;
    UserView view;

    static private Label timer;
    static private Panel levelEndPanel = new Panel();
    static private Label levelEndMessage = new Label("Well Done, Your Time Was:");
    static private Label levelEndTime = new Label();
    static private JTable levelEndResults;
    static private JButton nextLevelButton = new JButton("Next Level");

    @Override
    public void actionPerformed(ActionEvent e) { //next level button was pressed
        world.clear();
        nextLevel();
        hidelevelEnd();
    }

    public enum GameState {
        MAINMENU, GAME
    }
    private GameState gameState = GameState.MAINMENU;
    private int currentLevel = 0; //0 = main menu

    public List<Level> levelList = new ArrayList<>();

    private QuantumLock(){
        levelEndPanel.setSize(700,500);
        levelEndPanel.setVisible(false);
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

        frame = new JFrame("Rocket Jumper"); //Make a window with JFrame

        frame.addKeyListener(this);
        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //close when closed
        frame.setLocationByPlatform(true);
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);

        startMainMenu();
        //goToLevel(3);
    }

    public void startMainMenu() {
        gameState = GameState.MAINMENU;
        frame.getContentPane().removeAll();

        MainMenu mainMenu = new MainMenu(this);
        frame.add(mainMenu.mainMenu);
        frame.setVisible(true);
    }

    public void newGame(){
        currentLevel = 1;
        startLevel(currentLevel);
    }

    public void nextLevel() {
        currentLevel++;
        startLevel(currentLevel);
    }

    public void goToLevel(int level) {
        currentLevel = level;
        startLevel(currentLevel);
    }

    private void startLevel(int level) {
        gameState = GameState.GAME;
        frame.getContentPane().removeAll();

        for(KeyListener keyListener : frame.getKeyListeners()) {
            frame.removeKeyListener(keyListener);
        }
        for(MouseListener mouseListener : frame.getMouseListeners()) {
            frame.removeMouseListener(mouseListener);
        }
        for(MouseMotionListener mouseMotionListener : frame.getMouseMotionListeners()) {
            frame.removeMouseMotionListener(mouseMotionListener);
        }

        world = levelList.get(level-1);
        world.initialize(this);

        view = new UserView(world, 1366, 768);

        world.setView(view);
        view.setSize(1366,768);
        timer = new Label("" + world.currentTime);
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

        frame.add(view); //show world in window
        frame.setVisible(true);

        //listeners
        GameMouseListener gameMouseListener = new GameMouseListener(view);
        frame.addMouseListener(gameMouseListener); //mouse listener
        frame.addMouseMotionListener(gameMouseListener); //mouse motion listener
        world.addStepListener(new GameStepListener(world.getPlayer())); //step listener
        frame.addKeyListener(new GameKeyListener(world.getPlayer())); //key listener
        
        frame.addKeyListener(this);//for closing the game

        world.start();
        frame.requestFocus();

        //debug
        //view.setGridResolution(1);
        //DebugViewer debugView = new DebugViewer(world, 500, 500);
    }

    public static void main(String[] args) {
        new QuantumLock();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { //quit the game
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }

    static public void updateTimer(long time) {
        long seconds = time/1000;
        String secondsS = (seconds<10) ? "0"+seconds : ""+seconds;
        long minutes = seconds/60;
        String minutesS = (minutes<10) ? "0"+minutes : ""+minutes;
        time = time%1000;
        timer.setText("" + minutesS + ":" + secondsS + ":" + time);
        timer.setSize(140, 25);
    }

    public void showLevelEnd(long time) {
        levelEndPanel.setVisible(true);
        levelEndMessage.setEnabled(true);
        levelEndMessage.setVisible(true);
        levelEndResults.setEnabled(false);
        levelEndResults.setVisible(true);
        nextLevelButton.setEnabled(true);

        levelEndPanel.setLocation(100,200);

        long seconds = time/1000;
        String secondsS = (seconds<10) ? "0"+seconds : ""+seconds;
        long minutes = seconds/60;
        String minutesS = (minutes<10) ? "0"+minutes : ""+minutes;
        time = time%1000;
        levelEndTime.setText("" + minutesS + ":" + secondsS + ":" + time);
        levelEndTime.setLocation(1000,300);

        levelEndMessage.setLocation(1000,200);
        levelEndResults.setLocation(1000,500);

        nextLevelButton.setLocation(1000, 800);

        DefaultTableModel model = (DefaultTableModel) levelEndResults.getModel();
        model.addRow(new Object[]{"1", "You", timer.getText()});

        nextLevelButton.requestFocus();
    }

    public void hidelevelEnd() {
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
}
