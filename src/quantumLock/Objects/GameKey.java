/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rocketjumper.Objects;

import city.cs.engine.*;
import rocketjumper.PlayerCharacter;

/**
 *
 * @author pwajn
 */
public class GameKey extends DynamicBody implements CollisionListener{

    public GameKey(World w) {
        super(w);
        
        addCollisionListener(this);

        new SolidFixture(this, new CircleShape(0.5f, 0.45f,0f), 20);
        Shape polygonShape = new PolygonShape(-1.0f, 0.2f, 0.0f, 0.2f, 0.0f, -0.247f, -1.0f, 0f);
        new SolidFixture(this, polygonShape, 20);
        addImage(new BodyImage("sprites/key.png"));
    }

    @Override
    public void collide(CollisionEvent ce) {
        if (ce.getOtherBody() instanceof PlayerCharacter) {
            ((PlayerCharacter)ce.getOtherBody()).addKey();
            System.out.println("Well done, you got a key!");
            System.out.println("Key count: " + ((PlayerCharacter)ce.getOtherBody()).getKeysCollected());
            this.destroy();
        }
    }
}
