/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rocketjumper.Levels;

import city.cs.engine.BodyImage;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

/**
 *
 * @author pwajn
 */
public class TutorialText extends StaticBody{
    public TutorialText(World world, String image, Vec2 pos, float size) {
        super(world);
        
        this.addImage(new BodyImage(image, size));
        this.setPosition(pos);
    }
}
