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

    private void makeBodies() {
        BoxStaticBody staticBody0 = new BoxStaticBody(world, new Vec2(0.5f, 0.5f));
        staticBody0.setPosition(new Vec2(-18.0f, -3.5f));
        BoxStaticBody staticBody1 = new BoxStaticBody(world, new Vec2(0.5f, 0.5f));
        staticBody1.setPosition(new Vec2(-18.5f, -4.5f));
        BoxStaticBody staticBody2 = new BoxStaticBody(world, new Vec2(0.5f, 0.5f));
        staticBody2.setPosition(new Vec2(-17.5f, -4.5f));
        BoxStaticBody staticBody3 = new BoxStaticBody(world, new Vec2(0.5f, 0.5f));
        staticBody3.setPosition(new Vec2(-6.5f, -4.5f));
        BoxStaticBody staticBody5 = new BoxStaticBody(world, new Vec2(0.5f, 0.5f));
        staticBody5.setPosition(new Vec2(-23.5f, -4.5f));
        BoxStaticBody staticBody6 = new BoxStaticBody(world, new Vec2(0.5f, 0.5f));
        staticBody6.setPosition(new Vec2(62.5f, -18.5f));
        BoxStaticBody staticBody7 = new BoxStaticBody(world, new Vec2(0.25f, 2.0f));
        staticBody7.setPosition(new Vec2(84.5f, -17.0f));
        BoxStaticBody staticBody8 = new BoxStaticBody(world, new Vec2(4.5f, 0.5f));
        staticBody8.setPosition(new Vec2(89.5f, -14.5f));
        BoxStaticBody staticBody9 = new BoxStaticBody(world, new Vec2(0.5f, 2.5f));
        staticBody9.setPosition(new Vec2(94.5f, -16.5f));
        BoxStaticBody staticBody10 = new BoxStaticBody(world, new Vec2(10.0f, 0.5f));
        staticBody10.setPosition(new Vec2(85.0f, -19.5f));
        BoxStaticBody staticBody11 = new BoxStaticBody(world, new Vec2(0.5f, 7.5f));
        staticBody11.setPosition(new Vec2(84.5f, -7.5f));
        BoxStaticBody staticBody12 = new BoxStaticBody(world, new Vec2(16.0f, 0.5f));
        staticBody12.setPosition(new Vec2(69.0f, 0.5f));
        BoxStaticBody staticBody13 = new BoxStaticBody(world, new Vec2(0.5f, 2.0f));
        staticBody13.setPosition(new Vec2(74.5f, -21.0f));
        BoxStaticBody staticBody14 = new BoxStaticBody(world, new Vec2(2.0f, 0.5f));
        staticBody14.setPosition(new Vec2(72.0f, -22.5f));
        BoxStaticBody staticBody15 = new BoxStaticBody(world, new Vec2(0.5f, 2.0f));
        staticBody15.setPosition(new Vec2(69.5f, -21.0f));
        BoxStaticBody staticBody16 = new BoxStaticBody(world, new Vec2(9.0f, 0.5f));
        staticBody16.setPosition(new Vec2(60.0f, -19.5f));
        BoxStaticBody staticBody17 = new BoxStaticBody(world, new Vec2(0.5f, 8.0f));
        staticBody17.setPosition(new Vec2(53.5f, -8.0f));
        BoxStaticBody staticBody18 = new BoxStaticBody(world, new Vec2(0.5f, 10.0f));
        staticBody18.setPosition(new Vec2(50.5f, -10.0f));
        BoxStaticBody staticBody19 = new BoxStaticBody(world, new Vec2(0.5f, 17.0f));
        staticBody19.setPosition(new Vec2(55.5f, 18.0f));
        BoxStaticBody staticBody20 = new BoxStaticBody(world, new Vec2(47.0f, 0.5f));
        staticBody20.setPosition(new Vec2(8.0f, 34.5f));
        BoxStaticBody staticBody21 = new BoxStaticBody(world, new Vec2(13.0f, 0.5f));
        staticBody21.setPosition(new Vec2(28.0f, -8.5f));
        BoxStaticBody staticBody22 = new BoxStaticBody(world, new Vec2(6.0f, 0.5f));
        staticBody22.setPosition(new Vec2(45.0f, 0.5f));
        BoxStaticBody staticBody23 = new BoxStaticBody(world, new Vec2(0.5f, 4.0f));
        staticBody23.setPosition(new Vec2(40.5f, -4.0f));
        BoxStaticBody staticBody24 = new BoxStaticBody(world, new Vec2(0.5f, 4.0f));
        staticBody24.setPosition(new Vec2(15.5f, -4.0f));
        BoxStaticBody staticBody25 = new BoxStaticBody(world, new Vec2(8.0f, 0.5f));
        staticBody25.setPosition(new Vec2(9.0f, 0.5f));
        BoxStaticBody staticBody26 = new BoxStaticBody(world, new Vec2(0.5f, 4.0f));
        staticBody26.setPosition(new Vec2(0.5f, -2.0f));
        BoxStaticBody staticBody27 = new BoxStaticBody(world, new Vec2(0.5f, 20.0f));
        staticBody27.setPosition(new Vec2(-39.5f, 15.0f));
        BoxStaticBody staticBody28 = new BoxStaticBody(world, new Vec2(20.0f, 0.5f));
        staticBody28.setPosition(new Vec2(-20.0f, -5.5f));
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
