/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock.Objects;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import quantumLock.Freeze.Freezable;

import java.util.ArrayList;
import java.util.List;

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
    }

    @Override
    public void makeFixtures(Body body) {
        SolidFixture fixture = new SolidFixture(body, new BoxShape(0.5f,0.5f), density);
        if(body instanceof DynamicBody) {
            fixture.setFriction(6.f);
        }
    }

    @Override
    public List<Object> getExtraInfo() {
        List info = new ArrayList<Object>();
        info.add(density);
        return info;
    }

    @Override
    public void setExtraInfo(List<Object> info) {
        density = (Float)info.get(0);
    }
}
