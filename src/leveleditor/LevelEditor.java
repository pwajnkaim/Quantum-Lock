package leveleditor;

import city.cs.engine.*;
import leveleditor.bodies.BoxStaticBody;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class LevelEditor {
    static boolean movingUp, movingDown, movingLeft, movingRight;
    public static World world;
    public static UserView view;
    static ControlPanel controlPanel = new ControlPanel();
    static MouseController mouseController;
    static boolean inDialog = false;
    public static JFrame frame;

    static File saveFile;

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

        mouseController = new MouseController(view);

        JScrollPane scrollPane = new JScrollPane(controlPanel.panel);
        frame.add(scrollPane, BorderLayout.WEST);
        MenuBar menuBar = new MenuBar();
        frame.add(menuBar, BorderLayout.NORTH);

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

    static void clearWorld() {
        for(StaticBody body : world.getStaticBodies()) {
            body.destroy();
        }
    }

    public static void main(String[] args) {
        new LevelEditor();
    }
}
