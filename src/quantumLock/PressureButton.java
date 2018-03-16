package rocketjumper;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 *
 * @author pwajn
 */
public class PressureButton extends Walker{

    public PressureButton(World world) {
        super(world);

        new SolidFixture(this, new BoxShape(1.95f,0.25f), 50);
        new SolidFixture(this, new BoxShape(0.1f,0.1f, new Vec2(0,-1.85f)), 0);

        setGravityScale(-1);
    }
    
    public PressureButton(World world, float weight) {
        super(world);

        new SolidFixture(this, new BoxShape(1.95f,0.25f), weight);
        new SolidFixture(this, new BoxShape(0.1f,0.1f, new Vec2(0,-1.85f)), 0);

        setGravityScale(-1);
    }
}
