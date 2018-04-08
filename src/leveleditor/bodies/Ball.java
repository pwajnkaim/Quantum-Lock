package leveleditor.bodies;

import city.cs.engine.CircleShape;
import city.cs.engine.SolidFixture;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class Ball extends FakeBody implements Density {
    private float density = 1;
    public Ball(World world) {
        super(world);
        new SolidFixture(this, new CircleShape(0.5f));
        size = new Vec2(0.5f,0.5f);
        setFillColor(new Color(157, 161, 153));
    }

    public Ball(World world, Vec2 size) {
        super(world);
        new SolidFixture(this, new CircleShape(size.x));
        this.size = size;
        setFillColor(new Color(157, 161, 153));
    }

    public void setDensity(float density) {
        this.density = density;
    }
    public float getDensity() {
        return density;
    }

    @Override
    public FakeBody setSize(Vec2 size) {
        Ball newBody = new Ball(this.getWorld(),size);
        newBody.setName(this.getName());
        newBody.setPosition(this.getPosition());
        newBody.setAngle(this.getAngle());
        newBody.setDensity(density);
        this.destroy();
        return newBody;
    }

    @Override
    public String toString() {
        return "ball";
    }
}
