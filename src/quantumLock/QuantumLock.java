/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock;

import quantumLock.Levels.Level1;
import quantumLock.Levels.*;
import city.cs.engine.*;
import quantumLock.UI.EndLevelMenu;
import quantumLock.UI.MainMenu;
import quantumLock.UI.PauseMenu;

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
    public static final JFrame frame = new JFrame("Quantum Lock");
    public static Level world;

    private static JLabel timer = new JLabel("00:00:000", SwingConstants.CENTER);
    private static long overallTime = 0;

    private static PauseMenu pauseMenu = new PauseMenu();
    public static boolean isPaused = false;

    private static EndLevelMenu endLevelMenu = new EndLevelMenu();

    @Override
    public void actionPerformed(ActionEvent e) { //next level button was pressed
        world.clear();
        nextLevel();
        hideLevelEnd();
    }

    private static int currentLevel = 0; //0 = main menu

    private static List<Level> levelList = new ArrayList<>();

    private QuantumLock(){
        levelList.add(new Level1());
        levelList.add(new Level2());
        levelList.add(new Level3());

        //frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //close when closed
        frame.setLocationByPlatform(true);
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);

        //set large font
        timer.setFont(new Font("timerFont", Font.PLAIN, 30));

        //position GUI elements based on frame size
        Rectangle bounds = QuantumLock.frame.getBounds();
        //positioning them in the center of the screen
        pauseMenu.pausePanel.setLocation((bounds.width / 2)-pauseMenu.pausePanel.getWidth()/2, (bounds.height / 2)-pauseMenu.pausePanel.getHeight()/2);
        endLevelMenu.endPanel.setLocation((bounds.width / 2)-endLevelMenu.endPanel.getWidth()/2, (bounds.height / 2)-endLevelMenu.endPanel.getHeight()/2);
        timer.setSize(timer.getPreferredSize());
        timer.setLocation((bounds.width / 2)-timer.getWidth()/2,60);

        startMainMenu();
        //goToLevel(1);
    }

    public static void startMainMenu() {
        frame.getContentPane().removeAll();
        if (world != null) world.clear();

        MainMenu mainMenu = new MainMenu();
        frame.add(mainMenu.mainMenuPanel);
        frame.setVisible(true);
    }

    public static void quitGame() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public static void newGame(){
        endLevelMenu.clearResults();
        overallTime = 0;

        currentLevel = 1;
        startLevel();
    }

    public static void nextLevel() {
        world.clear();
        world = null;
        currentLevel++;
        startLevel();
    }

    public static void goToLevel(int level) {
        endLevelMenu.clearResults();
        overallTime = 0;

        currentLevel = level;
        startLevel();
    }

    private static void startLevel() {
        if(currentLevel >= 4) {
            quitGame(); //close game if at level 4
        }
        frame.getContentPane().removeAll(); //clear everything

        for(KeyListener keyListener : frame.getKeyListeners()) frame.removeKeyListener(keyListener);
        for(MouseListener mouseListener : frame.getMouseListeners()) frame.removeMouseListener(mouseListener);
        for(MouseMotionListener mouseMotionListener : frame.getMouseMotionListeners()) frame.removeMouseMotionListener(mouseMotionListener);

        world = levelList.get(currentLevel-1);
        world.initialize();

        UserView view = new UserView(world, 1366, 768);
        world.setView(view);
        //view.setSize(1366,768);
        view.setLayout(null);

        view.add(timer); //timer
        view.add(pauseMenu.pausePanel); //pause menu
        view.add(endLevelMenu.endPanel); //end level screen

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
        DebugViewer debugView = new DebugViewer(world, 500, 500);
    }

    public static void main(String[] args) {
        new QuantumLock();
    }

    private static String formatTimer(long time) {
        long seconds = time/1000;
        long minutes = seconds/60;
        long hours = minutes/60;

        time = time%1000;
        seconds = seconds%60;
        minutes = minutes%60;

        //add leading zeroes
        String secondsS = (seconds<10) ? "0"+seconds : ""+seconds;
        String minutesS = (minutes<10) ? "0"+minutes : ""+minutes;
        String hoursS = (hours<10) ? "0"+hours : ""+hours;

        if(hours > 0) return hoursS + "" + minutesS + ":" + secondsS + ":" + time;
        else    return "" + minutesS + ":" + secondsS + ":" + time;
    }

    public static void updateTimer(long time) {
        timer.setText(formatTimer(time));
        timer.setSize(timer.getPreferredSize());
    }

    public static void showLevelEnd() {
        endLevelMenu.show();

        if(currentLevel+1 >= 4) endLevelMenu.nextLevelButton.setEnabled(false);

        overallTime += world.currentTime;
        endLevelMenu.time.setText("Your Time: " + formatTimer(world.currentTime));
        DefaultTableModel model = (DefaultTableModel) endLevelMenu.results.getModel();
        model.addRow(new Object[]{""+currentLevel, timer.getText(), formatTimer(overallTime)});
    }

    public static void hideLevelEnd() {
        endLevelMenu.hide();
        frame.requestFocus();
    }
    
    public static void pauseGame() {
        if(!endLevelMenu.endPanel.isVisible()) {
            isPaused = true;
            world.pause();
            pauseMenu.show();
            frame.requestFocus();
        }
    }
    
    public static void resumeGame() {
        isPaused = false;
        world.resume();
        pauseMenu.hide();
        frame.requestFocus();
    }
}
