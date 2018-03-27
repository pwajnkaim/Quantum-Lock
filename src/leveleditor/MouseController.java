package leveleditor;

import city.cs.engine.*;
import java.awt.event.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import leveleditor.bodies.BoxStaticBody;
import leveleditor.bodies.FakeBody;
import leveleditor.bodies.SlidingDoor;
import org.jbox2d.common.Vec2;

/**
 *
 * @author pwajn
 */
public class MouseController extends MouseAdapter{
    final public WorldView view;
    private ControlPanel controlPanel;

    private Body holding = null;
    public FakeBody selected = null;
    private boolean clickedOnBody = false;

    private Vec2 offset; //offset between mouse and screen centre, used for dragging
    private Vec2 holdingOffset; //used for dragging bodies (so their center doesn't snap to the mouse)
    MouseController(WorldView view) {
        this.view = view;
    }

    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
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
                    if (selected instanceof BoxStaticBody) {

                    }
                }
                break;
            }
            clickedOnBody = false;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (holding != null) {
            //System.out.println(holding.getPosition());
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

    private void select(Body body) {
        selected = (FakeBody)body;
        if (body instanceof BoxStaticBody) controlPanel.boxStaticSelected((BoxStaticBody)body);
        else if (body instanceof SlidingDoor) controlPanel.slidingDoorSelected((SlidingDoor)body);
    }

    public void newBody(Class<?> bodyClass) {
        try {
            Constructor<?> constructor = bodyClass.getConstructor(World.class);
            Object obj = constructor.newInstance(view.getWorld());
            FakeBody body = (FakeBody)obj;
            body.setName("boxStaticBody"+0);
            body.setPosition(view.getCentre());
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void newBoxStaticBody() {
        BoxStaticBody boxStaticBody = new BoxStaticBody(view.getWorld());
        int count = 0;
        for (StaticBody body : view.getWorld().getStaticBodies()) {
            if (body instanceof BoxStaticBody) count++;
        }
        boxStaticBody.setName("boxStaticBody"+count);
        boxStaticBody.setPosition(view.getCentre());
        select(boxStaticBody);
    }

    public void newSlidingDoor() {
        SlidingDoor slidingDoor = new SlidingDoor(view.getWorld());
        int count = 0;
        for (StaticBody body : view.getWorld().getStaticBodies()) {
            if (body instanceof SlidingDoor) count++;
        }
        slidingDoor.setName("slidingDoor"+count);
        slidingDoor.setPosition(view.getCentre());
        select(slidingDoor);
    }
}