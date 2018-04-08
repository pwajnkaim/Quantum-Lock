/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import quantumLock.Freeze.FreezeBall;
import quantumLock.Levels.Level;

/**
 *
 * @author pwajn
 */
public class PlayerCharacter extends Walker implements CollisionListener{
    
    float speed;
    float jumpStrength;
    int jumps;
    boolean movingLeft;
    boolean movingRight;

    private boolean enabledGun = true;
    
    final private SolidFixture legs;
    GrabArea grabArea;
    DynamicBody grabbedBody;
    
    public PlayerCharacter(World w) {
        super(w);
        
        addCollisionListener(this);
        
        SolidFixture torso = new SolidFixture(this, new BoxShape(0.6f,0.9f, new Vec2(0,0.01f)), 20);
        torso.setFriction(0);
        
        PolygonShape legShape = new PolygonShape(-0.56f,-0.89f, 0.56f,-0.89f, 0.55f,-0.9f, -0.55f,-0.9f); 
        legs = new SolidFixture(this, legShape, 20);
        legs.setFriction(5);
        legs.setRestitution(0);
        
        grabArea = new GrabArea(this);
        
        addImage(new BodyImage("sprites/player.png", 2.0f));
        
        setGravityScale(4);
        
        speed = 6;
        jumpStrength = 800;
        movingLeft = false;
        movingRight = false;
        jumps = 1;
        grabbedBody = null;
    }
    
    public void fire() {
        if (enabledGun) {
            FreezeBall freezeBall = new FreezeBall(this.getWorld());
            Vec2 playerPos = this.getPosition();
            Vec2 aimPos = grabArea.getPosition();
            Vec2 delta = aimPos.sub(playerPos); delta.normalize();
            freezeBall.setPosition(playerPos.add(delta.mul(1.5f)));
            freezeBall.setLinearVelocity(delta.mul(50));
        }
    }

    public void grab(){
        for (DynamicBody body : this.getWorld().getDynamicBodies()) {
            if(!(body instanceof WeightButton.Button)) {
                if (body.intersects(grabArea)) grabbedBody = body;
            }
        }
    }
    
    public void throwGrabbed() {
        Vec2 playerPos = this.getPosition();
        Vec2 grabPos = grabArea.getPosition();
        grabbedBody.applyImpulse((grabPos.sub(playerPos)).mul(grabbedBody.getMass()*8f));
        grabbedBody = null;
    }
    
    public void dropGrabbed() {
        grabbedBody = null;
    }

    public void setGun(boolean value) {
        enabledGun = value;
    }

    @Override
    public void collide(CollisionEvent ce) {
        if (ce.getReportingFixture() == legs && ce.getOtherBody() != grabbedBody) { //if legs touch the ground
            jumps = 1;
        }
    }
}