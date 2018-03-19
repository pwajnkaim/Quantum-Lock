/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock.Levels;

import city.cs.engine.*;
import quantumLock.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pwajn
 */
public class Level extends World{
    public QuantumLock quantumLock;
    protected WorldView view;
    protected PlayerCharacter player;
    private StepListener stepListener;

    public long startTime;
    public long currentTime;
    public long pauseTime;

    private List<SlidingDoor> slidingDoors = new ArrayList<>();

    public void initialize(QuantumLock quantumLock) {
        this.quantumLock = quantumLock;
        startTime = System.currentTimeMillis();
    }

    public void levelComplete() {
        this.stop();
        quantumLock.showLevelEnd(currentTime);
        //quantumLock.nextLevel();
    }

    public void levelReset() {
        startTime = System.currentTimeMillis();
    }

    public void levelPopulate() {}

    public PlayerCharacter getPlayer(){
        return player;
    }
    
    public void setView(WorldView view) {
        this.view = view;
    }
    public WorldView getView() {
        return view;
    }

    protected void addSlidingDoor(SlidingDoor slidingDoor) {
        slidingDoors.add(slidingDoor);
    }
    public List<SlidingDoor> getSlidingDoors() {
        return slidingDoors;
    }

    public void clearSlidingDoors() {
        slidingDoors.clear();
    }

    public void clear() {
        this.removeStepListener(stepListener);
        player.destroy();
        player = null;
        for (StaticBody body : getStaticBodies()) {
            body.destroy();
            body = null;
        }
        for (DynamicBody body : getDynamicBodies()) {
            body.destroy();
            body = null;
        }
    }
    
    public void pause() {
        stop();
        pauseTime = currentTime;
        quantumLock.showPauseMenu();
    }
    
    public void resume() {
        start();
        startTime += ((System.currentTimeMillis()-startTime)-pauseTime); //make up for paused time
        quantumLock.hidePauseMenu();
    }

    @Override
    public void addStepListener(StepListener stepListener) {
        this.stepListener = stepListener;
        super.addStepListener(stepListener);
    }
}
