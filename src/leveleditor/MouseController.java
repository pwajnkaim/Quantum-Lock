package leveleditor;

import city.cs.engine.*;
import java.awt.event.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import leveleditor.bodies.*;
import org.jbox2d.common.Vec2;

/**
 *
 * @author pwajn
 */
public class MouseController extends MouseAdapter{
    final public WorldView view;

    private Body holding = null;
    public FakeBody selected = null;
    private boolean clickedOnBody = false;

    private Vec2 offset; //offset between mouse and screen centre, used for dragging
    private Vec2 holdingOffset; //used for dragging bodies (so their center doesn't snap to the mouse)
    MouseController(WorldView view) {
        this.view = view;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Vec2 mousePos = view.viewToWorld(e.getPoint());
        offset = mousePos;

        for (StaticBody body : view.getWorld().getStaticBodies()) {
            if(body.contains(mousePos)){ //if mouse clicked on a body
                clickedOnBody = true;
                if (e.getButton() == 1) { //left click
                    holding = body;
                    holdingOffset = holding.getPosition().sub(mousePos);
                } else if(e.getButton() == 3) {// right click
                    select(body);
                }
                break;
            }
            clickedOnBody = false;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (holding != null) {
            Vec2 roundedPos = holding.getPosition();
            roundedPos.x = Math.round(roundedPos.x*2)/2.f; //round to nearest multiple of 5
            roundedPos.y = Math.round(roundedPos.y*2)/2.f;
            holding.setPosition(roundedPos);
            select(holding);
        }
        holding = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Vec2 mousePos = view.viewToWorld(e.getPoint());
        if (holding != null) {
            holding.setPosition(mousePos.add(holdingOffset));
        } else if(!clickedOnBody){
            Vec2 delta = view.getCentre().sub(mousePos);
            view.setCentre(delta.add(offset));
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            view.setZoom(view.getZoom()+0.6f);
        } else {
            view.setZoom(view.getZoom()-0.6f);
        }
    }

    public void select(Body body) {
        selected = (FakeBody)body;
        if (body instanceof BoxStaticBody) LevelEditor.controlPanel.boxStaticSelected((BoxStaticBody)body);
        else if (body instanceof CircleStaticBody) LevelEditor.controlPanel.circleStaticSelected((CircleStaticBody)body);
        else if (body instanceof SlidingDoor) LevelEditor.controlPanel.slidingDoorSelected((SlidingDoor)body);
        else if (body instanceof Crate) LevelEditor.controlPanel.crateSelected((Crate)body);
        else if (body instanceof Ball) LevelEditor.controlPanel.ballSelected((Ball)body);
        else if (body instanceof Button) LevelEditor.controlPanel.buttonSelected((Button)body);
        else if (body instanceof Exit) LevelEditor.controlPanel.exitSelected((Exit)body);
        else if (body instanceof Player) LevelEditor.controlPanel.playerSelected((Player)body);
    }

    public void newBody(Class<?> bodyClass) {
        try {
            Constructor<?> constructor = bodyClass.getConstructor(World.class);
            Object obj = constructor.newInstance(view.getWorld());
            FakeBody body = (FakeBody)obj;
            int count = 0;
            for (StaticBody instance : view.getWorld().getStaticBodies()) {
                if (bodyClass.isInstance(instance)) count++;
            }
            body.setName(body.toString()+count);
            body.setPosition(view.getCentre());
            select(body);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}