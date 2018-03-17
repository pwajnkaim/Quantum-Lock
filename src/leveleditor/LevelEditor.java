package leveleditor;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;

public class LevelEditor {
    public boolean movingUp, movingDown, movingLeft, movingRight;
    public World world;
    public UserView view;

    private LevelEditor() {
        JFrame frame = new JFrame("Level Editor");

        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //close when closed
        frame.setLocationByPlatform(true);
        frame.setResizable(true);
        frame.setVisible(true);

        world = new World();
        view = new UserView(world, 1000, 500);
        frame.add(view); //show world in window
        frame.pack();

        makeBodies();

        MouseController mouseController = new MouseController(view);
        KeyController keyController = new KeyController(this);

        view.addMouseListener(mouseController);
        view.addMouseMotionListener(mouseController);
        view.addMouseWheelListener(mouseController);
        view.addKeyListener(keyController);
        world.addStepListener(new EditorStepListener(this, view));

        view.setGridResolution(1);
        world.start();
    }

    public void makeBodies() {

    }

    public void generateCode() {
        int i = 0;
        for(StaticBody body : world.getStaticBodies()) {
            System.out.println("StaticBody staticBody"+i+" = "+((BoxStaticBody)body).toString());
            System.out.println("staticBody"+i+".setPosition(new Vec2("+body.getPosition().x+"f, "+body.getPosition().y+"f));");
            i++;
        }
    }

    public void generateEditorCode() {
        int i = 0;
        for(StaticBody body : world.getStaticBodies()) {
            System.out.println("BoxStaticBody staticBody"+i+" = new BoxStaticBody(world, new Vec2("+((BoxStaticBody)body).getSize().x+"f, "+((BoxStaticBody)body).getSize().y+"f));");
            System.out.println("staticBody"+i+".setPosition(new Vec2("+body.getPosition().x+"f, "+body.getPosition().y+"f));");
            i++;
        }
    }

    public static void main(String[] args) {
        new LevelEditor();
    }
}
