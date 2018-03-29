package leveleditor;

import java.awt.event.KeyEvent;

/**
 *
 * @author pwajn
 */
public class KeyController {
    public void handleInput(KeyEvent e) {
        if (!LevelEditor.inDialog) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_W) { //UP
                    LevelEditor.movingUp = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_A) { //LEFT
                    LevelEditor.movingLeft = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) { //DOWN
                    if (e.isControlDown()) { //CTL+S save
                        MenuBar.save();
                    } else {
                        LevelEditor.movingDown = true;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_D) { //RIGHT
                    LevelEditor.movingRight = true;
                }

            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                if (e.getKeyCode() == KeyEvent.VK_W) { //UP
                    LevelEditor.movingUp = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_A) { //LEFT
                    LevelEditor.movingLeft = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_S) { //DOWN
                    LevelEditor.movingDown = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_D) { //RIGHT
                    LevelEditor.movingRight = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_Z) {
                    LevelEditor.view.setZoom(20);
                }
            }
        }
    }
}