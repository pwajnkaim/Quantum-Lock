package quantumLock;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 *
 * @author pwajn
 */
public class SlidingDoor extends StaticBody{
    private Vec2 openPos;
    private Vec2 closedPos;
    public boolean startClosed;
    private float slidingSpeed;
    private boolean opening = false;
    private boolean closing = false;
    private boolean stayOpen = false;

    public SlidingDoor(World world, Shape shape, Vec2 openPos, Vec2 closedPos, float slidingSpeed, boolean closed) {
        super(world);
        this.openPos = openPos;
        this.closedPos = closedPos;
        this.slidingSpeed = slidingSpeed;

        new SolidFixture(this, shape);

        startClosed = closed;
        if(startClosed) {
            setPosition(closedPos);
            close();
        } else {
            setPosition(openPos);
            open();
        }
    }
    
    public void stayOpen(boolean bool) {
        this.stayOpen = bool;
    }

    void open() {
        opening = true;
        closing = false;
    }
    void close() {
        opening = false;
        closing = true;
    }
    
    public void moveDoor() {
        if(opening) {
            Vec2 delta = openPos.sub(getPosition());
            delta.normalize();
            setPosition(getPosition().add(delta.mul(slidingSpeed)));
        } else if(closing && !stayOpen) {
            Vec2 delta = closedPos.sub(getPosition());
            delta.normalize();
            setPosition(getPosition().add(delta.mul(slidingSpeed)));
        }
    }
    
}
