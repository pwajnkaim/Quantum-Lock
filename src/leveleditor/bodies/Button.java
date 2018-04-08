package leveleditor.bodies;

import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.util.ArrayList;

public class Button extends FakeBody implements Density{
    private float density = 50;
    public ArrayList<SlidingDoor> doors = new ArrayList<>();
    public Button(World world) {
        super(world);
        new SolidFixture(this, new BoxShape(2,2));
        size = new Vec2(2,2);
        setFillColor(new Color(136, 99, 95));
    }

    public Button(World world, Vec2 size) {
        super(world);
        new SolidFixture(this, new BoxShape(size.x,size.y));
        this.size = size;
        setFillColor(new Color(136, 99, 95));
    }

    public void setDensity(float density) {
        this.density = density;
    }
    public float getDensity() {
        return density;
    }
    public void addDoor(SlidingDoor door) {
        if (!doors.contains(door))
            doors.add(door);
    }
    public void removeDoor(SlidingDoor door) {
        doors.remove(door);
    }

    @Override
    public FakeBody setSize(Vec2 size) {
        Button newBody = new Button(this.getWorld(),size);
        newBody.setName(this.getName());
        newBody.setPosition(this.getPosition());
        newBody.setAngle(this.getAngle());
        newBody.setDensity(density);
        newBody.doors = this.doors;
        this.destroy();
        return newBody;
    }

    @Override
    public String toString() {
        return "button";
    }
}
