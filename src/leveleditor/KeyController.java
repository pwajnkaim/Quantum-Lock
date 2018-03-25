package leveleditor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import city.cs.engine.UserView;
import leveleditor.bodies.BoxStaticBody;

/**
 *
 * @author pwajn
 */
public class KeyController extends KeyAdapter{
    private LevelEditor levelEditor;
    private UserView view;

    public KeyController(LevelEditor levelEditor){
        this.levelEditor = levelEditor;
        this.view = levelEditor.view;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) { //UP
            levelEditor.movingUp = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) { //LEFT
            levelEditor.movingLeft = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) { //DOWN
            levelEditor.movingDown = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) { //RIGHT
            levelEditor.movingRight = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) { //UP
            levelEditor.movingUp = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) { //LEFT
            levelEditor.movingLeft = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) { //DOWN
            levelEditor.movingDown = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) { //RIGHT
            levelEditor.movingRight = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_N) {
            BoxStaticBody body = new BoxStaticBody(levelEditor.world);
            body.setPosition(view.viewToWorld(view.getMousePosition()));
        }
        if (e.getKeyCode() == KeyEvent.VK_Z) {
            view.setZoom(20);
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            levelEditor.generateCode();
        }
        if (e.getKeyCode() == KeyEvent.VK_H) {
            levelEditor.generateEditorCode();
        }
    }
}