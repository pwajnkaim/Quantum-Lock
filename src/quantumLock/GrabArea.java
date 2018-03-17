/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock;

import city.cs.engine.*;

/**
 *
 * @author pwajn
 */
public class GrabArea extends StaticBody{
    
    GrabArea(PlayerCharacter player) {
        super(player.getWorld());
        
        new GhostlyFixture(this, new CircleShape(0.3f));
        addImage(new BodyImage("sprites/crosshair.png", 0.5f));
    }
}
