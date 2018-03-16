/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock.Levels;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import quantumLock.Objects.Crate;
import quantumLock.Objects.GameKey;
import quantumLock.Objects.Rope;
import quantumLock.PlayerCharacter;
import quantumLock.QuantumLock;

/**
 *
 * @author pwajn
 */
public class Level2 extends Level{
    @Override
    public void initialize(QuantumLock quantumLock) {
        super.initialize(quantumLock);
        // Ground
        Body ground = new StaticBody(this, new BoxShape(25, 0.5f));
        ground.setPosition(new Vec2(0, -11.5f));

        // Left Wall
        Body leftWall = new StaticBody(this, new BoxShape(0.5f, 6));
        leftWall.setPosition(new Vec2(-25f, -6));
        // Right Wall
        Body platform4 = new StaticBody(this, new BoxShape(0.5f, 6));
        platform4.setPosition(new Vec2(25f, -6));
        // Platform 1
        Body platform1 = new StaticBody(this, new BoxShape(4, 0.5f));
        platform1.setPosition(new Vec2(-9, 5.5f));
        // Platform 2
        Body platform2 = new StaticBody(this, new BoxShape(4, 0.5f));
        platform2.setPosition(new Vec2(11f, 5.5f));
        platform2.setAngleDegrees(15);

        //rope anchor
        Body ropeAnchor = new StaticBody(this, new CircleShape(0.4f));
        ropeAnchor.setPosition(new Vec2(-0.5f, 9f));
        ((SolidFixture)ropeAnchor.getFixtureList().get(0)).setFriction(0.001f); //set anchor friction
        //spawn rope
        for (int i = 0; i < 10; i++) {
            Rope rope = new Rope(this);
            rope.setPosition(new Vec2((i*3.75f*rope.getScale())+2f, 9));
        }

        //spawn keys
        GameKey key1 = new GameKey(this);
        key1.setPosition(new Vec2(-14f,-10f));

        GameKey key2 = new GameKey(this);
        key2.setPosition(new Vec2(-10f,7f));

        GameKey key3 = new GameKey(this);
        key3.setPosition(new Vec2(23f,-10f));

        //create player character
        player = new PlayerCharacter(this);
        player.setPosition(new Vec2(8,0.5f));
    }
}
