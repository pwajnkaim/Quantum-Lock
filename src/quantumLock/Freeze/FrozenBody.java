/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rocketjumper.Freeze;

import city.cs.engine.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.jbox2d.common.Vec2;

/**
 *
 * @author pwajn
 */
public class FrozenBody extends StaticBody{
    //old refers to the removed DynamicBody version of the frozen body
    private final Class oldClass; //class of the old object
    private final Vec2 oldLinearVel; //old object's LinearVelocity
    private final float oldAngularVel; //old object's AngularVelocity
    public FrozenBody(World world, DynamicBody oldBody) {
        super(world);
        oldClass = oldBody.getClass();
        oldLinearVel = oldBody.getLinearVelocity();
        oldAngularVel = oldBody.getAngularVelocity();
        
        ((Freezable)oldBody).makeFixtures(this);
        this.setPosition(oldBody.getPosition());
        this.rotate(oldBody.getAngle());
        oldBody.destroy();
    }
    
    public void unfreeze() {
        try {
            Constructor constructor = oldClass.getConstructor(World.class); //extracting the constructor from a Class object
            Object object = constructor.newInstance(this.getWorld());
            DynamicBody body = (DynamicBody)object;
            body.setPosition(this.getPosition());
            body.setLinearVelocity(oldLinearVel);
            body.rotate(this.getAngle());
            body.setAngularVelocity(oldAngularVel);
            this.destroy();
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            System.out.println("Something bad happened\n" + e);
        }
    }
}
