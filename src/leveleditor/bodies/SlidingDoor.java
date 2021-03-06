package leveleditor.bodies;

import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class SlidingDoor extends FakeBody {
    public int id; //used when saving and loading files (for button connections)
    private Vec2 openPos;
    private Vec2 closedPos;
    private float slidingSpeed = 0.05f;
    private boolean startOpen = false;
    private boolean stayOpen = false;

    public SlidingDoor(World world) {
        super(world);
        new SolidFixture(this, new BoxShape(0.25f,1f));
        size = new Vec2(0.25f,1f);
        closedPos = this.getPosition();
        openPos = this.getPosition();
        setFillColor(new Color(169, 164, 163));
    }

    public SlidingDoor(World world, Vec2 size) {
        super(world);
        new SolidFixture(this, new BoxShape(size.x,size.y));
        this.size = size;
        closedPos = this.getPosition();
        openPos = this.getPosition();
        setFillColor(new Color(169, 164, 163));
    }

    @Override
    public void setPosition(Vec2 position) {
        super.setPosition(position);
        updatePositions();
    }

    public void updatePositions() {
        if(startOpen) openPos = getPosition();
        else closedPos = getPosition();
    }

    public Vec2 getOpenPos() {
        return openPos;
    }
    public void setOpenPos(Vec2 openPos) {
        this.openPos = openPos;
    }
    public Vec2 getClosedPos() {
        return closedPos;
    }
    public void setClosedPos(Vec2 closedPos) {
        this.closedPos = closedPos;
    }
    public float getSlidingSpeed() {
        return slidingSpeed;
    }
    public void setSlidingSpeed(float slidingSpeed) {
        this.slidingSpeed = slidingSpeed;
    }
    public boolean isStartOpen() {
        return startOpen;
    }
    public void setStartOpen(boolean startOpen) {
        this.startOpen = startOpen;
    }
    public boolean isStayOpen() {
        return stayOpen;
    }
    public void setStayOpen(boolean stayOpen) {
        this.stayOpen = stayOpen;
    }

    @Override
    public FakeBody setSize(Vec2 size) {
        SlidingDoor newBody = new SlidingDoor(this.getWorld(),size);
        newBody.setName(this.getName());
        newBody.setPosition(this.getPosition());
        newBody.setAngle(this.getAngle());
        newBody.setOpenPos(openPos);
        newBody.setClosedPos(closedPos);
        newBody.setSlidingSpeed(slidingSpeed);
        newBody.setStartOpen(startOpen);
        newBody.setStayOpen(stayOpen);
        this.destroy();
        return newBody;
    }

    @Override
    public String toString() {
        return "slidingDoor";
    }

    @Override
    public void destroy() {
        for (StaticBody body : getWorld().getStaticBodies()) {
            if (body instanceof Button) {
                ((Button)body).removeDoor(this);
            }
        }
        super.destroy();
    }
}
