package leveleditor.bodies;

import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public class Button extends FakeBody{
    public Button(World world) {
        super(world);
        new SolidFixture(this, new BoxShape(2,2));
        size = new Vec2(2,2);
    }

    public Button(World world, Vec2 size) {
        super(world);
        new SolidFixture(this, new BoxShape(size.x,size.y));
        this.size = size;
    }

    @Override
    public FakeBody setSize(Vec2 size) {
        Button newBody = new Button(this.getWorld(),size);
        newBody.setName(this.getName());
        newBody.setPosition(this.getPosition());
        newBody.setAngle(this.getAngle());
        this.destroy();
        return newBody;
    }

    @Override
    public String toString() {
        return "Button";
    }
}
