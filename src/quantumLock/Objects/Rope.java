/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rocketjumper.Objects;

import city.cs.engine.*;
import rocketjumper.Freeze.Freezable;

/**
 *
 * @author pwajn
 */
public class Rope extends DynamicBody implements Freezable{
    
    private final float scale = 1;
    private final float length = 0.7f; //0.7f
    
    private final float[][] polygon_list = 
        {{-2.66f,0.4f, -2.6f,0.3f, -2.75f,0.0f},
        {-2.75f,0.0f, -2.6f,-0.3f, -2.66f,-0.4f},
        {-2.6f,-0.3f, -2.285f,-0.45f, -2.66f,-0.4f},
        {-2.285f,-0.45f, -1.97f,-0.31f, -1.83f,-0.48f},
        {-1.85f,-0.02f, -1.97f,-0.31f, -1.83f,-0.48f, length,-0.479f, length,-0.02f},
        {-1.85f,-0.02f, -1.97f,0.29f, -1.847f,0.493f, length,0.479f, length,-0.02f},
        {-2.285f,0.45f, -1.97f,0.29f, -1.847f,0.493f},
        {-2.285f,0.45f, -2.6f,0.3f, -2.66f,0.4f}};
    private final Shape hingeShape = new CircleShape(0.4f*scale, (0.8f+length)*scale, 0.0f);
    
    public Rope(World w){
        super(w);
        
        makeFixtures(this);
        
        //new AttachedImage(this, new BodyImage("sprites/rope.png"), scale, 0f, new Vec2(-0.5f,0));
    }

    @Override
    public void makeFixtures(Body body) {
        for (float[] polygon : polygon_list) {
            for(int i = 0; i < polygon.length; i++) {
                polygon[i] *= scale;
            }
            new SolidFixture(body, new PolygonShape(polygon), 5);
        }
        SolidFixture hingeFixture = new SolidFixture(body, hingeShape, 10);
        hingeFixture.setFriction(0.01f);
    }

    public float getScale() {
        return scale;
    }
    public float getLength() {
        return length;
    }
}
