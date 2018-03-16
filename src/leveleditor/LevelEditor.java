

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
        mouseController.setKeyController(keyController); //let key and mouse controller reference each other
        keyController.setMouseController(mouseController);

        view.addMouseListener(mouseController);
        view.addMouseMotionListener(mouseController);
        view.addMouseWheelListener(mouseController);
        view.addKeyListener(keyController);
        world.addStepListener(new EditorStepListener(this, view));

        view.setGridResolution(1);
        world.start();
    }

    public void makeBodies() {
        BoxStaticBody staticBody0 = new BoxStaticBody(world, new Vec2(8.5f, 0.5f));
        staticBody0.setPosition(new Vec2(97.5f, -8.5f));
        BoxStaticBody staticBody1 = new BoxStaticBody(world, new Vec2(0.5f, 3.5f));
        staticBody1.setPosition(new Vec2(106.5f, -11.5f));
        BoxStaticBody staticBody2 = new BoxStaticBody(world, new Vec2(14.0f, 0.5f));
        staticBody2.setPosition(new Vec2(93.0f, -15.5f));
        BoxStaticBody staticBody3 = new BoxStaticBody(world, new Vec2(0.25f, 2.0f));
        staticBody3.setPosition(new Vec2(88.5f, -13.0f));
        BoxStaticBody staticBody4 = new BoxStaticBody(world, new Vec2(0.5f, 7.0f));
        staticBody4.setPosition(new Vec2(88.5f, -4.0f));
        BoxStaticBody staticBody5 = new BoxStaticBody(world, new Vec2(13.0f, 0.5f));
        staticBody5.setPosition(new Vec2(62.0f, -15.5f));
        BoxStaticBody staticBody6 = new BoxStaticBody(world, new Vec2(14.0f, 0.5f));
        staticBody6.setPosition(new Vec2(75.0f, 3.5f));
        BoxStaticBody staticBody7 = new BoxStaticBody(world, new Vec2(3.0f, 0.5f));
        staticBody7.setPosition(new Vec2(52.0f, -0.5f));
        BoxStaticBody staticBody8 = new BoxStaticBody(world, new Vec2(0.5f, 2.0f));
        staticBody8.setPosition(new Vec2(55.5f, 1.0f));
        BoxStaticBody staticBody9 = new BoxStaticBody(world, new Vec2(0.5f, 9.5f));
        staticBody9.setPosition(new Vec2(48.5f, -6.5f));
        BoxStaticBody staticBody10 = new BoxStaticBody(world, new Vec2(0.5f, 14.5f));
        staticBody10.setPosition(new Vec2(-19.5f, 9.5f));
        BoxStaticBody staticBody11 = new BoxStaticBody(world, new Vec2(41.0f, 0.5f));
        staticBody11.setPosition(new Vec2(22.0f, 23.5f));
        BoxStaticBody staticBody12 = new BoxStaticBody(world, new Vec2(0.5f, 10.0f));
        staticBody12.setPosition(new Vec2(63.5f, 14.0f));
        BoxStaticBody staticBody13 = new BoxStaticBody(world, new Vec2(2.0f, 0.25f));
        staticBody13.setPosition(new Vec2(59.0f, 3.5f));
        BoxStaticBody staticBody14 = new BoxStaticBody(world, new Vec2(1.5f, 0.5f));
        staticBody14.setPosition(new Vec2(55.5f, 3.5f));
        BoxStaticBody staticBody15 = new BoxStaticBody(world, new Vec2(9.5f, 0.5f));
        staticBody15.setPosition(new Vec2(40.5f, 3.5f));
        BoxStaticBody staticBody16 = new BoxStaticBody(world, new Vec2(0.5f, 2.5f));
        staticBody16.setPosition(new Vec2(30.5f, 1.5f));
        BoxStaticBody staticBody17 = new BoxStaticBody(world, new Vec2(5.0f, 0.5f));
        staticBody17.setPosition(new Vec2(26.0f, -1.5f));
        BoxStaticBody staticBody18 = new BoxStaticBody(world, new Vec2(6.0f, 0.5f));
        staticBody18.setPosition(new Vec2(16.0f, -2.5f));
        BoxStaticBody staticBody19 = new BoxStaticBody(world, new Vec2(0.5f, 2.0f));
        staticBody19.setPosition(new Vec2(9.5f, -3.0f));
        BoxStaticBody staticBody20 = new BoxStaticBody(world, new Vec2(15.0f, 0.5f));
        staticBody20.setPosition(new Vec2(-5.0f, -5.5f));

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
