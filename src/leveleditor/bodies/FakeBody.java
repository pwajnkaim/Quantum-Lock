package leveleditor.bodies;

import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public abstract class FakeBody extends StaticBody{
    protected Vec2 size;

    public FakeBody(World world) {
        super(world);
    }

    public Vec2 getSize() {
        return size;
    }

    public FakeBody setSize(Vec2 size) {
        return null;
    }
}
