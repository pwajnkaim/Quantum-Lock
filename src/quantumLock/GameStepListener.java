/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import city.cs.engine.WorldView;
import java.awt.Point;
import org.jbox2d.common.Vec2;
import quantumLock.Levels.Level;

/**
 *
 * @author pwajn
 */
public class GameStepListener implements StepListener {
    final private WorldView view;
    final private PlayerCharacter player;
    final private Level world;
    
    GameStepListener(PlayerCharacter player) {
        this.player = player;
        view = ((Level)player.getWorld()).getView();
        world = ((Level)view.getWorld());
    }
    
    private  Vec2 offset = new Vec2(0,0);
    @Override
    public void preStep(StepEvent se) {
       

        for(SlidingDoor door : world.getSlidingDoors()){
            door.moveDoor();
        }
        
        Point mouseRaw = view.getMousePosition();
        if (mouseRaw != null) { //update grab area position
            Vec2 mousePos = view.viewToWorld(mouseRaw);
            Vec2 playerPos = player.getPosition();
            Vec2 delta = mousePos.sub(playerPos);
            delta.normalize();
            offset = delta.mul(2f);
        }
        player.grabArea.setPosition(player.getPosition().add(offset));
        
        //if player is holding an object
        if(player.grabbedBody != null) {
            Vec2 bodyPos = player.grabbedBody.getPosition();
            Vec2 grabPos = player.grabArea.getPosition();
            Vec2 delta = grabPos.sub(bodyPos);
            if(delta.length() > 2) {
                player.grabbedBody = null; //stop holding if too far away
            } else {
                player.grabbedBody.setLinearVelocity(delta.mul(10));
            }
        }
        
        Vec2 current_vel = player.getLinearVelocity();
        if(player.jumps < 1) {
            if(current_vel.x > 0) {
                player.setLinearVelocity(new Vec2(current_vel.x-0.1f, current_vel.y));
            } else if(current_vel.x < 0) {
                player.setLinearVelocity(new Vec2(current_vel.x+0.1f, current_vel.y));
            }
        }
        if(player.movingLeft) {
            player.setLinearVelocity(new Vec2(-player.speed, current_vel.y));
        }
        if(player.movingRight) {
            player.setLinearVelocity(new Vec2(player.speed, current_vel.y));
        }
        
        view.setCentre(new Vec2(player.getPosition().x, player.getPosition().y+5));
    }

    @Override
    public void postStep(StepEvent se) {
        world.currentTime = System.currentTimeMillis() - world.startTime;
        world.quantumLock.updateTimer(world.currentTime);
    }
    
}
