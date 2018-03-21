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
public class Level2 extends Level{
    Vec2 playerInitialPos = new Vec2(4,-3);

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void levelPopulate() {
        player.setPosition(playerInitialPos);

        StaticBody staticBody0 = new StaticBody(this, new BoxShape(3.0f, 0.5f));
        staticBody0.setPosition(new Vec2(112.0f, -23.5f));
        StaticBody staticBody1 = new StaticBody(this, new BoxShape(0.5f, 6.5f));
        staticBody1.setPosition(new Vec2(80.5f, -1.5f));
        StaticBody staticBody2 = new StaticBody(this, new BoxShape(0.5f, 12.5f));
        staticBody2.setPosition(new Vec2(90.5f, -7.5f));
        StaticBody staticBody3 = new StaticBody(this, new BoxShape(4.5f, 0.5f));
        staticBody3.setPosition(new Vec2(129.5f, -14.5f));
        StaticBody staticBody4 = new StaticBody(this, new BoxShape(0.5f, 2.5f));
        staticBody4.setPosition(new Vec2(134.5f, -16.5f));
        StaticBody staticBody5 = new StaticBody(this, new BoxShape(10.0f, 0.5f));
        staticBody5.setPosition(new Vec2(125.0f, -19.5f));
        StaticBody staticBody6 = new StaticBody(this, new BoxShape(0.5f, 7.5f));
        staticBody6.setPosition(new Vec2(124.5f, -7.5f));
        StaticBody staticBody7 = new StaticBody(this, new BoxShape(16.0f, 0.5f));
        staticBody7.setPosition(new Vec2(109.0f, 0.5f));
        StaticBody staticBody8 = new StaticBody(this, new BoxShape(0.5f, 2.0f));
        staticBody8.setPosition(new Vec2(114.5f, -21.0f));
        StaticBody staticBody9 = new StaticBody(this, new BoxShape(0.5f, 2.0f));
        staticBody9.setPosition(new Vec2(109.5f, -21.0f));
        StaticBody staticBody10 = new StaticBody(this, new BoxShape(9.0f, 0.5f));
        staticBody10.setPosition(new Vec2(100.0f, -19.5f));
        StaticBody staticBody11 = new StaticBody(this, new BoxShape(0.5f, 8.0f));
        staticBody11.setPosition(new Vec2(93.5f, -8.0f));
        StaticBody staticBody12 = new StaticBody(this, new BoxShape(0.5f, 17.0f));
        staticBody12.setPosition(new Vec2(95.5f, 18.0f));
        StaticBody staticBody13 = new StaticBody(this, new BoxShape(47.0f, 0.5f));
        staticBody13.setPosition(new Vec2(48.0f, 34.5f));
        StaticBody staticBody14 = new StaticBody(this, new BoxShape(13.0f, 0.5f));
        staticBody14.setPosition(new Vec2(68.0f, -8.5f));
        StaticBody staticBody15 = new StaticBody(this, new BoxShape(6.0f, 0.5f));
        staticBody15.setPosition(new Vec2(85.0f, 5.5f));
        StaticBody staticBody16 = new StaticBody(this, new BoxShape(0.5f, 4.0f));
        staticBody16.setPosition(new Vec2(55.5f, -4.0f));
        StaticBody staticBody17 = new StaticBody(this, new BoxShape(8.0f, 0.5f));
        staticBody17.setPosition(new Vec2(49.0f, 0.5f));
        StaticBody staticBody18 = new StaticBody(this, new BoxShape(0.5f, 4.0f));
        staticBody18.setPosition(new Vec2(40.5f, -2.0f));
        StaticBody staticBody19 = new StaticBody(this, new BoxShape(0.5f, 20.0f));
        staticBody19.setPosition(new Vec2(0.5f, 15.0f));
        StaticBody staticBody20 = new StaticBody(this, new BoxShape(20.0f, 0.5f));
        staticBody20.setPosition(new Vec2(20.0f, -5.5f));
        SlidingDoor door0 = new SlidingDoor(this, new BoxShape(0.25f, 2.0f), new Vec2(124.5f,-13), new Vec2(124.5f,-17), 0.08f, true);
        addSlidingDoor(door0);

        WeightButton button0 = new WeightButton(this, new Vec2(112,-19), door0);
        
        new TutorialText(this, "sprites/tutorialText/freeze.png", new Vec2(12,10), 7);
        new TutorialText(this, "sprites/tutorialText/unfreeze.png", new Vec2(30,10), 3);

        new Crate(this, new Vec2(13.5f, -4.5f));
        new Crate(this, new Vec2(34.5f, -4.5f));
        new Crate(this, new Vec2(23.5f, -4.5f));
        new Crate(this, new Vec2(22.5f, -4.5f));
        new Crate(this, new Vec2(23f, -3.5f));

        for(int i = 0; i < 10; i++) {
            new Ball(this, new Vec2((i*2)+58,-7), new CircleShape(0.6f));
            new Ball(this, new Vec2((i*2)+59,-7));
            new Ball(this, new Vec2((i*2)+58,-7), new CircleShape(0.7f));
            new Ball(this, new Vec2((i*2)+58,-7), new CircleShape(0.3f));
            new Ball(this, new Vec2((i*2)+58,-7), new CircleShape(0.4f));
            new Ball(this, new Vec2((i*2)+59,-7));
            new Ball(this, new Vec2((i*2)+59,-7));
            new Ball(this, new Vec2((i*2)+59,-7));

        }

        new Crate(this, new Vec2(102.5f, -18.5f));

        LevelDoor exit = new LevelDoor(this);
        exit.setPosition(new Vec2(132f,-17.8f));
    }
}
