package leveleditor;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import city.cs.engine.UserView;
import org.jbox2d.common.Vec2;

public class EditorStepListener implements StepListener{
    private float scrollSpeed;

    public EditorStepListener() {
        scrollSpeed = 0.3f;
    }

    @Override
    public void preStep(StepEvent stepEvent) {
        if (LevelEditor.movingUp) {
            LevelEditor.view.setCentre(new Vec2(LevelEditor.view.getCentre().x,LevelEditor.view.getCentre().y+scrollSpeed));
        }
        if(LevelEditor.movingLeft) {
            LevelEditor.view.setCentre(new Vec2(LevelEditor.view.getCentre().x-scrollSpeed,LevelEditor.view.getCentre().y));
        }
        if(LevelEditor.movingDown) {
            LevelEditor.view.setCentre(new Vec2(LevelEditor.view.getCentre().x,LevelEditor.view.getCentre().y-scrollSpeed));
        }
        if(LevelEditor.movingRight) {
            LevelEditor.view.setCentre(new Vec2(LevelEditor.view.getCentre().x+scrollSpeed,LevelEditor.view.getCentre().y));
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }
}
