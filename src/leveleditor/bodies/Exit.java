package leveleditor.bodies;

import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class Exit extends FakeBody {
    public Exit(World world) {
        super(world);
        new SolidFixture(this, new BoxShape(0.9f,1.2f));
        size = new Vec2(0.9f,1.2f);
        setFillColor(new Color(99, 136, 132));
    }

    public Exit(World world, Vec2 size) {
        super(world);
        new SolidFixture(this, new BoxShape(size.x,size.y));
        this.size = size;
        setFillColor(new Color(99, 136, 132));
    }

    @Override
    public FakeBody setSize(Vec2 size) {
        Exit newBody = new Exit(this.getWorld(),size);
        newBody.setName(this.getName());
        newBody.setPosition(this.getPosition());
        newBody.setAngle(this.getAngle());
        this.destroy();
        return newBody;
    }

    @Override
    public String toString() {
        return "Exit";
    }
}
