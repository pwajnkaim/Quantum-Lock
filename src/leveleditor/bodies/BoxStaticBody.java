package leveleditor.bodies;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;


public class BoxStaticBody extends FakeBody{
    public BoxStaticBody(World world) {
        super(world);
        new SolidFixture(this, new BoxShape(0.5f,0.5f));
        size = new Vec2(0.5f,0.5f);
    }

    public BoxStaticBody(World world, Vec2 size) {
        super(world);
        new SolidFixture(this, new BoxShape(size.x,size.y));
        this.size = size;
    }

    @Override
    public void setSize(Vec2 size) {
        BoxStaticBody newBody = new BoxStaticBody(this.getWorld(),size);
        newBody.setName(this.getName());
        newBody.setPosition(this.getPosition());
        newBody.setAngle(this.getAngle());
        this.destroy();
    }

    @Override
    public String toString() {
        return "new StaticBody(this, new BoxShape(" +  size.x + "f, " + size.y + "f));";
    }
}
