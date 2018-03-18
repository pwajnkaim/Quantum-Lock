package quantumLock.Objects;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import quantumLock.Freeze.Freezable;

public class Ball extends DynamicBody implements Freezable {
    private float density = 0.5f;
    private CircleShape circleShape;

    public Ball(World world, Vec2 pos){
        super(world);
        this.circleShape = new CircleShape(0.5f);
        makeFixtures(this);
        this.setPosition(pos);
    }

    public Ball(World world, Vec2 pos, float density){
        super(world);
        this.circleShape = new CircleShape(0.5f);
        this.density = density;
        makeFixtures(this);
        this.setPosition(pos);
    }

    public Ball(World world, Vec2 pos, CircleShape circleShape){
        super(world);
        this.circleShape = circleShape;
        makeFixtures(this);
        this.setPosition(pos);
    }

    public Ball(World world, Vec2 pos, CircleShape circleShape, float density){
        super(world);
        this.circleShape = circleShape;
        this.density = density;
        makeFixtures(this);
        this.setPosition(pos);
    }

    @Override
    public void makeFixtures(Body body) {
        SolidFixture fixture = new SolidFixture(body, circleShape, density);
        fixture.setFriction(6.f);
    }
}
