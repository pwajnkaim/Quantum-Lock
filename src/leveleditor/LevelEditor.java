package leveleditor;

import city.cs.engine.*;
import leveleditor.bodies.BoxStaticBody;
import leveleditor.bodies.FileManager;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class LevelEditor {
    public static boolean movingUp, movingDown, movingLeft, movingRight;
    public static World world;
    public static UserView view;
    public static ControlPanel controlPanel = new ControlPanel();
    public static MouseController mouseController;
    public static MenuBar menuBar;
    public static FileManager fileManager = new FileManager();
    public static JFrame frame;

    public static File saveFile;

    private LevelEditor() {
        frame = new JFrame("Level Editor");

        //frame.setSize(1200, 900);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //close when closed
        frame.setLocationByPlatform(true);
        frame.setResizable(true);
        frame.setLayout(new BorderLayout());

        world = new World();
        view = new UserView(world, 1200, 620);
        frame.add(view, BorderLayout.CENTER); //show world in window
        frame.pack();

        makeBodies();

        mouseController = new MouseController(view);

        JScrollPane scrollPane = new JScrollPane(controlPanel.panel);
        frame.add(scrollPane, BorderLayout.WEST);
        menuBar = new MenuBar();
        frame.add(menuBar, BorderLayout.NORTH);
        //view.validate();

        view.addMouseListener(mouseController);
        view.addMouseMotionListener(mouseController);
        view.addMouseWheelListener(mouseController);
        world.addStepListener(new EditorStepListener());
        KeyController keyController = new KeyController();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                keyController.handleInput(e);
                return false;
            }
        });

        view.setGridResolution(1);
        frame.setVisible(true);
        world.start();
    }

    private void makeBodies() {
        BoxStaticBody staticBody0 = new BoxStaticBody(world, new Vec2(4.0f, 1.0f));
        staticBody0.setPosition(new Vec2(68.0f, 1.0f));
        BoxStaticBody staticBody1 = new BoxStaticBody(world, new Vec2(0.5f, 1.5f));
        staticBody1.setPosition(new Vec2(64.5f, 3.5f));
        BoxStaticBody staticBody2 = new BoxStaticBody(world, new Vec2(2.5f, 0.25f));
        staticBody2.setPosition(new Vec2(61.5f, 1.5f));
        BoxStaticBody staticBody3 = new BoxStaticBody(world, new Vec2(3.0f, 0.5f));
        staticBody3.setPosition(new Vec2(69.0f, -10.5f));
        BoxStaticBody staticBody4 = new BoxStaticBody(world, new Vec2(0.5f, 2.0f));
        staticBody4.setPosition(new Vec2(71.5f, -8.0f));
        BoxStaticBody staticBody5 = new BoxStaticBody(world, new Vec2(0.5f, 2.0f));
        staticBody5.setPosition(new Vec2(66.5f, -8.0f));
        BoxStaticBody staticBody6 = new BoxStaticBody(world, new Vec2(9.5f, 0.5f));
        staticBody6.setPosition(new Vec2(81.5f, -6.5f));
        BoxStaticBody staticBody7 = new BoxStaticBody(world, new Vec2(7.5f, 0.5f));
        staticBody7.setPosition(new Vec2(58.5f, -6.5f));
        BoxStaticBody staticBody8 = new BoxStaticBody(world, new Vec2(0.5f, 0.5f));
        staticBody8.setPosition(new Vec2(78.0f, -5.5f));
        BoxStaticBody staticBody9 = new BoxStaticBody(world, new Vec2(0.5f, 0.5f));
        staticBody9.setPosition(new Vec2(90.5f, -5.5f));
        BoxStaticBody staticBody10 = new BoxStaticBody(world, new Vec2(0.5f, 0.5f));
        staticBody10.setPosition(new Vec2(87.5f, -5.5f));
        BoxStaticBody staticBody11 = new BoxStaticBody(world, new Vec2(0.5f, 0.5f));
        staticBody11.setPosition(new Vec2(83.5f, -5.5f));
        BoxStaticBody staticBody12 = new BoxStaticBody(world, new Vec2(0.5f, 0.5f));
        staticBody12.setPosition(new Vec2(82.0f, -4.5f));
        BoxStaticBody staticBody13 = new BoxStaticBody(world, new Vec2(0.5f, 0.5f));
        staticBody13.setPosition(new Vec2(82.5f, -5.5f));
        BoxStaticBody staticBody14 = new BoxStaticBody(world, new Vec2(0.5f, 0.5f));
        staticBody14.setPosition(new Vec2(81.5f, -5.5f));
        BoxStaticBody staticBody15 = new BoxStaticBody(world, new Vec2(0.5f, 0.5f));
        staticBody15.setPosition(new Vec2(75.5f, -5.5f));
        BoxStaticBody staticBody16 = new BoxStaticBody(world, new Vec2(0.5f, 2.5f));
        staticBody16.setPosition(new Vec2(109.5f, 20.5f));
        BoxStaticBody staticBody17 = new BoxStaticBody(world, new Vec2(9.0f, 0.5f));
        staticBody17.setPosition(new Vec2(101.0f, 23.5f));
        BoxStaticBody staticBody18 = new BoxStaticBody(world, new Vec2(10.0f, 0.5f));
        staticBody18.setPosition(new Vec2(100.0f, 17.5f));
        BoxStaticBody staticBody19 = new BoxStaticBody(world, new Vec2(1.0f, 0.5f));
        staticBody19.setPosition(new Vec2(81.5f, 7.5f));
        BoxStaticBody staticBody20 = new BoxStaticBody(world, new Vec2(0.5f, 6.0f));
        staticBody20.setPosition(new Vec2(91.5f, 29.0f));
        BoxStaticBody staticBody21 = new BoxStaticBody(world, new Vec2(45.0f, 0.5f));
        staticBody21.setPosition(new Vec2(46.0f, 34.5f));
        BoxStaticBody staticBody22 = new BoxStaticBody(world, new Vec2(0.5f, 18.0f));
        staticBody22.setPosition(new Vec2(72.5f, 16.0f));
        BoxStaticBody staticBody23 = new BoxStaticBody(world, new Vec2(10.5f, 0.5f));
        staticBody23.setPosition(new Vec2(61.5f, 9.5f));
        BoxStaticBody staticBody24 = new BoxStaticBody(world, new Vec2(0.25f, 2.0f));
        staticBody24.setPosition(new Vec2(72.5f, -4.0f));
        BoxStaticBody staticBody25 = new BoxStaticBody(world, new Vec2(0.5f, 12.0f));
        staticBody25.setPosition(new Vec2(91.5f, 5.0f));
        BoxStaticBody staticBody26 = new BoxStaticBody(world, new Vec2(3.0f, 0.5f));
        staticBody26.setPosition(new Vec2(63.0f, 7.0f));
        BoxStaticBody staticBody27 = new BoxStaticBody(world, new Vec2(2.0f, 4.0f));
        staticBody27.setPosition(new Vec2(53.0f, 5.0f));
        BoxStaticBody staticBody28 = new BoxStaticBody(world, new Vec2(4.0f, 0.5f));
        staticBody28.setPosition(new Vec2(55.0f, 0.5f));
        BoxStaticBody staticBody29 = new BoxStaticBody(world, new Vec2(16.0f, 0.5f));
        staticBody29.setPosition(new Vec2(36.0f, -5.5f));
        BoxStaticBody staticBody30 = new BoxStaticBody(world, new Vec2(0.25f, 2.0f));
        staticBody30.setPosition(new Vec2(50.5f, -3.0f));
        BoxStaticBody staticBody31 = new BoxStaticBody(world, new Vec2(0.5f, 17.5f));
        staticBody31.setPosition(new Vec2(50.5f, 16.5f));
        BoxStaticBody staticBody32 = new BoxStaticBody(world, new Vec2(17.0f, 0.5f));
        staticBody32.setPosition(new Vec2(18.0f, -0.5f));
        BoxStaticBody staticBody33 = new BoxStaticBody(world, new Vec2(0.25f, 1.5f));
        staticBody33.setPosition(new Vec2(35.5f, -3.5f));
        BoxStaticBody staticBody34 = new BoxStaticBody(world, new Vec2(0.5f, 18.0f));
        staticBody34.setPosition(new Vec2(35.5f, 16.0f));
        BoxStaticBody staticBody35 = new BoxStaticBody(world, new Vec2(0.5f, 2.5f));
        staticBody35.setPosition(new Vec2(19.5f, -6.5f));
        BoxStaticBody staticBody36 = new BoxStaticBody(world, new Vec2(7.0f, 0.5f));
        staticBody36.setPosition(new Vec2(7.0f, -5.5f));
        BoxStaticBody staticBody37 = new BoxStaticBody(world, new Vec2(0.5f, 20.0f));
        staticBody37.setPosition(new Vec2(0.5f, 15.0f));
        BoxStaticBody staticBody38 = new BoxStaticBody(world, new Vec2(0.5f, 2.0f));
        staticBody38.setPosition(new Vec2(14.5f, -7.0f));
        BoxStaticBody staticBody39 = new BoxStaticBody(world, new Vec2(3.0f, 0.5f));
        staticBody39.setPosition(new Vec2(17.0f, -9.5f));

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
