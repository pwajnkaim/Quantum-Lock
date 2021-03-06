package leveleditor.bodies;

import city.cs.engine.BoxShape;
import city.cs.engine.SolidFixture;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class Player extends FakeBody {
    private boolean gunDisabled = false;
    public Player(World world) {
        super(world);
        new SolidFixture(this, new BoxShape(0.6f,0.9f));
        size = new Vec2(0.6f,0.9f);
        setFillColor(new Color(95, 136, 91));
    }

    public Player(World world, Vec2 size) {
        super(world);
        new SolidFixture(this, new BoxShape(size.x,size.y));
        this.size = size;
        setFillColor(new Color(95, 136, 91));
    }

    public void setGunDisabled(boolean value) {
        this.gunDisabled = value;
    }
    public boolean isGunDisabled() {
        return gunDisabled;
    }

    @Override
    public FakeBody setSize(Vec2 size) {
        Player newBody = new Player(this.getWorld(),size);
        newBody.setName(this.getName());
        newBody.setPosition(this.getPosition());
        newBody.setAngle(this.getAngle());
        this.destroy();
        return newBody;
    }

    @Override
    public String toString() {
        return "Player";
    }
}
