package leveleditor;

import city.cs.engine.*;
import javafx.scene.shape.Circle;
import leveleditor.bodies.*;
import leveleditor.bodies.Button;
import org.jbox2d.common.Vec2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //close when closed
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
        MenuBar menuBar = new MenuBar();
        frame.add(menuBar, BorderLayout.NORTH);

        view.addMouseListener(mouseController);
        view.addMouseMotionListener(mouseController);
        view.addMouseWheelListener(mouseController);
        world.addStepListener(new EditorStepListener());
        KeyController keyController = new KeyController();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            keyController.handleInput(e);
            return false;
        });

        view.setGridResolution(1);
        frame.setVisible(true);
        world.start();
    }

    private void makeBodies() {
    }

    static void clearWorld() {
        for(StaticBody body : world.getStaticBodies()) {
            body.destroy();
        }
    }

    static void generateImage() {
        float topEdge = 0, bottomEdge = 0, leftEdge = 0, rightEdge = 0;
        for(StaticBody staticBody : world.getStaticBodies()){
            FakeBody body = (FakeBody)staticBody;
            if (body.getTopPoint() > topEdge)
                topEdge = body.getTopPoint();
            if (body.getLowPoint() < bottomEdge)
                bottomEdge = body.getLowPoint();
            if (body.getLeftPoint() < leftEdge)
                leftEdge = body.getLeftPoint();
            if (body.getRightPoint() > rightEdge)
                rightEdge = body.getRightPoint();
        }
        float width = (Math.abs(leftEdge)<Math.abs(rightEdge))? rightEdge : leftEdge;
        float height = (Math.abs(topEdge)<Math.abs(bottomEdge))? bottomEdge : topEdge;
        BufferedImage image = new BufferedImage((int)Math.ceil(width*2*20), (int)Math.ceil(height*2*20), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        for(StaticBody staticBody : world.getStaticBodies()) {
            FakeBody body = (FakeBody)staticBody;
            graphics.setColor(new Color(87, 87, 87));
            float bWidth = (body.getSize().x)*40;
            float bHeight = (body.getSize().y)*40;
            int x = Math.round(((body.getPosition().x+width)*20)-bWidth/2);
            int y = Math.abs(Math.round(((body.getPosition().y-height)*20)+bHeight/2));
            if(body instanceof CircleStaticBody || body instanceof Ball) {
                graphics.setPaint(new Color(87, 87, 87));
                if(body instanceof Ball) graphics.setPaint(new Color(157, 161, 153));
                graphics.fill(new Ellipse2D.Float(x, y, bWidth, bWidth));
                graphics.setPaint(new Color(34, 34, 34));
                graphics.draw(new Ellipse2D.Float(x, y, bWidth, bWidth)); //draw outline
            } else {
                graphics.setPaint(new Color(87, 87, 87));
                if(body instanceof Crate) graphics.setPaint(new Color(110, 79, 45));
                else if(body instanceof Button) graphics.setPaint(new Color(136, 99, 95));
                else if(body instanceof Exit) graphics.setPaint(new Color(99, 136, 132));
                else if(body instanceof Player) graphics.setPaint(new Color(95, 136, 91));
                else if(body instanceof SlidingDoor) graphics.setPaint(new Color(169, 164, 163));
                graphics.fill(new Rectangle(x, y, Math.round(bWidth), Math.round(bHeight)));
                graphics.setPaint(new Color(34, 34, 34));
                graphics.draw(new Rectangle(x, y, Math.round(bWidth), Math.round(bHeight)));
            }
        }
        try {
            ImageIO.write(image, "png", new File("maps/"+saveFile.getName().replaceFirst("[.][^.]+$", "")+"_generated.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LevelEditor();
    }
}
