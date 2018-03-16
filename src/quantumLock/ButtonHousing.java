package quantumLock;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import quantumLock.Levels.Level;

/**
 *
 * @author pwajn
 */
public class ButtonHousing extends StaticBody implements SensorListener{
    SlidingDoor door;

    public ButtonHousing(World world) {
        super(world);

        new SolidFixture(this, new BoxShape(0.1f,0.99f, new Vec2(-2.1f,-0.99f)));
        new SolidFixture(this, new BoxShape(0.1f,0.99f, new Vec2(2.1f,-0.99f)));

        new SolidFixture(this, new BoxShape(2.1f,0.1f, new Vec2(0,-1.89f)));
        Sensor sensor = new Sensor(this, new BoxShape(2.1f,0.05f, new Vec2(0,-1.79f)));

        sensor.addSensorListener(this);
    }

    @Override
    public void beginContact(SensorEvent se) {
        if(se.getContactBody() instanceof PressureButton) {
            System.out.println("button pressed");
            if (door != null) {
                door.open();
            }
        }
    }

    @Override
    public void endContact(SensorEvent se) {
        if(se.getContactBody() instanceof PressureButton) {
            System.out.println("button released");
            if (door != null) {
                door.close();
            }
        }
    }

    public void connectToDoor(SlidingDoor slidingDoor) {
        door = slidingDoor;
    }
}
