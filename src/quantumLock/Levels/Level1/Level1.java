/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock.Levels.Level1;

import quantumLock.*;
import quantumLock.Levels.Level;
import quantumLock.Objects.Crate;
import quantumLock.Levels.TutorialText;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;


/**
 *
 * @author pwajn
 */
public class Level1 extends Level{
    @Override
    public void initialize(QuantumLock quantumLock) {
        super.initialize(quantumLock);

        StaticBody staticBody0 = new StaticBody(this, new BoxShape(15.0f, 0.5f));
        staticBody0.setPosition(new Vec2(-5.0f, -5.5f));
        StaticBody staticBody1 = new StaticBody(this, new BoxShape(0.5f, 2.0f));
        staticBody1.setPosition(new Vec2(9.5f, -3.0f));
        StaticBody staticBody2 = new StaticBody(this, new BoxShape(6.0f, 0.5f));
        staticBody2.setPosition(new Vec2(16.0f, -2.5f));
        StaticBody staticBody3 = new StaticBody(this, new BoxShape(5.0f, 0.5f));
        staticBody3.setPosition(new Vec2(26.0f, -1.5f));
        StaticBody staticBody4 = new StaticBody(this, new BoxShape(0.5f, 2.5f));
        staticBody4.setPosition(new Vec2(30.5f, 1.5f));
        StaticBody staticBody5 = new StaticBody(this, new BoxShape(9.5f, 0.5f));
        staticBody5.setPosition(new Vec2(40.5f, 3.5f));
        StaticBody staticBody6 = new StaticBody(this, new BoxShape(1.5f, 0.5f));
        staticBody6.setPosition(new Vec2(55.5f, 3.5f));
        SlidingDoor door0 = new SlidingDoor(this, new BoxShape(2.0f, 0.25f), new Vec2(63,3.5f), new Vec2(59,3.5f), 0.02f, true); //door
        addSlidingDoor(door0);
        door0.stayOpen(true);
        StaticBody staticBody8 = new StaticBody(this, new BoxShape(0.5f, 10.0f));
        staticBody8.setPosition(new Vec2(63.5f, 14.0f));
        StaticBody staticBody9 = new StaticBody(this, new BoxShape(41.0f, 0.5f));
        staticBody9.setPosition(new Vec2(22.0f, 23.5f));
        StaticBody staticBody10 = new StaticBody(this, new BoxShape(0.5f, 14.5f));
        staticBody10.setPosition(new Vec2(-19.5f, 9.5f));
        StaticBody staticBody11 = new StaticBody(this, new BoxShape(0.5f, 9.5f));
        staticBody11.setPosition(new Vec2(48.5f, -6.5f));
        StaticBody staticBody12 = new StaticBody(this, new BoxShape(0.5f, 2.0f));
        staticBody12.setPosition(new Vec2(55.5f, 1.0f));
        StaticBody staticBody13 = new StaticBody(this, new BoxShape(3.0f, 0.5f));
        staticBody13.setPosition(new Vec2(52.0f, -0.5f));
        StaticBody staticBody14 = new StaticBody(this, new BoxShape(14.0f, 0.5f));
        staticBody14.setPosition(new Vec2(75.0f, 3.5f));
        StaticBody staticBody15 = new StaticBody(this, new BoxShape(13.0f, 0.5f));
        staticBody15.setPosition(new Vec2(62.0f, -15.5f));
        StaticBody staticBody16 = new StaticBody(this, new BoxShape(0.5f, 7.0f));
        staticBody16.setPosition(new Vec2(88.5f, -4.0f));
        SlidingDoor door1 = new SlidingDoor(this, new BoxShape(0.25f, 2.0f), new Vec2(88.5f,-9), new Vec2(88.5f,-13), 0.5f, true); //door
        addSlidingDoor(door1);
        StaticBody staticBody18 = new StaticBody(this, new BoxShape(14.0f, 0.5f));
        staticBody18.setPosition(new Vec2(93.0f, -15.5f));
        StaticBody staticBody19 = new StaticBody(this, new BoxShape(0.5f, 3.5f));
        staticBody19.setPosition(new Vec2(106.5f, -11.5f));
        StaticBody staticBody20 = new StaticBody(this, new BoxShape(8.5f, 0.5f));
        staticBody20.setPosition(new Vec2(97.5f, -8.5f));

        new Crate(this, new Vec2(14,2));

        WeightButton button0 = new WeightButton(this, new Vec2(52,4));
        button0.housing.connectToDoor(door0);
        WeightButton button1 = new WeightButton(this, new Vec2(77,-15), 20);
        button1.housing.connectToDoor(door1);

        //spawn crates
        for (int i = 5; i > 0; i--) {
            for (int v = 0; v < i; v++) {
                Crate crate = new Crate(this, new Vec2((1f*(i+0.5f)+48.5f)-v*0.5f, (1*(v+0.5f)) -13f), 40);
            }
        }
        
        new TutorialText(this, "sprites/tutorialText/jump.png", new Vec2(-8,0), 5);
        new TutorialText(this, "sprites/tutorialText/hold.png", new Vec2(15.3f,0), 3);
        new TutorialText(this, "sprites/tutorialText/throw.png", new Vec2(26,7), 5);
        new TutorialText(this, "sprites/tutorialText/stuck.png", new Vec2(39.1f,0.5f), 3);
        new TutorialText(this, "sprites/tutorialText/button1.png", new Vec2(52f,10f), 5);
        new TutorialText(this, "sprites/tutorialText/button2.png", new Vec2(70f,-5f), 4.5f);
        
        LevelDoor exit = new LevelDoor(this);
        exit.setPosition(new Vec2(103f,-13.68f));
        
        //new TutorialText(this, "sprites/tutorialText/jump.png", new Vec2(-8,0));

        //rope anchor
        /*Body ropeAnchor = new StaticBody(this, new CircleShape(0.4f));
        ropeAnchor.setPosition(new Vec2(-0.5f, 9f));
        ((SolidFixture)ropeAnchor.getFixtureList().get(0)).setFriction(0.001f); //set anchor friction
        //spawn rope
        for (int i = 0; i < 10; i++) {
            Rope rope = new Rope(this);
            rope.setPosition(new Vec2((i*3.75f*rope.getScale())+2f, 9));
        }*/

        //create player character
        player = new PlayerCharacter(this);
        //player.setPosition(new Vec2(-16,0.5f));
        player.setPosition(new Vec2(100f,-10));
        player.disableGun();
    }
}
