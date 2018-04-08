package leveleditor.bodies;

import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class Crate extends FakeBody implements Density {
    private float density = 20;
    public Crate(World world) {
        super(world);
        new SolidFixture(this, new BoxShape(0.5f,0.5f));
        size = new Vec2(0.5f,0.5f);
        setFillColor(new Color(110, 79, 45));
    }

    public Crate(World world, Vec2 size) {
        super(world);
        new SolidFixture(this, new BoxShape(size.x,size.y));
        this.size = size;
        setFillColor(new Color(110, 79, 45));
    }

    public void setDensity(float density) {
        this.density = density;
    }
    public float getDensity() {
        return density;
    }

    @Override
    public FakeBody setSize(Vec2 size) {
        Crate newBody = new Crate(this.getWorld(),size);
        newBody.setName(this.getName());
        newBody.setPosition(this.getPosition());
        newBody.setAngle(this.getAngle());
        newBody.setDensity(density);
        this.destroy();
        return newBody;
    }

    @Override
    public String toString() {
        return "crate";
    }
}
