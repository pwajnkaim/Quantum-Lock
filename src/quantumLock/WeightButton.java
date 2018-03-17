package quantumLock;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 *
 * @author pwajn
 */
public class WeightButton{
    private Button button;
    private Housing housing;

    public WeightButton(World world, Vec2 pos) {
        button = new Button(world);
        init(world,pos,null);
    }

    public WeightButton(World world, Vec2 pos, float buttonDensity) {
        button = new Button(world, buttonDensity);
        init(world,pos,null);
    }

    public WeightButton(World world, Vec2 pos, SlidingDoor door) {
        button = new Button(world);
        init(world,pos,door);
    }

    public WeightButton(World world, Vec2 pos, float buttonDensity, SlidingDoor door) {
        button = new Button(world, buttonDensity);
        init(world,pos,door);
    }

    private void init(World world, Vec2 pos, SlidingDoor door) {
        button.setPosition(pos.sub(new Vec2(0,0.5f))); //button is 0.5 lower than housing
        housing = new Housing(world);
        housing.setPosition(pos);
        if(door != null) connectToDoor(door);
    }

    public void connectToDoor(SlidingDoor door) {
        housing.connectToDoor(door);
    }

    class Button extends Walker {

        private Button(World world, float density) {
            super(world);

            SolidFixture leftEnd = new SolidFixture(this, new BoxShape(0.05f, 0.25f, new Vec2(-1.9f, 0)), 0);
            SolidFixture rightEnd = new SolidFixture(this, new BoxShape(0.05f, 0.25f, new Vec2(1.9f, 0)), 0);
            new SolidFixture(this, new BoxShape(1.95f, 0.25f), density);
            new SolidFixture(this, new BoxShape(0.1f, 0.1f, new Vec2(0, -1.85f)), 0);
            leftEnd.setFriction(0);
            rightEnd.setFriction(0);

            setGravityScale(-1);
        }

        private Button(World world) {
            this(world, 50); //default density
        }
    }

    public class Housing extends StaticBody implements SensorListener{
        private SlidingDoor door;

        private Housing(World world) {
            super(world);

            new SolidFixture(this, new BoxShape(0.1f,0.99f, new Vec2(-2.1f,-0.99f)));// Left Wall
            new SolidFixture(this, new BoxShape(0.1f,0.99f, new Vec2(2.1f,-0.99f)));// Right Wall

            new SolidFixture(this, new BoxShape(2.1f,0.1f, new Vec2(0,-1.89f)));// Floor
            Sensor sensor = new Sensor(this, new BoxShape(2.1f,0.05f, new Vec2(0,-1.79f)));

            sensor.addSensorListener(this);
        }

        @Override
        public void beginContact(SensorEvent se) {
            if(se.getContactBody() instanceof Button) {
                if (door != null) door.open();
            }
        }
        @Override
        public void endContact(SensorEvent se) {
            if(se.getContactBody() instanceof Button) {
                if (door != null) door.close();
            }
        }

        private void connectToDoor(SlidingDoor slidingDoor) {
            door = slidingDoor;
        }
    }
}
