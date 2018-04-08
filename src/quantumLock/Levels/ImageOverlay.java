package quantumLock.Levels;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageOverlay extends StaticBody {
    ImageOverlay(World world, File file) {
        super(world);
        new GhostlyFixture(this, new BoxShape(0.5f,0.5f));
        try {
            addImage(new BodyImage(file.getPath(), ImageIO.read(file).getHeight()/20));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPosition(new Vec2(0,0));
    }
}
