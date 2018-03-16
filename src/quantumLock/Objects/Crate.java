/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rocketjumper.Objects;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import rocketjumper.Freeze.Freezable;

/**
 *
 * @author pwajn
 */
public class Crate extends DynamicBody implements Freezable{
    float density = 20;
    
    public Crate(World world, Vec2 pos){
        super(world);
        
        makeFixtures(this);
        this.setPosition(pos);
    }

    public Crate(World world, Vec2 pos, float density){
        super(world);

        this.density = density;
        makeFixtures(this);
        this.setPosition(pos);

    }
    
    public Crate(World world) {
        super(world);
        makeFixtures(this);
    }

    @Override
    public void makeFixtures(Body body) {
        SolidFixture fixture = new SolidFixture(body, new BoxShape(0.5f,0.5f), density);
        fixture.setFriction(6.f);
    }
}
