/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock;

import city.cs.engine.BodyImage;
import city.cs.engine.BoxShape;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SolidFixture;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import quantumLock.Levels.Level;

/**
 *
 * @author pwajn
 */
public class LevelDoor extends StaticBody implements CollisionListener{
    public LevelDoor(World world){
        super(world);
        
        new SolidFixture(this, new BoxShape(0.9f,1.2f));
        addImage(new BodyImage("sprites/exit.png",2.5f));
        
        addCollisionListener(this);
    }
    
    
    

    @Override
    public void collide(CollisionEvent ce) {
        if(ce.getOtherBody() instanceof PlayerCharacter) {
            ((Level)ce.getOtherBody().getWorld()).levelComplete(); //finished level
        }
    }
}
