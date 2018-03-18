/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock;

import quantumLock.Levels.Level1.Level1;
import quantumLock.Levels.*;
import city.cs.engine.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author pwajn
 */
public class QuantumLock extends KeyAdapter{
    public final JFrame frame;
    Level world;
    UserView view;

    public enum GameState {
        MAINMENU, GAME
    }
    private GameState gameState = GameState.MAINMENU;
    private int currentLevel = 0; //0 = main menu

    public List<Level> levelList = new ArrayList<>();

    private QuantumLock(){
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

        //startMainMenu();
        //newGame();
        goToLevel(3);
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

        //if (view==null) {
            view = new UserView(world, 1366, 768);

        //}

        //view.setWorld(world);
        world.setView(view);
        view.setSize(1366,768);
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
        DebugViewer debugView = new DebugViewer(world, 500, 500);
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
}
