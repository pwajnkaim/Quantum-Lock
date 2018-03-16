/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock.Freeze;

import quantumLock.DestructionTimer;
import city.cs.engine.*;
import javax.swing.Timer;

/**
 *
 * @author pwajn
 */
public class FreezeBall extends DynamicBody implements CollisionListener{
    private World world;

    public FreezeBall(World world){
        super(world);
        this.world = world;
        
        this.setBullet(true);
        this.setGravityScale(0);
        new SolidFixture(this, new CircleShape(0.2f), 0.1f);
        
        this.addCollisionListener(this);

        Timer timer = new Timer(2000, new DestructionTimer(this));
        timer.start();
    }

    @Override
    public void collide(CollisionEvent ce) {
        if(ce.getOtherBody() instanceof Freezable){
            new FrozenBody(world, (DynamicBody)ce.getOtherBody());
            this.destroy();
        } else if(ce.getOtherBody() instanceof FrozenBody){
            ((FrozenBody)ce.getOtherBody()).unfreeze();
            this.destroy();
        } else {
            this.destroy();
        }
    }
}
