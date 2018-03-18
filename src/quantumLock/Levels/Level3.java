/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock.Levels;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import quantumLock.*;
import quantumLock.Objects.Ball;
import quantumLock.Objects.Crate;
import quantumLock.Objects.GameKey;
import quantumLock.Objects.Rope;

/**
 *
 * @author pwajn
 */
public class Level3 extends Level{
    Vec2 playerInitialPos = new Vec2(4,-3);

    @Override
    public void initialize(QuantumLock quantumLock) {
        super.initialize(quantumLock);

        //create player character
        player = new PlayerCharacter(this);
        player.setPosition(playerInitialPos);

        //create level bodies
        levelPopulate();
    }

    @Override
    public void levelPopulate() {
        player.setPosition(playerInitialPos);


        StaticBody staticBody8 = new StaticBody(this, new BoxShape(0.5f, 2.5f));
        staticBody8.setPosition(new Vec2(109.5f, 20.5f));
        StaticBody staticBody9 = new StaticBody(this, new BoxShape(9.0f, 0.5f));
        staticBody9.setPosition(new Vec2(101.0f, 23.5f));
        StaticBody staticBody10 = new StaticBody(this, new BoxShape(10.0f, 0.5f));
        staticBody10.setPosition(new Vec2(100.0f, 17.5f));
        StaticBody staticBody11 = new StaticBody(this, new BoxShape(1.0f, 0.5f));
        staticBody11.setPosition(new Vec2(81.5f, 7.5f));
        StaticBody staticBody12 = new StaticBody(this, new BoxShape(0.5f, 6.0f));
        staticBody12.setPosition(new Vec2(91.5f, 29.0f));
        StaticBody staticBody13 = new StaticBody(this, new BoxShape(45.0f, 0.5f));
        staticBody13.setPosition(new Vec2(46.0f, 34.5f));
        StaticBody staticBody14 = new StaticBody(this, new BoxShape(0.5f, 18.0f));
        staticBody14.setPosition(new Vec2(72.5f, 16.0f));
        StaticBody staticBody15 = new StaticBody(this, new BoxShape(10.5f, 0.5f));
        staticBody15.setPosition(new Vec2(61.5f, 9.5f));
        StaticBody staticBody16 = new StaticBody(this, new BoxShape(4.0f, 0.5f));
        staticBody16.setPosition(new Vec2(68.0f, 0.5f));
        SlidingDoor door2 = new SlidingDoor(this, new BoxShape(0.25f, 2.0f), new Vec2(72.5f, 0.0f), new Vec2(72.5f, -4.0f), 0.5f, true);
        addSlidingDoor(door2);
        StaticBody staticBody18 = new StaticBody(this, new BoxShape(0.5f, 12.0f));
        staticBody18.setPosition(new Vec2(91.5f, 5.0f));
        StaticBody staticBody19 = new StaticBody(this, new BoxShape(20.0f, 0.5f));
        staticBody19.setPosition(new Vec2(71.0f, -6.5f));
        StaticBody staticBody20 = new StaticBody(this, new BoxShape(3.0f, 0.5f));
        staticBody20.setPosition(new Vec2(62.5f, 7.25f));
        staticBody20.rotate(-0.785398f);
        StaticBody staticBody21 = new StaticBody(this, new BoxShape(2.0f, 4.0f));
        staticBody21.setPosition(new Vec2(53.0f, 5.0f));
        StaticBody staticBody22 = new StaticBody(this, new BoxShape(0.5f, 2.0f));
        staticBody22.setPosition(new Vec2(64.5f, 3.0f));
        StaticBody staticBody23 = new StaticBody(this, new BoxShape(4.0f, 0.5f));
        staticBody23.setPosition(new Vec2(55.0f, 0.5f));
        StaticBody staticBody24 = new StaticBody(this, new BoxShape(16.0f, 0.5f));
        staticBody24.setPosition(new Vec2(36.0f, -5.5f));
        SlidingDoor door1 = new SlidingDoor(this, new BoxShape(0.25f, 2.0f), new Vec2(50.5f, 1.0f), new Vec2(50.5f, -3.0f), 0.5f, true);
        addSlidingDoor(door1);
        StaticBody staticBody26 = new StaticBody(this, new BoxShape(0.5f, 17.5f));
        staticBody26.setPosition(new Vec2(50.5f, 16.5f));
        StaticBody staticBody27 = new StaticBody(this, new BoxShape(17.0f, 0.5f));
        staticBody27.setPosition(new Vec2(18.0f, -0.5f));
        SlidingDoor door0 = new SlidingDoor(this, new BoxShape(0.25f, 1.5f), new Vec2(35.5f, -0.5f), new Vec2(35.5f, -3.5f), 0.5f, false);
        addSlidingDoor(door0);
        StaticBody staticBody29 = new StaticBody(this, new BoxShape(0.5f, 18.0f));
        staticBody29.setPosition(new Vec2(35.5f, 16.0f));
        StaticBody staticBody30 = new StaticBody(this, new BoxShape(0.5f, 2.5f));
        staticBody30.setPosition(new Vec2(19.5f, -6.5f));
        StaticBody staticBody31 = new StaticBody(this, new BoxShape(7.0f, 0.5f));
        staticBody31.setPosition(new Vec2(7.0f, -5.5f));
        StaticBody staticBody32 = new StaticBody(this, new BoxShape(0.5f, 20.0f));
        staticBody32.setPosition(new Vec2(0.5f, 15.0f));
        StaticBody staticBody33 = new StaticBody(this, new BoxShape(0.5f, 2.0f));
        staticBody33.setPosition(new Vec2(14.5f, -7.0f));
        StaticBody staticBody34 = new StaticBody(this, new BoxShape(3.0f, 0.5f));
        staticBody34.setPosition(new Vec2(17.0f, -9.5f));

        WeightButton button0 = new WeightButton(this, new Vec2(17.0f, -5f), 20, door1);
        button0.connectToDoor(door0);

        new Crate(this, new Vec2(9.5f, -4.5f), 60);

        WeightButton button1 = new WeightButton(this, new Vec2(57f, 4f), 20, door2);

        new Crate(this, new Vec2(78.0f, -5.5f));
        new Crate(this, new Vec2(90.5f, -5.5f));
        new Crate(this, new Vec2(87.5f, -5.5f));
        new Crate(this, new Vec2(83.5f, -5.5f));
        new Crate(this, new Vec2(82.0f, -4.5f));
        new Crate(this, new Vec2(82.5f, -5.5f));
        new Crate(this, new Vec2(81.5f, -5.5f));
        new Crate(this, new Vec2(75.5f, -5.5f));

        LevelDoor exit = new LevelDoor(this);
        exit.setPosition(new Vec2(132f,-17.8f));

        /*// Ground
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
        key3.setPosition(new Vec2(23f,-10f));*/
    }

    @Override
    public void levelReset() {
        //delete everything except for player
        for (StaticBody body : getStaticBodies()) {
            if(!(body instanceof GrabArea)) body.destroy();
        }
        for (DynamicBody body : getDynamicBodies()) {
            if(!(body instanceof PlayerCharacter)) body.destroy();
        }
        player.setLinearVelocity(new Vec2(0,0));
        levelPopulate();
    }
}
