package leveleditor;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;


public class BoxStaticBody extends StaticBody{
    private Vec2 size;

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

    public Vec2 getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "new StaticBody(this, new BoxShape(" +  size.x + "f, " + size.y + "f));";
    }
}
