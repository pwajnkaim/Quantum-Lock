package quantumLock.Objects;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import quantumLock.Freeze.Freezable;

import java.util.ArrayList;
import java.util.List;

public class Ball extends DynamicBody implements Freezable {
    private float density = 1f;
    private CircleShape circleShape;

    public Ball(World world){
        this(world, new Vec2(0,0));
    }

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

    @Override
    public List<Object> getExtraInfo() {
        List info = new ArrayList<Object>();
        info.add(circleShape);
        info.add(density);
        return info;
    }

    @Override
    public void setExtraInfo(List<Object> info) {
        circleShape = (CircleShape)info.get(0);
        density = (Float)info.get(1);
    }
}
