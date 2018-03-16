import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import city.cs.engine.UserView;
import org.jbox2d.common.Vec2;

public class EditorStepListener implements StepListener{
    private float scrollSpeed;
    private LevelEditor levelEditor;
    private UserView view;

    public EditorStepListener(LevelEditor levelEditor, UserView view) {
        this.levelEditor = levelEditor;
        this.view = view;
        scrollSpeed = 0.3f;
    }

    @Override
    public void preStep(StepEvent stepEvent) {
        if (levelEditor.movingUp) {
            view.setCentre(new Vec2(view.getCentre().x,view.getCentre().y+scrollSpeed));
        }
        if(levelEditor.movingLeft) {
            view.setCentre(new Vec2(view.getCentre().x-scrollSpeed,view.getCentre().y));
        }
        if(levelEditor.movingDown) {
            view.setCentre(new Vec2(view.getCentre().x,view.getCentre().y-scrollSpeed));
        }
        if(levelEditor.movingRight) {
            view.setCentre(new Vec2(view.getCentre().x+scrollSpeed,view.getCentre().y));
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }
}
