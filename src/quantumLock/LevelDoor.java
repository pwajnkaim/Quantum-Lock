/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock;

import city.cs.engine.*;
import quantumLock.Levels.Level;

/**
 *
 * @author pwajn
 */
public class LevelDoor extends StaticBody implements SensorListener{
    Level world;
    public LevelDoor(World world){
        super(world);
        this.world = (Level)world;
        
        Sensor sensor = new Sensor(this, new BoxShape(0.9f,1.2f));
        addImage(new BodyImage("sprites/exit.png",2.5f));
        
        sensor.addSensorListener(this);
    }

    @Override
    public void beginContact(SensorEvent se) {
        if(se.getContactBody() instanceof PlayerCharacter) {
            se.getContactBody().destroy(); //strange behaviour if not destroyed
            world.levelComplete(); //finished level
        }
    }

    @Override
    public void endContact(SensorEvent sensorEvent) {

    }
}
