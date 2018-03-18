/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock.Levels;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import quantumLock.*;
import quantumLock.Levels.Level1.Level1;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pwajn
 */
public class Level extends World{
    protected QuantumLock quantumLock;
    protected WorldView view;
    protected PlayerCharacter player;
    private StepListener stepListener;

    private List<SlidingDoor> slidingDoors = new ArrayList<>();

    public void initialize(QuantumLock quantumLock) {
        this.quantumLock = quantumLock;
    }

    public void levelComplete() {
        clear();
        quantumLock.nextLevel();
    }

    public void levelReset() {

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

    public void clearSlodingDoors() {
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

    @Override
    public void addStepListener(StepListener stepListener) {
        this.stepListener = stepListener;
        super.addStepListener(stepListener);
    }
}
