package leveleditor.bodies;

import city.cs.engine.CircleShape;
import city.cs.engine.SolidFixture;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public class CircleStaticBody extends FakeBody {
    public CircleStaticBody(World world) {
        super(world);
        new SolidFixture(this, new CircleShape(0.5f));
        size = new Vec2(0.5f, 0.5f);
    }

    public CircleStaticBody(World world, Vec2 size) {
        super(world);
        new SolidFixture(this, new CircleShape(size.x));
        this.size = size;
    }

    @Override
    public FakeBody setSize(Vec2 size) {
        CircleStaticBody newBody = new CircleStaticBody(this.getWorld(), size);
        newBody.setName(this.getName());
        newBody.setPosition(this.getPosition());
        newBody.setAngle(this.getAngle());
        this.destroy();
        return newBody;
    }

    @Override
    public String toString() {
        return "CircleStaticBody";
    }
}
