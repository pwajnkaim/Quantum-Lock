/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;
import quantumLock.Levels.Level;

/**
 *
 * @author pwajn
 */
public class GameKeyListener extends KeyAdapter{
    private PlayerCharacter player;
    public List<Object> fuck = new ArrayList<>();
    
    GameKeyListener(PlayerCharacter player) {
        this.player = player;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE) { //JUMP
            if (player.jumps > 0 && player.getLinearVelocity().y < 5) {
                player.applyImpulse(new Vec2(0,player.jumpStrength));
                player.jumps--;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_A) { //LEFT
            player.movingLeft = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) { //DOWN
        }
        if (e.getKeyCode() == KeyEvent.VK_D) { //RIGHT
            player.movingRight = true;
            fuck = null;
        }
        if (e.getKeyCode() == KeyEvent.VK_R) { //Reset Level
            ((Level)player.getWorld()).levelReset();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) { //UP
        }
        if (e.getKeyCode() == KeyEvent.VK_A) { //LEFT
            player.movingLeft = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) { //DOWN
        }
        if (e.getKeyCode() == KeyEvent.VK_D) { //RIGHT
            player.movingRight = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { //pause the game
            if (QuantumLock.isPaused) QuantumLock.resumeGame();
            else QuantumLock.pauseGame();
        }
    }
}
